package net.karim.edu.entities.ChemistZombie;

import com.google.common.collect.Sets;
import net.karim.edu.EduChemMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

import static net.karim.edu.fluid.ModFluids.TOXIC_STILL;
import static net.minecraft.fluid.Fluids.FLOWING_WATER;

public class ChemistZombie extends HostileEntity implements GeoEntity, RangedAttackMob {

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final TrackedData<Boolean> DRINKING = DataTracker.registerData(ChemistZombie.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final Text EVENT_TEXT = Text.translatable("event.educationmod.chemist_boss");
    private final ServerBossBar bar = new ServerBossBar(EVENT_TEXT, BossBar.Color.RED, BossBar.Style.NOTCHED_10);

    public ChemistZombie(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }


    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createMobAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0f)
                .add(EntityAttributes.GENERIC_ARMOR, 2.0)
                .add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 130.0);
    }


    @Override
    protected void initGoals() {
//        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, false));
//        this.targetSelector.add(1, new ActiveTargetGoal<PlayerEntity>((MobEntity)this, PlayerEntity.class, true));
        //this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        //this.goalSelector.add(2, new LookAroundGoal(this));
        //this.goalSelector.add(3, new WanderAroundGoal(this, 0.3f));

        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new ProjectileAttackGoal(this, 1.40D, 60, 7.0f));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 0.30D, false));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 0.23f, 1));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));

        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, VillagerEntity.class, true));
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()){
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(DRINKING, false);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void tick() {
        if (this.world instanceof ServerWorld && this.isAlive()) {
            if(this.world.getTime() % 20 == 0){
                this.updateBarToPlayers();
            }

            if(this.world.getTime() % 300 == 0){
                this.toxicWaterSpread();

            }
        }
        super.tick();
    }


    public void toxicWaterSpread(){
            int range = 100;
            int maxYDifference = 10;


            int i = range;
            int j = maxYDifference;
            BlockPos blockPos = this.getBlockPos();
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            int k = 0;

            while (k <= j) {
                for (int l = 0; l < i; ++l) {
                    int m = 0;
                    while (m <= l) {
                        int n;
                        int n2 = n = m < l && m > -l ? l : 0;
                        while (n <= l) {
                            mutable.set(blockPos, m, k - 1, n);
                            if (this.world.getBlockState(mutable).getRegistryEntry().matchesKey(FLOWING_WATER.getDefaultState().getBlockState().getRegistryEntry().getKey().get())) {
                                EduChemMod.LOGGER.info("Found water block and tried to convert at " + mutable);
                                this.world.setBlockState(mutable, TOXIC_STILL.getDefaultState().getBlockState());
                                this.world.markDirty(mutable);
                            }
                            n = n > 0 ? -n : 1 - n;
                        }
                        m = m > 0 ? -m : 1 - m;
                    }
                }
                k = k > 0 ? -k : 1 - k;
            }


    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }

    public boolean isDrinking() {
        return this.getDataTracker().get(DRINKING);
    }

    private void updateBarToPlayers() {
        HashSet<ServerPlayerEntity> set = Sets.newHashSet(this.bar.getPlayers());
        ServerWorld serverWorld = (ServerWorld)this.world;

        List<ServerPlayerEntity> list = serverWorld.getPlayers(this.isInDistance());
        for (ServerPlayerEntity serverPlayerEntity : list) {
            if (set.contains(serverPlayerEntity)) continue;
            this.bar.addPlayer(serverPlayerEntity);
        }
        for (ServerPlayerEntity serverPlayerEntity : set) {
            if (list.contains(serverPlayerEntity)) continue;
            this.bar.removePlayer(serverPlayerEntity);
        }
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        HashSet<ServerPlayerEntity> set = Sets.newHashSet(this.bar.getPlayers());
        for (ServerPlayerEntity serverPlayerEntity : set) {
            this.bar.removePlayer(serverPlayerEntity);
        }
        Entity entity = ((EntityType<VillagerEntity>)EntityType.get("minecraft:villager").get()).create(world);
        assert entity != null;
        entity.updatePosition(this.getPos().x, this.getPos().y, this.getPos().z);
        world.spawnEntity(entity);
        super.onDeath(damageSource);
    }

    private Predicate<ServerPlayerEntity> isInDistance() {
        return player -> {
            BlockPos blockPos = player.getBlockPos();
            return player.isAlive() && blockPos.isWithinDistance(this.getPos(), 50);
        };
    }


    @Override
    public boolean damage(DamageSource source, float amount) {
        float hpPercent = (float) (this.getHealth()/this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue());
        this.bar.setPercent(MathHelper.clamp(hpPercent, 0.0f, 1.0f));
//       this.updateBarToPlayers();
        EduChemMod.LOGGER.info("Chemist Zombie damaged by " + source.getName() + " and damaged by " + amount);

        return super.damage(source, amount);
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        if (this.isDrinking()) {
            return;
        }
        Vec3d vec3d = target.getVelocity();
        double d = target.getX() + vec3d.x - this.getX();
        double e = target.getEyeY() - (double)1.1f - this.getY();
        double f = target.getZ() + vec3d.z - this.getZ();
        double g = Math.sqrt(d * d + f * f);
        Potion potion = Potions.HARMING;
        if (target instanceof RaiderEntity) {
            potion = target.getHealth() <= 4.0f ? Potions.HEALING : Potions.REGENERATION;
            this.setTarget(null);
        } else if (g >= 8.0 && !target.hasStatusEffect(StatusEffects.SLOWNESS)) {
            potion = Potions.SLOWNESS;
        } else if (target.getHealth() >= 8.0f && !target.hasStatusEffect(StatusEffects.POISON)) {
            potion = Potions.POISON;
        } else if (g <= 3.0 && !target.hasStatusEffect(StatusEffects.WEAKNESS) && this.random.nextFloat() < 0.25f) {
            potion = Potions.WEAKNESS;
        }
        PotionEntity potionEntity = new PotionEntity(this.world, this);
        potionEntity.setItem(PotionUtil.setPotion(new ItemStack(Items.SPLASH_POTION), potion));
        potionEntity.setPitch(potionEntity.getPitch() - -20.0f);
        potionEntity.setVelocity(d, e + g * 0.2, f, 0.75f, 8.0f);
        if (!this.isSilent()) {
            this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0f, 0.8f + this.random.nextFloat() * 0.4f);
        }
        this.world.spawnEntity(potionEntity);
    }
}

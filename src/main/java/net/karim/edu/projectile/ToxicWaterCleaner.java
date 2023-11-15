package net.karim.edu.projectile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.karim.edu.EduChemMod;
import net.karim.edu.Item.ModItems;
import net.karim.edu.block.blocks.GenericFireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

import static net.karim.edu.fluid.ModFluids.TOXIC_STILL;
import static net.minecraft.fluid.Fluids.FLOWING_WATER;
import static net.minecraft.fluid.Fluids.WATER;

public class ToxicWaterCleaner extends ThrownItemEntity {

    final double speedScale = 0.3;
    public ToxicWaterCleaner(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public ToxicWaterCleaner(World world, LivingEntity owner) {
        super(ModProjectiles.rocketEntity, owner, world);
    }

    public ToxicWaterCleaner(World world, double x, double y, double z) {
        super(ModProjectiles.rocketEntity, x, y, z,  world);
    }


    @Override
    protected Item getDefaultItem() {
        return ModItems.TOXIC_WATER_CLEANER_POTION;
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 3) {
            Random r = new Random();
            for(int i = 0; i < 100; i++){
                this.world.addParticle(EduChemMod.GREEN_FLAME, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), r.nextDouble()*speedScale - speedScale*0.5, Math.abs(r.nextDouble()*speedScale-0.5*speedScale), r.nextDouble()*speedScale- speedScale*0.5);
            }
        }

    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if(!this.world.isClient){
            BlockPos pos = new BlockPos(hitResult.getPos().getX(), hitResult.getPos().getY(), hitResult.getPos().getZ());
            cleanToxicWater(8, 10, pos);
        }

        this.world.sendEntityStatus(this, (byte)3);
        this.kill();
    }

    public void cleanToxicWater(int range, int maxYDifference, BlockPos pos){

        int i = range;
        int j = maxYDifference;
        BlockPos blockPos = pos;
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
                        if (this.world.getBlockState(mutable).getRegistryEntry().matchesKey(TOXIC_STILL.getDefaultState().getBlockState().getRegistryEntry().getKey().get())) {
                            this.world.setBlockState(mutable, WATER.getDefaultState().getBlockState());
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
}

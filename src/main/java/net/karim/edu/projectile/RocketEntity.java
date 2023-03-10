package net.karim.edu.projectile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.karim.edu.ExampleMod;
import net.karim.edu.Item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

import java.util.Random;

public class RocketEntity extends ThrownItemEntity {
    final double speedScale = 0.3;
    public RocketEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public RocketEntity(World world, LivingEntity owner) {
        super(ExampleMod.rocketEntity, owner, world);
    }

    public RocketEntity(World world, double x, double y, double z) {
        super(ExampleMod.rocketEntity, x, y, z,  world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.GPS_ITEM;
    }


    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 3) {
            Random r = new Random();
            for(int i = 0; i < 100; i++){
                this.world.addParticle(ExampleMod.GREEN_FLAME, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), r.nextDouble()*speedScale - speedScale*0.5, Math.abs(r.nextDouble()*speedScale-0.5*speedScale), r.nextDouble()*speedScale- speedScale*0.5);
            }
        }

    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        entityHitResult.getEntity().damage(DamageSource.thrownProjectile(this, this.getOwner()), 10000);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if(!this.world.isClient){
            this.world.sendEntityStatus(this, (byte)3);
            this.kill();
        }
    }
}

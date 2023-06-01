package net.karim.edu.projectile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.karim.edu.EduChemMod;
import net.karim.edu.Item.ModItems;
import net.karim.edu.block.ModBlocks;
import net.karim.edu.block.blocks.GenericFireBlock;
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

public class RocketEntity extends ThrownItemEntity {
    final double speedScale = 0.3;
    private GenericFireBlock fireBlock = (GenericFireBlock) ModBlocks.BLUE_FIRE;
    public RocketEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public RocketEntity(World world, LivingEntity owner) {
        super(EduChemMod.rocketEntity, owner, world);
    }

    public RocketEntity(World world, LivingEntity owner, GenericFireBlock fireBlock) {
        super(EduChemMod.rocketEntity, owner, world);
        this.fireBlock = fireBlock;
    }

    public RocketEntity(World world, double x, double y, double z) {
        super(EduChemMod.rocketEntity, x, y, z,  world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.FIRE_BOMB;
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
        entityHitResult.getEntity().damage(DamageSource.thrownProjectile(this, this.getOwner()), 100);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if(!this.world.isClient){
            BlockPos pos = new BlockPos(hitResult.getPos().getX(), hitResult.getPos().getY(), hitResult.getPos().getZ());
            //int widthOfArea = 1;
            if(world.isAir(pos)) {
                world.setBlockState(pos, fireBlock.getDefaultState());
            }
//            for(int i = 0; i < widthOfArea;i++) {
//                for (int x = 0; x < widthOfArea; x++) {
//                    for (int y = 0; y < widthOfArea; y++) {
//                        BlockPos tempPos = pos.offset(Direction.UP, i-widthOfArea/2).offset(Direction.EAST, x-widthOfArea/2).offset(Direction.NORTH, y-widthOfArea/2);
//                        if(world.isAir(tempPos)) {
//                            world.setBlockState(tempPos, fireBlock.getDefaultState());
//                        }
//                    }
//                }
//            }

            }

            this.world.sendEntityStatus(this, (byte)3);
            this.kill();
    }
}

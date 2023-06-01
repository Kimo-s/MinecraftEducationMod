package net.karim.edu.block.entity;

import net.karim.edu.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ToxicAirBlockEntity extends BlockEntity {

    int timeLeft = 10;
    public ToxicAirBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TOXIC_AIR_BLOCK_ENTITY, pos, state);
    }



    @Override
    public void readNbt(NbtCompound nbt) {
        timeLeft = nbt.getInt("timeLeft");
        super.readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("timeLeft", timeLeft);
        super.writeNbt(nbt);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ToxicAirBlockEntity entity) {
        if(!world.isClient()){
            if(world.getTime() % 15 == 0 && world.isAir(pos.up())){
                entity.timeLeft -= 1;
                if (entity.timeLeft > 0) {
                    world.setBlockState(pos.up(), ModBlocks.TOXIC_GAS.getDefaultState());
                    world.markDirty(pos.up());
                }
                world.removeBlockEntity(pos);
                world.removeBlock(pos,true);
                world.markDirty(pos);
            }

            return;
        }


    }



}

package net.karim.edu.block.entity;

import net.karim.edu.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ToxicBlockEntity extends BlockEntity {
    public ToxicBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TOXIC_BLOCK_ENTITY, pos, state);
    }


    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ToxicBlockEntity entity) {
        if(!world.isClient()){
            if(world.getTime() % 300 == 0 && world.isAir(pos.up())){
                world.setBlockState(pos.up(), ModBlocks.TOXIC_GAS.getDefaultState());
                world.markDirty(pos.up());
                world.markDirty(pos);
            }

            return;
        }


    }
}

package net.karim.edu.block.entity;

import net.karim.edu.ExampleMod;
import net.karim.edu.block.ModBlocks;
import net.karim.edu.block.blocks.ToxicAirBlock;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ToxicAirBlockEntity extends BlockEntity {

    public ToxicAirBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TOXIC_AIR_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ToxicAirBlockEntity entity) {
        if(world.isClient()){
            if(world.getTime() % 15 == 0){
                world.setBlockState(pos.up(), ModBlocks.TOXIC_GAS.getDefaultState());
                world.markDirty(pos);
                world.markDirty(pos.up());
            }
            return;
        }


    }

}

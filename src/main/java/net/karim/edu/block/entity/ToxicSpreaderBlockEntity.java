package net.karim.edu.block.entity;

import net.karim.edu.block.ModBlocks;
import net.karim.edu.block.blocks.CleanerBlock;
import net.karim.edu.block.blocks.ToxicWaste;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import static net.minecraft.block.Blocks.DIRT;

public class ToxicSpreaderBlockEntity extends BlockEntity {
    public ToxicSpreaderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TOXIC_SPREADER_ENTITY, pos, state);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ToxicSpreaderBlockEntity entity) {

        int maxYDif = 4;
        int searchRange = 30;

        if(!world.isClient()){

            float rand = Random.create().nextFloat();
            if(rand <= 0.95){
                return;
            }

            if(entity.searchCleanerWithInRange(pos, maxYDif, searchRange)){
                return;
            }


            BlockPos.Mutable curPos = new BlockPos.Mutable();
            for(int x = -searchRange/2 ; x < searchRange/2; x++){
                for(int z = -searchRange/2 ; z < searchRange/2; z++){
                    for(int y = -maxYDif/2; y < maxYDif/2; y++){
                        curPos.set(x + pos.getX(), y + pos.getY(), z + pos.getZ());
                        if(world.getBlockState(curPos).getBlock() instanceof GrassBlock){
                            world.removeBlockEntity(curPos);
                            world.removeBlock(curPos, true);
                            world.setBlockState(curPos, DIRT.getDefaultState());

                            world.markDirty(curPos);
                        }

                    }
                }
            }

            return;
        }

    }

    boolean searchCleanerWithInRange(BlockPos pos, int maxYDif, int searchRange){
        BlockPos.Mutable curPos = new BlockPos.Mutable();
        for(int x = -searchRange/2 ; x < searchRange/2; x++){
            for(int z = -searchRange/2 ; z < searchRange/2; z++){
                for(int y = -maxYDif/2; y < maxYDif/2; y++){
                    curPos.set(x + pos.getX(), y + pos.getY(), z + pos.getZ());
                    if(world.getBlockState(curPos).getBlock() instanceof CleanerBlock){
                        return true;
                    }

                }
            }
        }
        return false;
    }

}

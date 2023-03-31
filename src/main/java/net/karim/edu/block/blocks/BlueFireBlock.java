package net.karim.edu.block.blocks;

import net.karim.edu.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.Map;

public class BlueFireBlock extends FireBlock {

    public static final int COLOR = 0x30E6FF;
    public static final int COLOR_BRIGHT = 0xBFF7FF;
    private VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 0.1D, 16.0D);

    private static final Map<Direction, BooleanProperty> DIRECTION_PROPERTIES = ConnectingBlock.FACING_PROPERTIES.entrySet().stream().filter(entry -> entry.getKey() != Direction.DOWN).collect(Util.toMap());

    public BlueFireBlock(Settings settings) {
        super(settings);
    }



    public BlockState getStateForPosition(BlockView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        if (!this.isFlammable(blockState) && !blockState.isSideSolidFullSquare(world, blockPos, Direction.UP)) {
            BlockState blockState2 = this.getDefaultState();
            Direction[] var6 = Direction.values();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                Direction direction = var6[var8];
                BooleanProperty booleanProperty = (BooleanProperty)DIRECTION_PROPERTIES.get(direction);
                if (booleanProperty != null) {
                    blockState2 = (BlockState)blockState2.with(booleanProperty, this.isFlammable(world.getBlockState(pos.offset(direction))));
                }
            }

            return blockState2;
        } else {
            return this.getDefaultState();
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }


}

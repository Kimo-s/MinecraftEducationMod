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

public class BlueFireBlock extends GenericFireBlock {
    public static final int COLOR = 0x30E6FF;
    public static final int COLOR_BRIGHT = 0xBFF7FF;

    public BlueFireBlock() {
        super();
    }
}

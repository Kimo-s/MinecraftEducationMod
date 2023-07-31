package net.karim.edu.block.blocks;

import net.karim.edu.block.entity.ModBlockEntities;
import net.karim.edu.block.entity.ToxicAirBlockEntity;
import net.karim.edu.block.entity.ToxicSpreaderBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ToxicSpreaderBlock extends BlockWithEntity implements BlockEntityProvider {


    public ToxicSpreaderBlock(Settings settings) {
        super(settings);
    }



    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.TOXIC_SPREADER_ENTITY, ToxicSpreaderBlockEntity::tick);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ToxicSpreaderBlockEntity(pos, state);
    }




}

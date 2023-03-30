package net.karim.edu.block.blocks;

import net.fabricmc.fabric.mixin.datagen.loot.BlockLootTableGeneratorMixin;
import net.karim.edu.block.entity.DecomposerTableBlockEntity;
import net.karim.edu.block.entity.ModBlockEntities;
import net.karim.edu.block.entity.ToxicAirBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ToxicAirBlock extends BlockWithEntity implements BlockEntityProvider {
    public ToxicAirBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ToxicAirBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.TOXIC_AIR_BLOCK_ENTITY, ToxicAirBlockEntity::tick);
    }
}

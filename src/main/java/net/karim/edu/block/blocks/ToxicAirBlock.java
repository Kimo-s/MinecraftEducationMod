package net.karim.edu.block.blocks;

import net.fabricmc.fabric.mixin.datagen.loot.BlockLootTableGeneratorMixin;
import net.karim.edu.block.entity.DecomposerTableBlockEntity;
import net.karim.edu.block.entity.ModBlockEntities;
import net.karim.edu.block.entity.ToxicAirBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.entity.effect.StatusEffects.POISON;

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

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if(entity instanceof LivingEntity){
            ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(POISON, 30, 1));
        }
    }
}

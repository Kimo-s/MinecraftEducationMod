package net.karim.edu.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.karim.edu.ExampleMod;
import net.karim.edu.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.logging.Logger;


public class ModBlockEntities {
    public static BlockEntityType<ChemTableBlockEntity> CHEM_TABLE_ENTITY;
    public static BlockEntityType<DecomposerTableBlockEntity> DECOMPOSER_TABLE_ENTITY;
    public static BlockEntityType<LockBlockEntity> LOCK_BLOCK_ENTITY;
    public static BlockEntityType<ToxicAirBlockEntity> TOXIC_AIR_BLOCK_ENTITY;

    public static void registerBlockEntities(){
        CHEM_TABLE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(ExampleMod.MOD_ID, "chem_table"),
                FabricBlockEntityTypeBuilder.create(ChemTableBlockEntity::new, ModBlocks.CHEM_TABLE).build(null));

        DECOMPOSER_TABLE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(ExampleMod.MOD_ID, "decomposer_table"),
                FabricBlockEntityTypeBuilder.create(DecomposerTableBlockEntity::new, ModBlocks.DECOMPOSER_TABLE).build(null));
        LOCK_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(ExampleMod.MOD_ID, "lock_table"),
                FabricBlockEntityTypeBuilder.create(LockBlockEntity::new, ModBlocks.LOCK_BLOCK).build(null));

        TOXIC_AIR_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(ExampleMod.MOD_ID, "toxic_gas"),
                FabricBlockEntityTypeBuilder.create(ToxicAirBlockEntity::new, ModBlocks.TOXIC_GAS).build(null));
    }
}

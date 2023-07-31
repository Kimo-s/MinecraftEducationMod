package net.karim.edu.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.karim.edu.EduChemMod;
import net.karim.edu.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModBlockEntities {
    public static BlockEntityType<ChemTableBlockEntity> CHEM_TABLE_ENTITY;
    public static BlockEntityType<DecomposerTableBlockEntity> DECOMPOSER_TABLE_ENTITY;
    public static BlockEntityType<LockBlockEntity> LOCK_BLOCK_ENTITY;
    public static BlockEntityType<ToxicAirBlockEntity> TOXIC_AIR_BLOCK_ENTITY;
    public static BlockEntityType<ToxicBlockEntity> TOXIC_BLOCK_ENTITY;
    public static BlockEntityType<ToxicSpreaderBlockEntity> TOXIC_SPREADER_ENTITY;

    public static void registerBlockEntities(){
        CHEM_TABLE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(EduChemMod.MOD_ID, "chem_table"),
                FabricBlockEntityTypeBuilder.create(ChemTableBlockEntity::new, ModBlocks.CHEM_TABLE).build(null));

        DECOMPOSER_TABLE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(EduChemMod.MOD_ID, "decomposer_table"),
                FabricBlockEntityTypeBuilder.create(DecomposerTableBlockEntity::new, ModBlocks.DECOMPOSER_TABLE).build(null));

        LOCK_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(EduChemMod.MOD_ID, "lock_table"),
                FabricBlockEntityTypeBuilder.create(LockBlockEntity::new, ModBlocks.LOCK_BLOCK).build(null));

        TOXIC_AIR_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(EduChemMod.MOD_ID, "toxic_gas"),
                FabricBlockEntityTypeBuilder.create(ToxicAirBlockEntity::new, ModBlocks.TOXIC_GAS).build(null));

        TOXIC_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(EduChemMod.MOD_ID, "toxic_block"),
                FabricBlockEntityTypeBuilder.create(ToxicBlockEntity::new, ModBlocks.TOXIC_BLOCK).build(null));

        TOXIC_SPREADER_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(EduChemMod.MOD_ID, "toxic_spreader"),
                FabricBlockEntityTypeBuilder.create(ToxicSpreaderBlockEntity::new, ModBlocks.TOXIC_SPREADER_BLOCK).build(null));
    }
}

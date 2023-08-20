package net.karim.edu.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.karim.edu.EduChemMod;
import net.karim.edu.Item.ModItemGroup;
import net.karim.edu.block.blocks.*;
import net.karim.edu.fluid.ModFluids;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import static net.minecraft.block.Blocks.COBBLED_DEEPSLATE;

public class ModBlocks {


    public static final Block CHEM_TABLE = registerBlock("chem_table", ModItemGroup.ELEMENTS, new ChemTableBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.ANVIL).strength(6f)));
    public static final Block DECOMPOSER_TABLE = registerBlock("decomposer_table", ModItemGroup.ELEMENTS, new DecomposerTableBlock(FabricBlockSettings.of(Material.METAL).strength(6f)));
    public static final Block LOCK_BLOCK = registerBlock("lock_block", ModItemGroup.ELEMENTS, new LockBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.ANVIL).strength(100000f).resistance(1000000).hardness(1000000)));
    public static final Block HARDENED_BRICKS = registerBlock("hardened_bricks",  ModItemGroup.ELEMENTS, new Block(FabricBlockSettings.copy(COBBLED_DEEPSLATE).sounds(BlockSoundGroup.DEEPSLATE_BRICKS).strength(100000f).resistance(1000000)));
    public static final Block TITANIUM_ORE = registerBlock("titanium_ore", ModItemGroup.ELEMENTS, new ExperienceDroppingBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0f, 3.0f)));
    public static final Block TOXIC_BLOCK = registerBlock("toxic_block", ModItemGroup.ELEMENTS, new ToxicBlock(FabricBlockSettings.of(Material.METAL).strength(6f)));
    public static final Block TOXIC_SPREADER_BLOCK = registerBlock("toxic_spreader", ModItemGroup.ELEMENTS, new ToxicSpreaderBlock(FabricBlockSettings.of(Material.METAL).strength(6f)));
    public static final Block CLEANER_BLOCK = registerBlock("cleaner_block", ModItemGroup.ELEMENTS, new CleanerBlock(FabricBlockSettings.of(Material.METAL).strength(6f)));


    public static final Block TOXIC_GAS = registerBlock("toxic_gas",  ModItemGroup.ELEMENTS, new ToxicAirBlock(FabricBlockSettings.of(Material.AIR).nonOpaque().noCollision().suffocates(ModBlocks::always)));
    public static final Block BLUE_FIRE = registerBlock("blue_fire",  ModItemGroup.ELEMENTS, new BlueFireBlock());
    public static final Block GREEN_FIRE = registerBlock("green_fire",  ModItemGroup.ELEMENTS, new GreenFireBlock());
    public static final Block PURPLE_FIRE = registerBlock("purple_fire",  ModItemGroup.ELEMENTS, new PurpleFireBlock());
    public static final Block GENERIC_FIRE = registerBlock("generic_fire",  ModItemGroup.ELEMENTS, new GenericFireBlock());

    public static final Block TOXIC_FLUID_BLOCK = registerBlock("toxic_fluid_block", ModItemGroup.ELEMENTS, new FluidBlock(ModFluids.TOXIC_STILL, FabricBlockSettings.of(Material.WATER).noCollision().nonOpaque().dropsNothing()));

    private static boolean always(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    private static Block registerBlock(String name, ItemGroup group, Block block) {
        registerBlockItem(name, group, block);
        EduChemMod.LOGGER.info("Registering block: " + name);
        return Registry.register(Registries.BLOCK, new Identifier(EduChemMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, ItemGroup group, Block block) {
        BlockItem item = new BlockItem(block, new FabricItemSettings());
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add((item)));

        return Registry.register(Registries.ITEM, new Identifier(EduChemMod.MOD_ID, name), item);
    }

    public static void registerModBlocks() {
        EduChemMod.LOGGER.info("Registering mod blocks");
    }

}
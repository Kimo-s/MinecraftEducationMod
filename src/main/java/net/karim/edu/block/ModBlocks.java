package net.karim.edu.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.karim.edu.ExampleMod;
import net.karim.edu.Item.ModItemGroup;
import net.karim.edu.Item.ModItems;
import net.karim.edu.block.blocks.*;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import static net.minecraft.block.Blocks.COBBLED_DEEPSLATE;

public class ModBlocks {


    public static final Block CHEM_TABLE = registerBlock("chem_table", ModItemGroup.ELEMENTS, new ChemTableBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.ANVIL).strength(6f)));
    public static final Block DECOMPOSER_TABLE = registerBlock("decomposer_table", ModItemGroup.ELEMENTS, new DecomposerTableBlock(FabricBlockSettings.of(Material.METAL).strength(6f)));
    public static final Block LOCK_BLOCK = registerBlock("lock_block", ModItemGroup.ELEMENTS, new LockBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.ANVIL).strength(100000f).resistance(1000000)));
    public static final Block HARDENED_BRICKS = registerBlock("hardened_bricks",  ModItemGroup.ELEMENTS, new Block(FabricBlockSettings.copy(COBBLED_DEEPSLATE).sounds(BlockSoundGroup.DEEPSLATE_BRICKS).strength(100000f).resistance(1000000)));
    public static final Block TITANIUM_ORE = registerBlock("titanium_ore", ModItemGroup.ELEMENTS, new ExperienceDroppingBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0f, 3.0f)));

    public static final Block TOXIC_GAS = registerBlock("toxic_gas",  ModItemGroup.ELEMENTS, new ToxicAirBlock(FabricBlockSettings.of(Material.AIR).nonOpaque().air().noCollision()));
    public static final Block BLUE_FIRE = registerBlock("blue_fire",  ModItemGroup.ELEMENTS, new BlueFireBlock());
    public static final Block GREEN_FIRE = registerBlock("green_fire",  ModItemGroup.ELEMENTS, new GreenFireBlock());
    public static final Block PURPLE_FIRE = registerBlock("purple_fire",  ModItemGroup.ELEMENTS, new PurpleFireBlock());
    public static final Block GENERIC_FIRE = registerBlock("generic_fire",  ModItemGroup.ELEMENTS, new GenericFireBlock());

    private static Block registerBlock(String name, ItemGroup group, Block block) {
        registerBlockItem(name, group, block);
        ExampleMod.LOGGER.info("Registering block: " + name);
        return Registry.register(Registries.BLOCK, new Identifier(ExampleMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, ItemGroup group, Block block) {
        BlockItem item = new BlockItem(block, new FabricItemSettings());
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add((item)));

        return Registry.register(Registries.ITEM, new Identifier(ExampleMod.MOD_ID, name), item);
    }

    public static void registerModBlocks() {
        ExampleMod.LOGGER.info("Registering mod blocks");
    }

}

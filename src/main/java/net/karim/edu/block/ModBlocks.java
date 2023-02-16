package net.karim.edu.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.karim.edu.ExampleMod;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {


    public static final Block test_block = registerBlock("test_block", ItemGroups.INGREDIENTS, new Block(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.ANVIL).collidable(true).strength(6f).requiresTool()));


    private static Block registerBlock(String name, ItemGroup group, Block block) {
        registerBlockItem(name, group, block);
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

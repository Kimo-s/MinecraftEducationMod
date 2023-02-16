package net.karim.edu.Item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.karim.edu.ExampleMod;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.item.Item;

public class ModItems {

    public static final Item test_item = registerItem("test_item", new Item(new FabricItemSettings().fireproof()));

    private static Item registerItem(String name, Item item){
        ExampleMod.LOGGER.info("Registered Item: " + name);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add((item)));
        return Registry.register (Registries.ITEM,new Identifier(ExampleMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        ExampleMod.LOGGER.info("Registering mod items for " + ExampleMod.MOD_ID);
    }
}

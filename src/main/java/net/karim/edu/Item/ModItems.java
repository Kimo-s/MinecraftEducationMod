package net.karim.edu.Item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.karim.edu.ExampleMod;
import net.minecraft.item.PotionItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.item.Item;

public class ModItems {

    public static final Item test_item = registerItem("test_item", new Item(new FabricItemSettings().fireproof()));
    public static final Item GPS_ITEM = registerItem("gps", new element(new FabricItemSettings().fireproof()));
    public static final Item OXYGEN_ITEM = registerItem("oxygen_item", new Item(new FabricItemSettings().fireproof()));
    public static final Item HYDREGON_ITEM = registerItem("hydrogen_item", new Item(new FabricItemSettings().fireproof()));
    public static final Item IRON_ITEM = registerItem("iron_item", new Item(new FabricItemSettings().fireproof()));
    public static final Item Cu_ITEM = registerItem("cu_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item P_ITEM = registerItem("p_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item N_ITEM = registerItem("n_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item K_ITEM = registerItem("k_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item S_ITEM = registerItem("s_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item C_ITEM = registerItem("c_elem", new Item(new FabricItemSettings().fireproof()));


    //molecules
    public static final Item O2_ITEM = registerItem("o2_elem", new BlueFireNA2O2(new FabricItemSettings().fireproof()));




    private static Item registerItem(String name, Item item){
        ExampleMod.LOGGER.info("Registered Item: " + name);
        ItemGroupEvents.modifyEntriesEvent(ModItemGroup.ELEMENTS).register(entries -> entries.add((item)));
        return Registry.register (Registries.ITEM,new Identifier(ExampleMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        ExampleMod.LOGGER.info("Registering mod items for " + ExampleMod.MOD_ID);
    }
}

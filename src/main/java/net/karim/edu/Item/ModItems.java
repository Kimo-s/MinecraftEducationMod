package net.karim.edu.Item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.karim.edu.EduChemMod;
import net.karim.edu.block.ModBlocks;
import net.karim.edu.block.blocks.BlueFireBlock;
import net.karim.edu.block.blocks.PurpleFireBlock;
import net.karim.edu.entities.ModEntities;
import net.karim.edu.fluid.ModFluids;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item test_item = registerItem("test_item", new Item(new FabricItemSettings().fireproof()));
    public static final Item GUIDE = registerItem("chem_book", (Item)new WrittenBookItem(new FabricItemSettings().maxCount(16)));
    public static final Item BLOCK_ANALYZER = registerItem("block_analyzer", new BlockAnalyzer(new FabricItemSettings().maxCount(1)));
    public static final Item FERTILIZER_ROBOT_SPAWN_EGG = registerItem("fertilizer_robot_spawn_egg", (Item)new SpawnEggItem(ModEntities.FERTILIZER_ROBOT, 0x959B9B, 12422002, new FabricItemSettings()));
    public static final Item FIRE_BOMB = registerItem("fire_bomb", new GenericBomb(new FabricItemSettings().fireproof(), (BlueFireBlock) ModBlocks.BLUE_FIRE));
    public static final Item TOXIC_WATER_CLEANER_POTION = registerItem("toxic_water_cleaner_potion", new ToxicWaterCleanerPotion(new FabricItemSettings().fireproof()));
    public static final Item FIRE_BOMB_PURPLE = registerItem("fire_bomb_purple", new GenericBomb(new FabricItemSettings().fireproof(), (PurpleFireBlock) ModBlocks.PURPLE_FIRE));

    //elements
    public static final Item O_ELEM = registerItem("o_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item HYDREGON_ITEM = registerItem("h_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item IRON_ITEM = registerItem("iron_item", new Item(new FabricItemSettings().fireproof()));
    public static final Item Cu_ITEM = registerItem("cu_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item P_ITEM = registerItem("p_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item N_ITEM = registerItem("n_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item K_ITEM = registerItem("k_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item S_ITEM = registerItem("s_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item C_ITEM = registerItem("c_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item NA_ITEM = registerItem("na_elem", new Item(new FabricItemSettings().fireproof()));

    public static final Item[] elements = new Item[]{O_ELEM, HYDREGON_ITEM, IRON_ITEM, Cu_ITEM, P_ITEM, N_ITEM, K_ITEM, S_ITEM, C_ITEM, NA_ITEM};


    //molecules
    public static final Item NA2O2_ITEM = registerItem("na2o2_elem", new BlueFireNA2O2(new FabricItemSettings().fireproof()));
    public static final Item K2O_ITEM = registerItem("k2o_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item NAOH_ITEM = registerItem("naoh_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item OH_ITEM = registerItem("oh_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item P2O5_ITEM = registerItem("p2o5_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item O2_ITEM = registerItem("o2_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item H2_ITEM = registerItem("h2_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item NA2_ITEM = registerItem("na2_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item CH4_ITEM = registerItem("ch4_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item CO_ITEM = registerItem("co_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item CO2_ITEM = registerItem("co2_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item H2O_ITEM = registerItem("h2o_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item SO2_ITEM = registerItem("so2_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item CUSO4_ITEM = registerItem("cuso4_elem", new Item(new FabricItemSettings().fireproof()));
    public static final Item PURPLE_KEY = registerItem("purple_key", new Item(new FabricItemSettings().fireproof()));
    public static final Item GREEN_KEY = registerItem("green_key", new Item(new FabricItemSettings().fireproof()));
    public static final Item YELLOW_KEY = registerItem("yellow_key", new Item(new FabricItemSettings().fireproof()));

    public static final Item TITANIUM_INGOT = registerItem("titanium_ingot", new Item(new FabricItemSettings()));
    public static final Item TOXIC_BUCKET = registerItem("toxic_fluid_bucket", new BucketItem(ModFluids.TOXIC_STILL, new FabricItemSettings().maxCount(1)));

    public static final Item TOXIC_CLEANER = registerItem("toxic_cleaner", new ToxicCleaner(new FabricItemSettings().fireproof().maxCount(1)));
    public static final Item QUESTION_1 = registerItem("question_1", new ReadableMessage(new FabricItemSettings().fireproof().maxCount(1), "Castile soap is a common kind of soap that can be used to clean all sorts of things, and is very easy to make. The first step is to create Sodium Carbonate, a substance made of sodium, carbon, and oxygen. Sodium is written as “Na”, Carbon is written as “C”, and oxygen is written as “O”. Which of the following compounds do you think is Sodium Carbonate?" +
            "\n A. M2CO3" +
            "\n B. NaCl" +
            "\n C. Na2CO3"));
    public static final Item HYPER_JUMPER = registerItem("hyper_jumper", new JumpBoost(new FabricItemSettings().fireproof().maxCount(1), 2.4));




    //Titanium tools
    public static Item TITANIUM_AXE = registerItem("titanium_axe", (Item) new AxeItem(TitaniumMaterial.INSTANCE, 3, -3.0f, new Item.Settings()));
    public static Item TITANIUM_PICKAXE = registerItem("titanium_pickaxe", (Item) new PickaxeItem(TitaniumMaterial.INSTANCE, 2, -2.0f, new Item.Settings()));
    public static Item TITANIUM_SWORD = registerItem("titanium_sword", (Item) new SwordItem(TitaniumMaterial.INSTANCE, 6, -2.4f, new Item.Settings()));

    //Titanium armor
    public static final Item DIAMOND_HELMET = registerItem("titanium_helmet", (Item)new ArmorItem(TitaniumArmor.INSTANCE, EquipmentSlot.HEAD, new Item.Settings()));
    public static final Item DIAMOND_CHESTPLATE = registerItem("titanium_chestplate", (Item)new ArmorItem(TitaniumArmor.INSTANCE, EquipmentSlot.CHEST, new Item.Settings()));
    public static final Item DIAMOND_LEGGINGS = registerItem("titanium_leggings", (Item)new ArmorItem(TitaniumArmor.INSTANCE, EquipmentSlot.LEGS, new Item.Settings()));
    public static final Item DIAMOND_BOOTS = registerItem("titanium_boots", (Item)new ArmorItem(TitaniumArmor.INSTANCE, EquipmentSlot.FEET, new Item.Settings()));

    private static Item registerItem(String name, Item item){
        EduChemMod.LOGGER.info("Registered Item: " + name);
        ItemGroupEvents.modifyEntriesEvent(ModItemGroup.ELEMENTS).register(entries -> entries.add((item)));
        return Registry.register (Registries.ITEM,new Identifier(EduChemMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        EduChemMod.LOGGER.info("Registering mod items for " + EduChemMod.MOD_ID);
    }
}

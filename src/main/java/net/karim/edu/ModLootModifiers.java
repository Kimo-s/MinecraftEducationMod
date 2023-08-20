package net.karim.edu;

import net.fabricmc.fabric.api.loot.v2.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v2.FabricLootTableBuilder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.karim.edu.Item.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.BinomialLootNumberProvider;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class ModLootModifiers {

    public static void addElementsDrop(Identifier mobID, Identifier id, LootTable.Builder supplier, LootTableSource setter){
        if(mobID.equals(id) && setter.isBuiltin()) {
            for(int i = 0; i < ModItems.elements.length; i++){
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(ModItems.elements[i]))
                        .rolls(ConstantLootNumberProvider.create(1))
                        .apply(SetCountLootFunction.builder(BinomialLootNumberProvider.create(1, 0.04f)));

                supplier.pool(pool);
            }
        }
    }

    public static void addItemDrop(Item itemToAdd, Identifier mobID, Identifier id, LootTable.Builder supplier, LootTableSource setter){
        if(mobID.equals(id) && setter.isBuiltin()) {
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(itemToAdd))
                        .rolls(ConstantLootNumberProvider.create(1))
                        .apply(SetCountLootFunction.builder(BinomialLootNumberProvider.create(1, 1.0f)));

                supplier.pool(pool);
        }
    }

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register(((resourceManager, manager, id, supplier, setter) -> {
            addElementsDrop(new Identifier("minecraft", "entities/creeper"), id, supplier, setter);
            addElementsDrop(new Identifier("minecraft", "entities/zombie"), id, supplier, setter);
            addElementsDrop(new Identifier("minecraft", "entities/spider"), id, supplier, setter);
            addElementsDrop(new Identifier("minecraft", "entities/slime"), id, supplier, setter);
            addElementsDrop(new Identifier("minecraft", "entities/hoglin"), id, supplier, setter);
            addElementsDrop(new Identifier("minecraft", "entities/pillager"), id, supplier, setter);
            addElementsDrop(new Identifier("minecraft", "entities/enderman"), id, supplier, setter);
            addElementsDrop(new Identifier("minecraft", "entities/witcher"), id, supplier, setter);

            addItemDrop(ModItems.PURPLE_KEY, new Identifier(EduChemMod.MOD_ID, "entities/chemist_zombie"), id, supplier, setter);

        }));
    }


}

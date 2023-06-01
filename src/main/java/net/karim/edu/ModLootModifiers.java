package net.karim.edu;

import net.fabricmc.fabric.api.loot.v2.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v2.FabricLootTableBuilder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.karim.edu.Item.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootModifiers {

    private static final Identifier CREEPER_ID
            = new Identifier("minecraft", "entities/creeper");

    public static void modifyLootTables() {

        LootTableEvents.MODIFY.register(((resourceManager, manager, id, supplier, setter) -> {

            if(CREEPER_ID.equals(id) && setter.isBuiltin()) {
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(ModItems.FIRE_BOMB))
                        .rolls(ConstantLootNumberProvider.create(1))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                supplier.pool(pool);
            }

            if(Blocks.DIRT.getLootTableId().equals(id) && setter.isBuiltin()) {
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(ModItems.FIRE_BOMB))
                        .rolls(ConstantLootNumberProvider.create(1))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f)).build());
                supplier.pool(pool);
            }
        }));
    }


}

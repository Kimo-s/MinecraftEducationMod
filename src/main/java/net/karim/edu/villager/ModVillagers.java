package net.karim.edu.villager;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerProfessionBuilder;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.karim.edu.EduChemMod;
import net.karim.edu.Item.ModItems;
import net.karim.edu.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

public class ModVillagers {
    public static final PointOfInterestType CHEM_POI = registerPOI("chem_poi", ModBlocks.CHEM_TABLE);
    public static final VillagerProfession CHEM_VILLAGER = registerProfession("chemist",
            RegistryKey.of(Registries.POINT_OF_INTEREST_TYPE.getKey(), new Identifier(EduChemMod.MOD_ID, "chem_poi")));

    public static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type){
        return Registry.register(Registries.VILLAGER_PROFESSION, new Identifier(EduChemMod.MOD_ID, name),
                VillagerProfessionBuilder.create().id(new Identifier(EduChemMod.MOD_ID, name)).workstation(type)
                        .workSound(SoundEvents.ENTITY_VILLAGER_WORK_ARMORER).build());
    }
    public static PointOfInterestType registerPOI(String name, Block block){
        return PointOfInterestHelper.register(new Identifier(EduChemMod.MOD_ID, name),
                1, 1, ImmutableSet.copyOf(block.getStateManager().getStates()));
    }

    public static void registerVillagers(){
        EduChemMod.LOGGER.info("Registering Villagers for " + EduChemMod.MOD_ID);
    }

    public static void registerTrades(){

        TradeOfferHelper.registerVillagerOffers(CHEM_VILLAGER, 1,
                factories -> {
                for(int i = 0; i < ModItems.elements.length; i++) {
                    int finalI = i;
                    factories.add((entity, random) -> new TradeOffer(
                        new ItemStack(Items.EMERALD, 2),
                        new ItemStack(ModItems.elements[finalI], 2),
                        8, 15, 0.02f));
                }
        });

        TradeOfferHelper.registerVillagerOffers(CHEM_VILLAGER, 1,
                factories -> {
                    for(int i = 0; i < ModItems.elements.length; i++) {
                        int finalI = i;
                        factories.add((entity, random) -> new TradeOffer(
                                new ItemStack(ModItems.elements[finalI], 1),
                                new ItemStack(Items.EMERALD, 3),
                                3, 10, 0.02f));
                    }
                });

    }

    public static void registerCustomTrades(){
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 2),
                            new ItemStack(ModItems.TITANIUM_INGOT, 12),
                            6, 2, 0.02f));
                });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 5),
                            new ItemStack(ModItems.TITANIUM_PICKAXE, 1),
                            2, 7, 0.02f));
                });
    }
}

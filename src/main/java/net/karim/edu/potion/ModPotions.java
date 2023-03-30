package net.karim.edu.potion;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.karim.edu.ExampleMod;
import net.karim.edu.Item.ModItemGroup;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModPotions {


    public static final Potion HASTE_POTION = registerPotion("haste_potion", new StatusEffectInstance(StatusEffects.HASTE, 3000, 3));


    public static Potion registerPotion(String name, StatusEffectInstance effect){
        Potion temp = Registry.register(Registries.POTION, new Identifier(ExampleMod.MOD_ID, name), new Potion(
                effect
        ));
        return temp;
    }

    public static void registerModPotions() {
        ExampleMod.LOGGER.info("Registering mod potions for " + ExampleMod.MOD_ID);
    }
}

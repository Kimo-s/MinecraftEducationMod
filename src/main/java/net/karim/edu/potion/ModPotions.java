package net.karim.edu.potion;

import net.karim.edu.EduChemMod;
import net.karim.edu.Item.ModItems;
import net.karim.edu.mixin.BrewingRecipeRegistryMixin;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModPotions {


    public static final Potion HASTE_POTION = registerPotion("haste_potion", new StatusEffectInstance(StatusEffects.HASTE, 3000, 3));


    public static Potion registerPotion(String name, StatusEffectInstance effect){
        Potion temp = Registry.register(Registries.POTION, new Identifier(EduChemMod.MOD_ID, name), new Potion(
                effect
        ));
        return temp;
    }

    public static void registerModPotions() {
        addPotionRecipe(ModItems.CH4_ITEM, HASTE_POTION);
        addPotionRecipe(ModItems.SO2_ITEM, Potions.HARMING);
        EduChemMod.LOGGER.info("Registering mod potions for " + EduChemMod.MOD_ID);
    }

    static void addPotionRecipe(Item item, Potion output){
        Potion inputPotions[] = new Potion[]{Potions.AWKWARD, Potions.WATER, Potions.THICK, Potions.MUNDANE};
        for(int i = 0; i < inputPotions.length; i++){
            BrewingRecipeRegistryMixin.addPotionRecipe(inputPotions[i], item, output);
        }
    }


}

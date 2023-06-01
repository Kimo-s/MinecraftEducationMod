package net.karim.edu.recipe;

import net.karim.edu.EduChemMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static void registerRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(EduChemMod.MOD_ID, ChemTableRecipe.Serializer.ID),
                ChemTableRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(EduChemMod.MOD_ID, ChemTableRecipe.Type.ID),
                ChemTableRecipe.Type.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(EduChemMod.MOD_ID, DecomposerTableRecipe.Serializer.ID),
                DecomposerTableRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(EduChemMod.MOD_ID, DecomposerTableRecipe.Type.ID),
                DecomposerTableRecipe.Type.INSTANCE);
    }
}

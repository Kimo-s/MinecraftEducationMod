package net.karim.edu.Item;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class TitaniumMaterial implements ToolMaterial {

    public static final TitaniumMaterial INSTANCE = new TitaniumMaterial();

    @Override
    public int getDurability() {
        return 2300;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 11.0F;
    }

    @Override
    public float getAttackDamage() {
        return 3.0F;
    }

    @Override
    public int getMiningLevel() {
        return 5;
    }

    @Override
    public int getEnchantability() {
        return 18;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModItems.TITANIUM_INGOT);
    }
}

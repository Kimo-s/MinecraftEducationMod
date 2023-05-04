package net.karim.edu.Item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class TitaniumArmor implements ArmorMaterial {

    public static final TitaniumArmor INSTANCE = new TitaniumArmor();
    private static final int[] BASE_DURABILITY = new int[] {13, 15, 16, 11};
    private static final int[] PROTECTION_VALUES = new int[] {4, 7, 9, 4};
    private final int durabilityMultiplier = 47;
    private final int enchantability = 20;
    @Override
    public int getDurability(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getEntitySlotId()] * durabilityMultiplier;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return PROTECTION_VALUES[slot.getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModItems.TITANIUM_INGOT);
    }

    @Override
    public String getName() {
        return "titanium";
    }

    @Override
    public float getToughness() {
        return 4.0f;
    }

    @Override
    public float getKnockbackResistance() {
        return durabilityMultiplier*(1.0f/10.0f);
    }
}

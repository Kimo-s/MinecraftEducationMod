package net.karim.edu.Item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.karim.edu.ExampleMod;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup ELEMENTS = FabricItemGroup.builder(new Identifier(ExampleMod.MOD_ID, "elements")).build();
}

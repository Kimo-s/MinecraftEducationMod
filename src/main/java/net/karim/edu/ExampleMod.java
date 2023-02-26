package net.karim.edu;


import net.fabricmc.api.ModInitializer;
import net.karim.edu.Item.ModItems;
import net.karim.edu.block.ModBlocks;
import net.karim.edu.block.entity.ModBlockEntities;
import net.karim.edu.recipe.ModRecipes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExampleMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "educationmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {

		ModBlocks.registerModBlocks();
		ModItems.registerModItems();

		ModBlockEntities.registerBlockEntities();
		ModRecipes.registerRecipes();
	}
}

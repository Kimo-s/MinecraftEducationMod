package net.karim.edu;


import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.karim.edu.Item.ModItems;
import net.karim.edu.block.ModBlocks;
import net.karim.edu.block.entity.ModBlockEntities;
import net.karim.edu.potion.ModPotions;
import net.karim.edu.projectile.RocketEntity;
import net.karim.edu.recipe.ModRecipes;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.loot.LootTables;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExampleMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "educationmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	public static final DefaultParticleType GREEN_FLAME = FabricParticleTypes.simple();
	public static final EntityType<RocketEntity> rocketEntity = Registry.register(Registries.ENTITY_TYPE,
			new Identifier(ExampleMod.MOD_ID, "rocket_projectile"),
			FabricEntityTypeBuilder.<RocketEntity>create(SpawnGroup.MISC, RocketEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeBlocks(7).trackedUpdateRate(10).build()
	);

	public static final RegistryKey<PlacedFeature> TITANIUM_ORE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(ExampleMod.MOD_ID, "titanium_ore"));


	@Override
	public void onInitialize() {

		Registry.register(Registries.PARTICLE_TYPE, new Identifier(ExampleMod.MOD_ID, "test_particle"), GREEN_FLAME);
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, TITANIUM_ORE_PLACED_KEY);

		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModPotions.registerModPotions();

		ModBlockEntities.registerBlockEntities();
		ModRecipes.registerRecipes();

	}
}

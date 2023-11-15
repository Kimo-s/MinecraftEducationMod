package net.karim.edu.projectile;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.karim.edu.EduChemMod;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModProjectiles {
    public static final EntityType<RocketEntity> rocketEntity = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(EduChemMod.MOD_ID, "rocket_projectile"),
            FabricEntityTypeBuilder.<RocketEntity>create(SpawnGroup.MISC, RocketEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeBlocks(7).trackedUpdateRate(10).build()
    );
    public static final EntityType<ToxicWaterCleaner> TOXIC_WATER_CLEANER_ENTITY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(EduChemMod.MOD_ID, "toxic_water_cleaner_projectile"),
            FabricEntityTypeBuilder.<ToxicWaterCleaner>create(SpawnGroup.MISC, ToxicWaterCleaner::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeBlocks(7).trackedUpdateRate(10).build()
    );

    public static void registerProjectileEntities(){
        EntityRendererRegistry.register(ModProjectiles.rocketEntity, (context) -> new FlyingItemEntityRenderer(context));
        EntityRendererRegistry.register(ModProjectiles.TOXIC_WATER_CLEANER_ENTITY, (context) -> new FlyingItemEntityRenderer(context));
    }
}

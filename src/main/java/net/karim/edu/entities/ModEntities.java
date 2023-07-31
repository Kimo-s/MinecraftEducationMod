package net.karim.edu.entities;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.karim.edu.EduChemMod;
import net.karim.edu.entities.ChemistZombie.ChemistZombie;
import net.karim.edu.entities.ChemistZombie.ChemistZombieRenderer;
import net.karim.edu.entities.FertilizerRobot.FertilizerRobot;
import net.karim.edu.entities.FertilizerRobot.FertilizerRobotRenderer;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<FertilizerRobot> FERTILIZER_ROBOT = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(EduChemMod.MOD_ID, "fertilizer_robot"),
                    FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FertilizerRobot::new)
                            .dimensions(EntityDimensions.fixed(0.75f,0.75f))
                            .build());

    public static final EntityType<ChemistZombie> CHEMIST_ZOMBIE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(EduChemMod.MOD_ID, "chemist_zombie"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChemistZombie::new)
                    .dimensions(EntityDimensions.fixed(0.6f,1.95f))
                    .build());

    public static void registerEntities(){
        EduChemMod.LOGGER.info("Registering the entities");
        FabricDefaultAttributeRegistry.register(FERTILIZER_ROBOT, FertilizerRobot.createMobAttributes());
        FabricDefaultAttributeRegistry.register(CHEMIST_ZOMBIE, ChemistZombie.setAttributes());
    }

    public static void registerClientRenderers(){
        EduChemMod.LOGGER.info("Registering the entities' renderers");
        EntityRendererRegistry.register(ModEntities.FERTILIZER_ROBOT, FertilizerRobotRenderer::new);
        EntityRendererRegistry.register(ModEntities.CHEMIST_ZOMBIE, ChemistZombieRenderer::new);
    }
}

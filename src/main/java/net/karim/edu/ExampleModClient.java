package net.karim.edu;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.karim.edu.block.ModBlocks;
import net.karim.edu.screen.ChemTableScreen;
import net.karim.edu.screen.DecomposerTableScreen;
import net.karim.edu.screen.DecomposerTableScreenHandler;
import net.karim.edu.screen.ModScreenHandlers;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;


public class ExampleModClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(ExampleMod.GREEN_FLAME, FlameParticle.Factory::new);

        EntityRendererRegistry.register(ExampleMod.rocketEntity, (context) -> new FlyingItemEntityRenderer(context));

        ScreenRegistry.register(ModScreenHandlers.CHEM_TABLE_SCREEN_HANDLER, ChemTableScreen::new);
        ScreenRegistry.register(ModScreenHandlers.DECOMPOSER_SCREEN_HANDLER, DecomposerTableScreen::new);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TOXIC_GAS, RenderLayer.getTranslucent());
    }
}

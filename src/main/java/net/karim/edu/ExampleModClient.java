package net.karim.edu;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.karim.edu.block.ModBlocks;
import net.karim.edu.block.blocks.BlueFireBlock;
import net.karim.edu.block.blocks.GenericFireBlock;
import net.karim.edu.block.blocks.GreenFireBlock;
import net.karim.edu.block.blocks.PurpleFireBlock;
import net.karim.edu.event.WorldTimeTickHandler;
import net.karim.edu.screen.ChemTableScreen;
import net.karim.edu.screen.DecomposerTableScreen;
import net.karim.edu.screen.ModScreenHandlers;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.util.profiling.jfr.event.ServerTickTimeEvent;

import static net.karim.edu.block.ModBlocks.*;


public class ExampleModClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(ExampleMod.GREEN_FLAME, FlameParticle.Factory::new);

        EntityRendererRegistry.register(ExampleMod.rocketEntity, (context) -> new FlyingItemEntityRenderer(context));

        ScreenRegistry.register(ModScreenHandlers.CHEM_TABLE_SCREEN_HANDLER, ChemTableScreen::new);
        ScreenRegistry.register(ModScreenHandlers.DECOMPOSER_SCREEN_HANDLER, DecomposerTableScreen::new);

        GenericFireBlock.registerDefaultFlammablesF((BlueFireBlock) BLUE_FIRE);
        GenericFireBlock.registerDefaultFlammablesF((GreenFireBlock) GREEN_FIRE);
        GenericFireBlock.registerDefaultFlammablesF((PurpleFireBlock) PURPLE_FIRE);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> (tintIndex == 0) ? BlueFireBlock.COLOR : BlueFireBlock.COLOR_BRIGHT, BLUE_FIRE);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> (tintIndex == 0) ? GreenFireBlock.COLOR : GreenFireBlock.COLOR_BRIGHT, GREEN_FIRE);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> (tintIndex == 0) ? PurpleFireBlock.COLOR : PurpleFireBlock.COLOR_BRIGHT, PURPLE_FIRE);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BLUE_FIRE, GREEN_FIRE, PURPLE_FIRE);


        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TOXIC_GAS, RenderLayer.getTranslucent());
    }
}

package net.karim.edu;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.karim.edu.screen.ChemTableScreen;
import net.karim.edu.screen.ModScreenHandlers;

public class ExampleModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {


        ScreenRegistry.register(ModScreenHandlers.CHEM_TABLE_SCREEN_HANDLER, ChemTableScreen::new);
    }
}

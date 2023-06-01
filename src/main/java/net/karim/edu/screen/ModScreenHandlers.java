package net.karim.edu.screen;

import net.karim.edu.EduChemMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;


public class ModScreenHandlers {
    public static ScreenHandlerType<ChemTableScreenHandler> CHEM_TABLE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER,
                    new Identifier(EduChemMod.MOD_ID, "chem_table"),
                    new ScreenHandlerType<>(ChemTableScreenHandler::new));

    public static ScreenHandlerType<DecomposerTableScreenHandler> DECOMPOSER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER,
                    new Identifier(EduChemMod.MOD_ID, "decomposer_table"),
                    new ScreenHandlerType<>(DecomposerTableScreenHandler::new));

}

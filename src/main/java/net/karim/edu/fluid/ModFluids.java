package net.karim.edu.fluid;

import net.karim.edu.EduChemMod;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModFluids {

    public static final FlowableFluid TOXIC_STILL = register("toxic_still", new ToxicFluid.Still());
    public static final FlowableFluid TOXIC_FLOWING = register("toxic_flowing", new ToxicFluid.Flowing());

    public static FlowableFluid  register(String name, FlowableFluid flowableFluid){
        return Registry.register(Registries.FLUID, new Identifier(EduChemMod.MOD_ID, name), flowableFluid);
    }
}

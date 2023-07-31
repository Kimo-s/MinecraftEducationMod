package net.karim.edu.entities.ChemistZombie;

import net.karim.edu.EduChemMod;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ChemistZombieRenderer extends GeoEntityRenderer<ChemistZombie> {


    public ChemistZombieRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ChemistZombieModel());
    }

    @Override
    public Identifier getTextureLocation(ChemistZombie animatable) {
        return new Identifier(EduChemMod.MOD_ID, "textures/entity/chemist_zombie.png");
    }
}

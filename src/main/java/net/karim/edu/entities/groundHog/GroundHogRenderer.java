package net.karim.edu.entities.groundHog;

import net.karim.edu.EduChemMod;
import net.karim.edu.entities.ChemistZombie.ChemistZombie;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GroundHogRenderer extends GeoEntityRenderer<GroundHog> {
    public GroundHogRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new GroundHogModel());
    }

    @Override
    public Identifier getTextureLocation(GroundHog animatable) {
        return new Identifier(EduChemMod.MOD_ID, "textures/entity/ground_hog.png");
    }
}

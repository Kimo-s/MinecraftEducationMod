package net.karim.edu.entities.FertilizerRobot;

import net.karim.edu.EduChemMod;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FertilizerRobotRenderer extends GeoEntityRenderer<FertilizerRobot> {


    public FertilizerRobotRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new FertilizerRobotModel());
    }

    @Override
    public Identifier getTextureLocation(FertilizerRobot animatable) {
        return new Identifier(EduChemMod.MOD_ID, "textures/entity/fertilizer_robot.png");
    }
}

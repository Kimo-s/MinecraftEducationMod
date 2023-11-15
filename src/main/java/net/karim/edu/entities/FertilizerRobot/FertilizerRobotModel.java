package net.karim.edu.entities.FertilizerRobot;

import net.karim.edu.EduChemMod;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FertilizerRobotModel extends GeoModel<FertilizerRobot> {


    @Override
    public Identifier getModelResource(FertilizerRobot animatable) {
        return new Identifier(EduChemMod.MOD_ID, "geo/fertilizer_robot2.geo.json");
    }

    @Override
    public Identifier getTextureResource(FertilizerRobot animatable) {
        return new Identifier(EduChemMod.MOD_ID, "textures/entity/fertilizer_robot2.png");
    }

    @Override
    public Identifier getAnimationResource(FertilizerRobot animatable) {
        return new Identifier(EduChemMod.MOD_ID, "animations/fertilizer_robot2.animation.json");
    }


}

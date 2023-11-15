package net.karim.edu.entities.FertilizerRobot;

import net.karim.edu.EduChemMod;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FertilizerRobotRenderer extends GeoEntityRenderer<FertilizerRobot> {


    public FertilizerRobotRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new FertilizerRobotModel());
    }

    @Override
    public Identifier getTextureLocation(FertilizerRobot animatable) {
        return new Identifier(EduChemMod.MOD_ID, "textures/entity/fertilizer_robot2.png");
    }


    @Override
    public void render(FertilizerRobot entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        poseStack.scale(0.5f,0.5f,0.5f);
        entityYaw = 180.0f;
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

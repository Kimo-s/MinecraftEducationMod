package net.karim.edu.entities.ChemistZombie;

import net.karim.edu.EduChemMod;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.DataTicket;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ChemistZombieModel extends GeoModel<ChemistZombie> {
    @Override
    public Identifier getModelResource(ChemistZombie animatable) {
        return new Identifier(EduChemMod.MOD_ID, "geo/chemist_zombie.geo.json");
    }

    @Override
    public Identifier getTextureResource(ChemistZombie animatable) {
        return new Identifier(EduChemMod.MOD_ID, "textures/entity/chemist_zombie.png");
    }

    @Override
    public Identifier getAnimationResource(ChemistZombie animatable) {
        return new Identifier(EduChemMod.MOD_ID, "animations/chemist_zombie.animation.json");
    }

    @Override
    public void setCustomAnimations(ChemistZombie animatable, long instanceId, AnimationState<ChemistZombie> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if(head != null){
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY((entityData.netHeadYaw() + 180.0f) * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}

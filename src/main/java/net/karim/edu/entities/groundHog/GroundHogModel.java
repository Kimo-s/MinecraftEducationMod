package net.karim.edu.entities.groundHog;

import net.karim.edu.EduChemMod;
import net.karim.edu.entities.ChemistZombie.ChemistZombie;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


class GroundHogModel extends GeoModel<GroundHog> {


    @Override
    public Identifier getModelResource(GroundHog animatable) {
        return new Identifier(EduChemMod.MOD_ID, "geo/ground_hog.geo.json");
    }

    @Override
    public Identifier getTextureResource(GroundHog animatable) {
        return new Identifier(EduChemMod.MOD_ID, "textures/entity/ground_hog.png");
    }

    @Override
    public Identifier getAnimationResource(GroundHog animatable) {
        return new Identifier(EduChemMod.MOD_ID, "animations/ground_hog.animation.json");
    }

    @Override
    public void setCustomAnimations(GroundHog animatable, long instanceId, AnimationState<GroundHog> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("Head");

        if(head != null){
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY((entityData.netHeadYaw()) * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
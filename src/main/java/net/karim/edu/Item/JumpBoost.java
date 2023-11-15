package net.karim.edu.Item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class JumpBoost extends Item {

    double jumpMultiplier = 1.0f;

    public JumpBoost(Settings settings, double jumpMultiplier) {
        super(settings);
        this.jumpMultiplier = jumpMultiplier;
    }

    public JumpBoost(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isUsedOnRelease(ItemStack stack) {
        return super.isUsedOnRelease(stack);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(user.isOnGround()){
            double d = jumpMultiplier;
            Vec3d vec3d = user.getVelocity();
            user.setVelocity(vec3d.x, d, vec3d.z);
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 100, 1000, false, false, true));

            if (user.isSprinting()) {
                float f = user.getYaw() * ((float)Math.PI / 180);
                user.setVelocity(user.getVelocity().add(-MathHelper.sin(f) * 0.5f, 0.0, MathHelper.cos(f) * 0.5f));
            }
            user.velocityDirty = true;
            user.startFallFlying();
        }
        return super.use(world, user, hand);
    }


}

package net.karim.edu.Item;

import net.karim.edu.projectile.ToxicWaterCleaner;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ToxicWaterCleanerPotion extends Item {

    public ToxicWaterCleanerPotion(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if(!world.isClient()){
            ToxicWaterCleaner proj = new ToxicWaterCleaner(world, user);
            proj.setItem(itemStack);
            proj.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5f, 0f);

            world.spawnEntity(proj);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
//        if (!user.getAbilities().creativeMode) {
//            itemStack.decrement(1); // decrements itemStack if user is not in creative mode
//        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}

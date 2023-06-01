package net.karim.edu.Item;

import net.karim.edu.block.ModBlocks;
import net.karim.edu.block.blocks.GenericFireBlock;
import net.karim.edu.block.blocks.GreenFireBlock;
import net.karim.edu.projectile.RocketEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class GenericBomb extends Item {

    private GenericFireBlock fireBlock;
    public GenericBomb(Item.Settings settings, GenericFireBlock fireBlock){
        super(settings);
        this.fireBlock = fireBlock;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH, SoundCategory.NEUTRAL, 0.5F, 1F);


        if(!world.isClient){
            RocketEntity rocket = new RocketEntity(world, user, fireBlock);
            rocket.setItem(itemStack);
            rocket.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5f, 0f);

            world.spawnEntity(rocket);

        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1); // decrements itemStack if user is not in creative mode
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
package net.karim.edu.Item;

import net.karim.edu.EduChemMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.karim.edu.Item.ModItems.NA2CO3_ITEM;
import static net.minecraft.fluid.Fluids.WATER;

public class MineralWater extends Item {

    public MineralWater(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient){
            return super.use(world, user, hand);
        }
        HitResult fluidHit =  user.raycast(5.0f, 0.0f, true);
        if(fluidHit.getType() == HitResult.Type.BLOCK){
            BlockPos blockPos = ((BlockHitResult)fluidHit).getBlockPos();
            FluidState fluidState = world.getFluidState(blockPos);
            if(fluidState.isOf(WATER)){
                EduChemMod.LOGGER.info("WE HIT WATERRRRRR");
                ItemStack itemStack = user.getStackInHand(hand);
                itemStack.decrement(1);
                ItemStack newItem = new ItemStack(NA2CO3_ITEM, 1);
                user.setStackInHand(hand, newItem);
            }
        }
        return super.use(world, user, hand);
    }
}

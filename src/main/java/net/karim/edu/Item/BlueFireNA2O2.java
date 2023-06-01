package net.karim.edu.Item;

import net.karim.edu.block.ModBlocks;
import net.karim.edu.block.blocks.BlueFireBlock;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;

public class BlueFireNA2O2 extends Item {


    public BlueFireNA2O2(Settings settings){
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        boolean fired = true;

        //GenericFireBlock.registerDefaultFlammables();
        if(!context.getWorld().isClient){
            BlockPos pos = context.getBlockPos().up();
            if(context.getWorld().isAir(context.getBlockPos().up())){
                BlockState state;
                state = ((BlueFireBlock)ModBlocks.BLUE_FIRE).getStateForPosition(context.getWorld(), pos);
                context.getWorld().setBlockState(pos, state, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
                context.getWorld().emitGameEvent((Entity)context.getPlayer(), GameEvent.BLOCK_PLACE, pos);
            } else {
                fired = false;
            }
        }


        context.getPlayer().incrementStat(Stats.USED.getOrCreateStat(this));
        if (!context.getPlayer().getAbilities().creativeMode && fired) {
            context.getStack().decrement(1); // decrements itemStack if user is not in creative mode
        }


        return ActionResult.SUCCESS;
    }
}
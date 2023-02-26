package net.karim.edu.Item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class element extends Item {

    public element(Item.Settings settings){
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(context.getWorld().isClient){
            BlockPos vec = context.getBlockPos();
            context.getPlayer().sendMessage(Text.of("Block hit at " + vec.getX() + " " + vec.getY() + " " + vec.getZ()));
        }

        return super.useOnBlock(context);
    }
}
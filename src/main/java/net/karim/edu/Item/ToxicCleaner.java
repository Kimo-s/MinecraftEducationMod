package net.karim.edu.Item;

import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.Registries;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class ToxicCleaner extends Item {

    public ToxicCleaner(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();

        if(world.getBlockState(context.getBlockPos()).getRegistryEntry().matchesKey(Registries.BLOCK.getEntry(Blocks.DIRT).getKey().get())){
            world.setBlockState(context.getBlockPos(), Blocks.GRASS_BLOCK.getDefaultState());
            world.markDirty(context.getBlockPos());
        }

        return super.useOnBlock(context);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}

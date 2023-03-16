package net.karim.edu.block.blocks;

import net.karim.edu.block.entity.ChemTableBlockEntity;
import net.karim.edu.block.entity.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LockBlock extends Block {

    private ItemStack keyItem;
    public LockBlock(Settings settings, ItemStack keyItem) {
        this(settings);
        this.keyItem = keyItem;
    }

    public LockBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            ItemStack handItem = player.getEquippedStack(EquipmentSlot.MAINHAND);
            if(handItem.isItemEqual(keyItem)){

                world.breakBlock(pos.west(), false);
                world.breakBlock(pos.east(), false);
                world.breakBlock(pos.west().down(), false);
                world.breakBlock(pos.east().down(), false);
                world.breakBlock(pos.west().up(), false);
                world.breakBlock(pos.east().up(), false);
                world.breakBlock(pos.up(), false);
                world.breakBlock(pos.down(), false);

                world.breakBlock(pos, false);

            }
        }

        return ActionResult.SUCCESS;
    }


}

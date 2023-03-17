package net.karim.edu.block.blocks;

import net.karim.edu.block.entity.ChemTableBlockEntity;
import net.karim.edu.block.entity.LockBlockEntity;
import net.karim.edu.block.entity.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LockBlock extends Block implements BlockEntityProvider{

    public LockBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            LockBlockEntity entity = (LockBlockEntity) world.getBlockEntity(pos);

            String handItem = player.getEquippedStack(EquipmentSlot.MAINHAND).getItem().getName().getString();

            if(entity.lockDecided) {
                if (handItem.equals(entity.keyItem)) {

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
                //player.sendMessage(Text.of("KeyItem = " + entity.keyItem + " hand Item = " + handItem), true);
            } else {
                entity.keyItem = handItem;
                player.sendMessage(Text.of("Lock key sat into " + handItem), true);
                entity.lockDecided = true;
            }
        }

        return ActionResult.SUCCESS;
    }



    //create the block ene
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LockBlockEntity(pos, state);
    }
}

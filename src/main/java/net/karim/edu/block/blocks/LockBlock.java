package net.karim.edu.block.blocks;

import net.karim.edu.EduChemMod;
import net.karim.edu.Item.ReadableMessage;
import net.karim.edu.block.entity.LockBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;


public class LockBlock extends Block implements BlockEntityProvider {

    private static final DispenserBehavior BEHAVIOR = new ItemDispenserBehavior();

    public LockBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            LockBlockEntity entity = (LockBlockEntity) world.getBlockEntity(pos);
            assert entity != null;

            Item heldItem = player.getEquippedStack(EquipmentSlot.MAINHAND).getItem();

            RegistryKey<Item> itemHeldKey;
            if(Registries.ITEM.getKey(heldItem).isPresent()){
                itemHeldKey = Registries.ITEM.getKey(heldItem).get();
                if(heldItem instanceof ReadableMessage){
                    EduChemMod.LOGGER.info("Got a hand with a message paper " + itemHeldKey.getValue());
                    if(!entity.foundMessage){
                        entity.message = String.valueOf(itemHeldKey.getValue());
                        entity.foundMessage = true;
                    } else {
                        entity.hintItems.add(String.valueOf(itemHeldKey.getValue()));
                    }
                    return ActionResult.SUCCESS;
                }
            }

            if(player.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
                if(entity.foundMessage){
                    Vec3d posDir = player.getBlockPos().toCenterPos().subtract(pos.toCenterPos());
                    Direction dir = Direction.getFacing(posDir.getX(), posDir.getY(), posDir.getZ());
                    ItemDispenserBehavior.spawnItem(world, Registries.ITEM.get(new Identifier(entity.message)).getDefaultStack(), 6, dir, pos.toCenterPos());
                    return ActionResult.SUCCESS;
                }
                return ActionResult.SUCCESS;
            }

            String handItem = player.getEquippedStack(EquipmentSlot.MAINHAND).getItem().getName().getString();

            if(entity.lockDecided) {
                if(world.getTime() - entity.lastTickUsed > 20){
                    if(handItem.equals(entity.keyItem)) {

                        world.breakBlock(pos.west(), false);
                        world.breakBlock(pos.east(), false);
                        world.breakBlock(pos.west().down(), false);
                        world.breakBlock(pos.east().down(), false);
                        world.breakBlock(pos.west().up(), false);
                        world.breakBlock(pos.east().up(), false);
                        world.breakBlock(pos.up(), false);
                        world.breakBlock(pos.down(), false);

                        world.breakBlock(pos, false);
                    } else {
                        player.sendMessage(Text.of("Wrong key"), true);
                        int attemptsBeforeHint = 3;
                        EduChemMod.LOGGER.info("Current number of failed attempts: " + entity.failedTries);
                        if(entity.failedTries % attemptsBeforeHint == 0) {
                            int ind = entity.failedTries / attemptsBeforeHint;
                            EduChemMod.LOGGER.info("ind = " + ind);
                            if (ind < entity.hintItems.size()) {
                                Vec3d posDir = player.getBlockPos().toCenterPos().subtract(pos.toCenterPos());
                                Direction dir = Direction.getFacing(posDir.getX(), posDir.getY(), posDir.getZ());
                                ItemDispenserBehavior.spawnItem(world, Registries.ITEM.get(new Identifier(entity.hintItems.get(ind))).getDefaultStack(), 6, dir, pos.toCenterPos());
                            } else {
                                EduChemMod.LOGGER.info("Reset the failed tried counter");
                            }
                        }
                        if(entity.failedTries / attemptsBeforeHint < entity.hintItems.size()){
                            entity.failedTries += 1;
                        } else {
                            entity.failedTries = 0;
                        }
                    }
                    entity.lastTickUsed = world.getTime();
                } else {
                    player.sendMessage(Text.of("Please wait for the cool down"), true);
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

package net.karim.edu.screen;

import net.karim.edu.ExampleMod;
import net.karim.edu.screen.slot.ModResultSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

public class DecomposerTableScreenHandler extends ScreenHandler {
    private final  Inventory inventory;

    public DecomposerTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(10));
    }

    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        for(int i = 1; i < 10; i++){
            if(slotIndex == i){
                this.getSlot(0).setStack(ItemStack.EMPTY);
            }
        }

        if(slotIndex == 0){
            for(int i = 1; i < 10; i++) {
                this.getSlot(i).setStack(ItemStack.EMPTY);
            }
        }
        super.onSlotClick(slotIndex, button, actionType, player);
    }

    public DecomposerTableScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.DECOMPOSER_SCREEN_HANDLER, syncId);
        checkSize(inventory, 10);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);

        int offset = 10;

        this.addSlot(new Slot(inventory, 0, 9, 50-offset));
        this.addSlot(new ModResultSlot(inventory, 1, 56, 22-offset));
        this.addSlot(new ModResultSlot(inventory, 2, 96, 22-offset));
        this.addSlot(new ModResultSlot(inventory, 3, 131, 22-offset));
        this.addSlot(new ModResultSlot(inventory, 4, 56, 50-offset));
        this.addSlot(new ModResultSlot(inventory, 5, 96, 50-offset));
        this.addSlot(new ModResultSlot(inventory, 6, 131, 50-offset));
        this.addSlot(new ModResultSlot(inventory, 7, 56, 78-offset));
        this.addSlot(new ModResultSlot(inventory, 8, 96, 78-offset));
        this.addSlot(new ModResultSlot(inventory, 9, 131, 78-offset));

        addPlayerInventory(playerInventory, 0, 8);
        addPlayerHotbar(playerInventory, 0, 8);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory, int offsetX, int offsetY) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18 + offsetX, 86 + i * 18 + offsetY));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory, int offsetX, int offsetY) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18 + offsetX, 144 + offsetY));
        }
    }
}

package net.karim.edu.screen;

import net.karim.edu.screen.slot.ModResultSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class ChemTableScreenHandler extends ScreenHandler {
    private final  Inventory inventory;

    public ChemTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(4));
    }

    public ChemTableScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.CHEM_TABLE_SCREEN_HANDLER, syncId);
        checkSize(inventory, 4);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);


        this.addSlot(new Slot(inventory, 0, 19, 34));
        this.addSlot(new Slot(inventory, 1, 58, 34));
        this.addSlot(new ModResultSlot(inventory, 2, 108, 34));
        this.addSlot(new ModResultSlot(inventory, 3, 143, 34));

        addPlayerInventory(playerInventory, 0, 0);
        addPlayerHotbar(playerInventory, 0, 0);
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

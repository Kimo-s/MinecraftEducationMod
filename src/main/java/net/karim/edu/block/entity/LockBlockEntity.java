package net.karim.edu.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class LockBlockEntity extends BlockEntity {

    public String keyItem = "nothing";
    public String message = "nothing";
    public boolean lockDecided = false;
    public boolean foundMessage = false;
    public long lastTickUsed = 0;
    public int failedTries = 0;
    public ArrayList<String> hintItems;


    public LockBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LOCK_BLOCK_ENTITY, pos, state);
        hintItems = new ArrayList<>();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        lockDecided = nbt.getBoolean("lockDecided");
        keyItem = nbt.getString("keyItem");
        foundMessage = nbt.getBoolean("foundMessage");
        message = nbt.getString("message");
        int hintItemsSize = nbt.getInt("hintItemsSize");
        for (int i = 0; i < hintItemsSize; i++) {
            hintItems.add(nbt.getString("hintItems"+i));
        }
        super.readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("lockDecided", lockDecided);
        nbt.putString("keyItem", keyItem);
        nbt.putBoolean("foundMessage", foundMessage);
        nbt.putString("message", message);
        nbt.putInt("hintItemsSize", hintItems.size());
        for(int i = 0 ; i < hintItems.size(); i++){
            nbt.putString("hintItems"+i, hintItems.get(i));
        }
    }
}

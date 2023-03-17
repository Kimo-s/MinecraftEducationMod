package net.karim.edu.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class LockBlockEntity extends BlockEntity {

    public String keyItem;
    public boolean lockDecided = false;
    public LockBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LOCK_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        lockDecided = nbt.getBoolean("lockDecided");
        keyItem = nbt.getString("keyItem");
        super.readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("lockDecided", lockDecided);
        nbt.putString("keyItem", keyItem);
    }
}

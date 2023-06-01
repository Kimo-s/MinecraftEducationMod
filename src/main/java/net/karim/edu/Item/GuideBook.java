package net.karim.edu.Item;

import net.karim.edu.EduChemMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.world.World;

public class GuideBook extends WrittenBookItem {
    public GuideBook(Settings settings) {
        super(settings);
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        NbtCompound data = new NbtCompound();
        data.putString(TITLE_KEY, "Karim Salem");

        NbtList pages = new NbtList();
        pages.add(NbtString.of("Testing this"));
        pages.add(NbtString.of("Test 2"));

        data.put(PAGES_KEY, pages);
        data.putString(TITLE_KEY, "WorldOfElements Guide");

        stack.setNbt(data);

        EduChemMod.LOGGER.info("Made new book stack!");
    }


}

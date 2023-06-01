package net.karim.edu.block.entity;

import net.karim.edu.EduChemMod;
import net.karim.edu.block.ImplementedInventory;
import net.karim.edu.recipe.DecomposerTableRecipe;
import net.karim.edu.screen.DecomposerTableScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;

public class DecomposerTableBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(10, ItemStack.EMPTY);

    public DecomposerTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DECOMPOSER_TABLE_ENTITY, pos, state);
    }

    @Override
    public Text getDisplayName() {
        return Text.of("Element Decomposer");
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new DecomposerTableScreenHandler(syncId, inv, this);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
    }

    @Override
    public void onOpen(PlayerEntity player) {

        ImplementedInventory.super.onOpen(player);
    }


    public static void tick(World world, BlockPos pos, BlockState state, DecomposerTableBlockEntity entity) {
        if(world.isClient()){
            Random r = new Random();
            world.addParticle(ParticleTypes.EFFECT, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, r.nextDouble(), r.nextDouble(), r.nextDouble());
            return;
        }

        if(hasRecipe(entity)){
            craftItem(entity);
        }

    }



    private static void craftItem(DecomposerTableBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }


        Optional<DecomposerTableRecipe> recipe = entity.getWorld().getRecipeManager()
                .getFirstMatch(DecomposerTableRecipe.Type.INSTANCE, inventory, entity.getWorld());

            if(hasRecipe(entity)) {
                int intputStack = entity.getStack(0).getCount();

                for(int i = 1; i < 10; i++){
                    entity.setStack(i, new ItemStack(recipe.get().getOutputArr().get(i-1).getItem(),
                            (entity.getStack(i-1).getCount() + intputStack)));
                    EduChemMod.LOGGER.info("Crafting item " + recipe.get().getOutputArr().get(i-1).getItem() + " Count: " + (entity.getStack(i-1).getCount() + intputStack));
                }


                BlockPos pos = entity.getPos();
                PlayerEntity player= entity.getWorld().getClosestPlayer(pos.getX()*1.0f, pos.getY()*1.0f, pos.getZ()*1.0f, 10, false);
                if(player != null){
                    player.addExperience(recipe.get().getExp());
                }
            }
    }

    private static boolean hasRecipe(DecomposerTableBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<DecomposerTableRecipe> match = entity.getWorld().getRecipeManager()
                .getFirstMatch(DecomposerTableRecipe.Type.INSTANCE, inventory, entity.getWorld());

        boolean toRet = match.isPresent();
        if(toRet){
            for(int i = 0; i < match.get().getOutputArr().size(); i++){
                toRet = toRet && canInsertAmountIntoOutputSlot(inventory, i+1) && canInsertItemIntoOutputSlot(inventory, match.get().getOutput().getItem(), i+2);

            }
        }

        return toRet;
    }


    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, Item output, int index) {
        return inventory.getStack(index).getItem() == output || inventory.getStack(index).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory, int index) {
        return inventory.getStack(index).getMaxCount() > inventory.getStack(index).getCount();
    }
}

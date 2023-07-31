package net.karim.edu.block.entity;

import net.karim.edu.block.ImplementedInventory;
import net.karim.edu.recipe.ChemTableRecipe;
import net.karim.edu.screen.ChemTableScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;

public class ChemTableBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);


    public ChemTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHEM_TABLE_ENTITY, pos, state);
    }

    @Override
    public Text getDisplayName() {
        return Text.of("Chemistry Table");
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new ChemTableScreenHandler(syncId, inv, this);
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


    public static void tick(World world, BlockPos pos, BlockState state, ChemTableBlockEntity entity) {
        if(world.isClient()){
            Random r = new Random();
            world.addParticle(ParticleTypes.EFFECT, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, r.nextDouble(), r.nextDouble(), r.nextDouble());
            return;
        }

        if(hasRecipe(entity) ){
            craftItem(entity);

        }
    }

    private static void craftItem(ChemTableBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }


        Optional<ChemTableRecipe> recipe = entity.getWorld().getRecipeManager()
                .getFirstMatch(ChemTableRecipe.Type.INSTANCE, inventory, entity.getWorld());

        if(hasRecipe(entity)) {
            int stackOneCount = entity.getStack(0).getCount();
            int stackTwoCount = entity.getStack(1).getCount();
            int stacksToRemove;
            if(stackTwoCount == 0){
                stacksToRemove = stackOneCount;
            } else if (stackOneCount == 0) {
                stacksToRemove = stackTwoCount;
            } else {
                stacksToRemove = Math.min(stackOneCount, stackTwoCount);
            }

            ItemStack stack = new ItemStack(recipe.get().getOutputArr().get(0).getItem(), stacksToRemove);

            if(stack.isEnchantable() && recipe.get().getEnchantmentList().size() != 0) {
                DefaultedList<Enchantment> enchantmentList = recipe.get().getEnchantmentList();
                for(int i = 0; i < enchantmentList.size(); i ++) {
                    stack.addEnchantment(enchantmentList.get(i), 3);
                }
                entity.setStack(2, stack);
                entity.setStack(3, new ItemStack(recipe.get().getOutputArr().get(1).getItem(), stacksToRemove));
            } else {
                entity.setStack(2, new ItemStack(recipe.get().getOutputArr().get(0).getItem(), stacksToRemove));
                entity.setStack(3, new ItemStack(recipe.get().getOutputArr().get(1).getItem(), stacksToRemove));
            }


           // ExampleMod.LOGGER.info("Crafting chem table recipe with this many stacks: " + stacksToRemove);

//            BlockPos pos = entity.getPos();
//            PlayerEntity player= entity.getWorld().getClosestPlayer(pos.getX()*1.0f, pos.getY()*1.0f, pos.getZ()*1.0f, 10, false);
//            if(player != null){
//                player.addExperience(recipe.get().getExp());
//            }
        }
    }

    private static boolean hasRecipe(ChemTableBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<ChemTableRecipe> match = entity.getWorld().getRecipeManager()
                .getFirstMatch(ChemTableRecipe.Type.INSTANCE, inventory, entity.getWorld());

        boolean toRet = match.isPresent();
        if(toRet){
            for(int i = 2; i < 4; i++){
                toRet = toRet && canInsertAmountIntoOutputSlot(inventory, i) && canInsertItemIntoOutputSlot(inventory, match.get().getOutput().getItem(), i);

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

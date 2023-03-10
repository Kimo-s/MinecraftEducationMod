package net.karim.edu.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class DecomposerTableRecipe implements Recipe<SimpleInventory> {

    private final Identifier id;
    private final ItemStack output;
    private final DefaultedList<Ingredient> recipeItems;
    private final DefaultedList<ItemStack> outputs;
    private final int exp;


    public DecomposerTableRecipe(Identifier id, ItemStack output, DefaultedList<Ingredient> recipeItems, DefaultedList<ItemStack> outputs, int exp) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.outputs = outputs;
        this.exp = exp;
    }



    public DefaultedList<ItemStack> getOutputArr() {
        return outputs;
    }

    public int getExp() {
        return exp;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if(world.isClient()){
            return false;
        }


        return recipeItems.get(0).test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(SimpleInventory inventory) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return output.copy();
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<DecomposerTableRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "decomposer_table";
    }

    public static class Serializer implements RecipeSerializer<DecomposerTableRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "decomposer_table";
        // this is the name given in the json file

        @Override
        public DecomposerTableRecipe read(Identifier id, JsonObject json) {
            JsonArray outputsJson = JsonHelper.getArray(json, "output");
            DefaultedList<ItemStack> outputs = DefaultedList.ofSize(10, ItemStack.EMPTY);

            for (int i = 0; i < outputsJson.size(); i++) {
                outputs.set(i, Ingredient.fromJson(outputsJson.get(i)).getMatchingStacks()[0]);
            }

            ItemStack output = Ingredient.fromJson(outputsJson.get(0)).getMatchingStacks()[0];

            JsonArray ingredients = JsonHelper.getArray(json, "ingredients");
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(1, Ingredient.EMPTY);

            for (int i = 0; i < ingredients.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            JsonElement xpJson = json.get("exp");
            int exp = 0;
            if(xpJson != null && !xpJson.isJsonNull()){
                exp = xpJson.getAsInt();
            }

            return new DecomposerTableRecipe(id, output, inputs, outputs, exp);
        }

        @Override
        public DecomposerTableRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<ItemStack> outputs = DefaultedList.ofSize(buf.readInt(), ItemStack.EMPTY);

            for (int i = 0; i < outputs.size(); i++) {
                outputs.set(i, Ingredient.fromPacket(buf).getMatchingStacks()[0]);
            }

            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromPacket(buf));
            }

            ItemStack output = buf.readItemStack();
            int exp = 0;
            if(!output.isEmpty()){
                exp = buf.readInt();
            }

            return new DecomposerTableRecipe(id, output, inputs, outputs, exp);
        }

        @Override
        public void write(PacketByteBuf buf, DecomposerTableRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.write(buf);
            }
            buf.writeItemStack(recipe.getOutput());
        }


    }
}

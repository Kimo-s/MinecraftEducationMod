package net.karim.edu.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.TeamS2CPacket;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class ChemTableRecipe implements Recipe<SimpleInventory> {

    private final Identifier id;
    private final ItemStack output;
    private final DefaultedList<Ingredient> recipeItems;
    private final DefaultedList<ItemStack> outputs;
    private final int exp;


    public ChemTableRecipe(Identifier id, ItemStack output, DefaultedList<Ingredient> recipeItems, DefaultedList<ItemStack> outputs,
                           int exp) {
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


        return (recipeItems.get(0).test(inventory.getStack(0))
                || recipeItems.get(0).test(inventory.getStack(1)))
                && (recipeItems.get(1).test(inventory.getStack(0))
                || recipeItems.get(1).test(inventory.getStack(1)))
                && !(inventory.getStack(0).isEmpty() && inventory.getStack(1).isEmpty());
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

    public static class Type implements RecipeType<ChemTableRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "chem_table";
    }

    public static class Serializer implements RecipeSerializer<ChemTableRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "chem_table";
        // this is the name given in the json file

        @Override
        public ChemTableRecipe read(Identifier id, JsonObject json) {
            JsonArray outputsJson = JsonHelper.getArray(json, "output");
            DefaultedList<ItemStack> outputs = DefaultedList.ofSize(2, ItemStack.EMPTY);

            for (int i = 0; i < outputsJson.size(); i++) {
                outputs.set(i, Ingredient.fromJson(outputsJson.get(i)).getMatchingStacks()[0]);
            }

            ItemStack output = Ingredient.fromJson(outputsJson.get(0)).getMatchingStacks()[0];

            JsonArray ingredients = JsonHelper.getArray(json, "ingredients");
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(2, Ingredient.EMPTY);

            for (int i = 0; i < ingredients.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            JsonElement xpJson = json.get("exp");
            int exp = 0;
            if(xpJson != null && !xpJson.isJsonNull()){
                exp = xpJson.getAsInt();
            }

//            JsonArray countArr = JsonHelper.getArray(json, "inputCount");
//            DefaultedList<Integer> counts = DefaultedList.ofSize(2, 0);
//            for(int i = 0; i < ingredients.size(); i++){
//                counts.set(i, countArr.getAsInt());
//            }

            return new ChemTableRecipe(id, output, inputs, outputs, exp);
        }

        @Override
        public ChemTableRecipe read(Identifier id, PacketByteBuf buf) {
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

            return new ChemTableRecipe(id, output, inputs, outputs, exp);
        }

        @Override
        public void write(PacketByteBuf buf, ChemTableRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.write(buf);
            }
            buf.writeItemStack(recipe.getOutput());
        }


    }
}

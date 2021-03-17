package com.LogicalDom.vulcan.recipies;

import com.LogicalDom.vulcan.Vulcan;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.io.Console;

public class DamageToolRecipe extends ForgeRegistryEntry<IRecipeSerializer<?>> implements ICraftingRecipe {

    //Testing Code
    private static final Console console = System.console();

    private static final DamageToolRecipe.Serializer SERIALIZER = new DamageToolRecipe.Serializer();

    private static final int MAX_HEIGHT= 3;
    private static final int MAX_WIDTH = 3;

    private final NonNullList<Ingredient> recipeItems;
    private final ItemStack recipeOutput;
    private final ItemStack toolItem;
    private final ResourceLocation id;
    private final String group;

    public DamageToolRecipe(ResourceLocation idIn, String groupIn, ItemStack recipeOutputIn, NonNullList<Ingredient> recipeItemsIn, ItemStack toolItemIn) {
        this.id = idIn;
        this.group = groupIn;
        this.recipeItems = recipeItemsIn;
        this.recipeOutput = recipeOutputIn;
        this.toolItem = toolItemIn;
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        NonNullList<ItemStack> invItems = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            invItems.set(i, inv.getStackInSlot(i));
        }
        return (RecipeMatcher.findMatches(invItems, recipeItems) != null);
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        return this.recipeOutput;
    }

    @Override
    public boolean canFit(int width, int height) {
        return recipeItems.size() <= (width * height);
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput.copy();
    }

    // I'm trying to damage the tool, but I don't know how to get the
    // ServerPlayerEntity needed to damage the tool.
    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
        NonNullList<ItemStack> remainingItems = NonNullList.create();
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            if(inv.getStackInSlot(i).isItemEqualIgnoreDurability(toolItem)) {
                ItemStack item = inv.getStackInSlot(i);
                item.getItem().setDamage(item, item.getDamage() - 1);
                remainingItems.add(item);
            }
        }
        return remainingItems;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static ItemStack deserializeItem(JsonObject object) {
        String itemString = JSONUtils.getString(object, "item");
        Item item = Registry.ITEM.getOptional(new ResourceLocation(itemString)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + itemString + "'");
        });
        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            int i = JSONUtils.getInt(object, "count", 1);
            return CraftingHelper.getItemStack(object, true);
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<DamageToolRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation(Vulcan.MOD_ID, "crafting_damage_tool");
        public DamageToolRecipe read(ResourceLocation recipeId, JsonObject json) {
            String s = JSONUtils.getString(json, "group", "");
            NonNullList<Ingredient> nonnulllist = readIngredients(JSONUtils.getJsonArray(json, "ingredients"));
            nonnulllist.add(readIngredient(JSONUtils.getJsonObject(json, "tool")));
            for(Ingredient ingredient : nonnulllist) {
                System.out.println(ingredient.toString());
            }
            if (nonnulllist.isEmpty()) {
                throw new JsonParseException("No ingredients for damage tool recipe");
            } else if (nonnulllist.size() > DamageToolRecipe.MAX_WIDTH * DamageToolRecipe.MAX_HEIGHT) {
                throw new JsonParseException("Too many ingredients for damage tool recipe the max is " + (DamageToolRecipe.MAX_WIDTH * DamageToolRecipe.MAX_HEIGHT));
            } else {
                ItemStack resultItemStack = DamageToolRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
                ItemStack toolItem = DamageToolRecipe.deserializeItem(JSONUtils.getJsonObject(json, "tool"));
                return new DamageToolRecipe(recipeId, s, resultItemStack, nonnulllist, toolItem);
            }
        }

        private static NonNullList<Ingredient> readIngredients(JsonArray ingredientArray) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();

            for(int i = 0; i < ingredientArray.size(); ++i) {
                Ingredient ingredient = Ingredient.deserialize(ingredientArray.get(i));
                if (!ingredient.hasNoMatchingItems()) {
                    nonnulllist.add(ingredient);
                }
            }

            return nonnulllist;
        }

        private static Ingredient readIngredient(JsonObject jsonIngredient) {
            Ingredient ingredient = Ingredient.deserialize(jsonIngredient);
            if (!ingredient.hasNoMatchingItems()) {
                return ingredient;
            }
            return null;
        }

        public DamageToolRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            String s = buffer.readString(32767);
            int i = buffer.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

            for(int j = 0; j < nonnulllist.size(); ++j) {
                nonnulllist.set(j, Ingredient.read(buffer));
            }

            ItemStack toolItem = buffer.readItemStack();
            ItemStack result = buffer.readItemStack();
            return new DamageToolRecipe(recipeId, s, result, nonnulllist, toolItem);
        }

        public void write(PacketBuffer buffer, DamageToolRecipe recipe) {
            buffer.writeString(recipe.group);
            buffer.writeVarInt(recipe.recipeItems.size());

            for(Ingredient ingredient : recipe.recipeItems) {
                ingredient.write(buffer);
            }

            buffer.writeItemStack(recipe.toolItem);
            buffer.writeItemStack(recipe.recipeOutput);
        }
    }


}

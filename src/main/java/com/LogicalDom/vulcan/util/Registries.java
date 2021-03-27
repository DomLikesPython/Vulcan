package com.LogicalDom.vulcan.util;

import com.LogicalDom.vulcan.Vulcan;
import com.LogicalDom.vulcan.blocks.*;
import com.LogicalDom.vulcan.containers.ForgedSteelAnvilContainer;
import com.LogicalDom.vulcan.enums.ModArmorMaterial;
import com.LogicalDom.vulcan.enums.ModItemTier;
import com.LogicalDom.vulcan.items.ForgeHammer;
import com.LogicalDom.vulcan.items.StoneHammer;
import com.LogicalDom.vulcan.recipies.DamageToolRecipe;
import com.LogicalDom.vulcan.tileentities.ForgedSteelAnvilTileEntity;
import net.minecraft.block.Block;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("UNUSED_DECLARATION")
public class Registries {

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINER_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CUSTOM_CRAFTING_RECIPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // Registries
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Vulcan.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Vulcan.MOD_ID);
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, Vulcan.MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Vulcan.MOD_ID);
    public static final DeferredRegister<IRecipeSerializer<?>> CUSTOM_CRAFTING_RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Vulcan.MOD_ID);

    // Items and Block Items
    public static final RegistryObject<Item> STONE_HAMMER = ITEMS.register("stone_hammer", StoneHammer::new);
    public static final RegistryObject<Item> COAL_DUST = ITEMS.register("coal_dust", ItemBase::new);
    public static final RegistryObject<Item> CARBON_ENRICHED_IRON = ITEMS.register("carbon_enriched_iron", ItemBase::new);
    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", ItemBase::new);
    public static final RegistryObject<Item> FORGED_STEEL_INGOT = ITEMS.register("forged_steel_ingot", ItemBase::new);
    public static final RegistryObject<SwordItem> FORGED_STEEL_SWORD = ITEMS.register("forged_steel_sword", () -> {
        return new SwordItem(ModItemTier.FORGED_STEEL, 2, -2.4F, (new Item.Properties()).group(Vulcan.TAB));
    });
    public static final RegistryObject<PickaxeItem> FORGED_STEEL_PICKAXE = ITEMS.register("forged_steel_pickaxe", () -> {
        return new PickaxeItem(ModItemTier.FORGED_STEEL, 0, -2.8F, (new Item.Properties()).group(Vulcan.TAB));
    });
    public static final RegistryObject<AxeItem> FORGED_STEEL_AXE = ITEMS.register("forged_steel_axe", () -> {
        return new AxeItem(ModItemTier.FORGED_STEEL, 4.0F, -3.0F, (new Item.Properties()).group(Vulcan.TAB));
    });
    public static final RegistryObject<ShovelItem> FORGED_STEEL_SHOVEL = ITEMS.register("forged_steel_shovel", () -> {
        return new ShovelItem(ModItemTier.FORGED_STEEL, 0.0F, -2.8F, (new Item.Properties()).group(Vulcan.TAB));
    });
    public static final RegistryObject<ArmorItem> FORGED_STEEL_HELMET = ITEMS.register("forged_steel_helmet", () -> {
        return new ArmorItem(ModArmorMaterial.FORGED_STEEL, EquipmentSlotType.HEAD, (new Item.Properties()).group(Vulcan.TAB));
    });
    public static final RegistryObject<ArmorItem> FORGED_STEEL_CHESTPLATE = ITEMS.register("forged_steel_chestplate", () -> {
        return new ArmorItem(ModArmorMaterial.FORGED_STEEL, EquipmentSlotType.CHEST, (new Item.Properties()).group(Vulcan.TAB));
    });
    public static final RegistryObject<ArmorItem> FORGED_STEEL_LEGGINGS = ITEMS.register("forged_steel_leggings", () -> {
        return new ArmorItem(ModArmorMaterial.FORGED_STEEL, EquipmentSlotType.LEGS, (new Item.Properties()).group(Vulcan.TAB));
    });
    public static final RegistryObject<ArmorItem> FORGED_STEEL_BOOTS = ITEMS.register("forged_steel_boots", () -> {
        return new ArmorItem(ModArmorMaterial.FORGED_STEEL, EquipmentSlotType.FEET, (new Item.Properties()).group(Vulcan.TAB));
    });
    public static final RegistryObject<Item> STEEL_BLOCK_ITEM = ITEMS.register("steel_block", () -> {
        return new BlockItemBase((Block) Registries.STEEL_BLOCK.get(), (new Item.Properties()).group(Vulcan.TAB));
    });
    public static final RegistryObject<Item> FORGED_STEEL_BLOCK_ITEM = ITEMS.register("forged_steel_block", () -> {
        return new BlockItemBase((Block) Registries.FORGED_STEEL_BLOCK.get(), (new Item.Properties()).group(Vulcan.TAB));
    });
    public static final RegistryObject<Item> FORGED_STEEL_ANVIL_ITEM = ITEMS.register("forged_steel_anvil", () -> {
        return new BlockItemBase((Block) Registries.FORGED_STEEL_ANVIL.get(), (new Item.Properties()).group(Vulcan.TAB));
    });

    public static final RegistryObject<Item> HEARTH_ITEM = ITEMS.register("hearth", () -> {
        return new BlockItemBase((Block) Registries.HEARTH.get(), (new Item.Properties()).group(Vulcan.TAB));
    });

    public static final RegistryObject<Item> BASALT_BRICKS_ITEM = ITEMS.register("basalt_bricks", () -> {
        return new BlockItemBase((Block) Registries.BASALT_BRICKS.get(), (new Item.Properties().group(Vulcan.TAB)));
    });

    public static final RegistryObject<Item> BASALT_TILES_ITEM = ITEMS.register("basalt_tiles", () -> {
        return new BlockItemBase((Block) Registries.BASALT_TILES.get(), (new Item.Properties().group(Vulcan.TAB)));
    });

    public static final RegistryObject<Item> CRACKED_BASALT_BRICKS_ITEM = ITEMS.register("cracked_basalt_bricks", () -> {
        return new BlockItemBase((Block) Registries.CRACKED_BASALT_BRICKS.get(), (new Item.Properties().group(Vulcan.TAB)));
    });

    public static final RegistryObject<Item> FORGE_HAMMER = ITEMS.register("forge_hammer", ForgeHammer::new);

    public static final RegistryObject<Item> HEATING_COIL_ITEM = ITEMS.register("heating_coil", () -> {
        return new BlockItemBase((Block) Registries.HEATING_COIL.get(), (new Item.Properties().group(Vulcan.TAB)));
    });

    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test_item", ItemBase::new);
    public static final RegistryObject<Item> CAKE = ITEMS.register("cake", ItemBase::new);

    // Blocks
    public static final RegistryObject<Block> STEEL_BLOCK = BLOCKS.register("steel_block", SteelBlock::new);
    public static final RegistryObject<Block> FORGED_STEEL_BLOCK = BLOCKS.register("forged_steel_block", ForgedSteelBlock::new);
    public static final RegistryObject<Block> FORGED_STEEL_ANVIL = BLOCKS.register("forged_steel_anvil", ForgedSteelAnvil::new);

    public static final RegistryObject<Block> HEARTH = BLOCKS.register("hearth", Hearth::new);

    public static final RegistryObject<Block> BASALT_BRICKS = BLOCKS.register("basalt_bricks", BasaltBricks::new);
    public static final RegistryObject<Block> BASALT_TILES = BLOCKS.register("basalt_tiles", BasaltTiles::new);
    public static final RegistryObject<Block> CRACKED_BASALT_BRICKS = BLOCKS.register("cracked_basalt_bricks", CrackedBasaltBricks::new);

    public static final RegistryObject<Block> HEATING_COIL = BLOCKS.register("heating_coil", HeatingCoil::new);

    // Container Types
    public static final RegistryObject<ContainerType<ForgedSteelAnvilContainer>> FORGED_STEEL_ANVIL_CONTAINER_TYPE = CONTAINER_TYPES.register("forged_steel_anvil", () -> IForgeContainerType.create(ForgedSteelAnvilContainer::new));

    // Tile Entity Types
    public static final RegistryObject<TileEntityType<ForgedSteelAnvilTileEntity>> FORGED_STEEL_ANVIL_TILE_ENTITY_TYPE = TILE_ENTITY_TYPES.register("forged_steel_anvil", () -> TileEntityType.Builder.create(ForgedSteelAnvilTileEntity::new, Registries.FORGED_STEEL_ANVIL.get()).build(null));

    // Custom Recipe Types
    public static final RegistryObject<IRecipeSerializer<?>> CRAFTING_DAMAGE_TOOL = CUSTOM_CRAFTING_RECIPES.register("crafting_damage_tool", DamageToolRecipe.Serializer::new);

}
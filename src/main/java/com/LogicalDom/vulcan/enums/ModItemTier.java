package com.LogicalDom.vulcan.enums;

import com.LogicalDom.vulcan.util.Registries;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;

import java.util.function.Supplier;

public enum ModItemTier implements IItemTier {
    FORGE_HAMMER(2, 1000, 0F, 0F, 0, () -> {
        return Ingredient.fromItems((IItemProvider) Registries.BASALT_BRICKS.get());
    }),
    FORGED_STEEL(4, 2000, 11.0F, 4.0F, 16, () -> {
        return Ingredient.fromItems((IItemProvider) Registries.FORGED_STEEL_INGOT.get());
    });

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairMaterial;

    ModItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getMaxUses() { return this.maxUses; }

    @Override
    public float getEfficiency() { return this.efficiency; }

    @Override
    public float getAttackDamage() { return this.attackDamage; }

    @Override
    public int getHarvestLevel() { return this.harvestLevel; }

    @Override
    public int getEnchantability() { return this.enchantability; }

    @Override
    public Ingredient getRepairMaterial() { return (Ingredient)this.repairMaterial.get(); }

}

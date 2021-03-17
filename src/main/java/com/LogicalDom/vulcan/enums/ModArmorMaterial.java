package com.LogicalDom.vulcan.enums;

import com.LogicalDom.vulcan.util.Registries;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum ModArmorMaterial implements IArmorMaterial {
    FORGED_STEEL("vulcan:forged_steel", 44, new int[]{4, 7, 8, 4}, 16, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 3.0F, () -> {
        return Ingredient.fromItems((IItemProvider) Registries.FORGED_STEEL_INGOT.get());
    });

    private static final int[] MAX_DAMAGE_ARRAY = new int[]{11, 16, 15, 13};
    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final Supplier<Ingredient> repairMaterial;

    ModArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness, Supplier<Ingredient> repairMaterial) {
        this.name = name;
        this.maxDamageFactor = maxDamageFactor;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.repairMaterial = repairMaterial;
    }

    public int getDurability(EquipmentSlotType slotIn) { return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor; }
    public int getDamageReductionAmount(EquipmentSlotType slotIn) { return this.damageReductionAmountArray[slotIn.getIndex()]; }
    public int getEnchantability() { return this.enchantability; }
    public SoundEvent getSoundEvent() { return this.soundEvent; }
    public Ingredient getRepairMaterial() { return (Ingredient)this.repairMaterial.get(); }

    @OnlyIn(Dist.CLIENT)
    public String getName() { return this.name; }
    public float getToughness() { return this.toughness; }
    public float getKnockbackResistance() { return 0.0F; }
}

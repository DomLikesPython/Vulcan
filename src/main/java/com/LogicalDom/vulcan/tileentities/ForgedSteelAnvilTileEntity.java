package com.LogicalDom.vulcan.tileentities;

import com.LogicalDom.vulcan.Vulcan;
import com.LogicalDom.vulcan.containers.ForgedSteelAnvilContainer;
import com.LogicalDom.vulcan.util.CustomEnergyStorage;
import com.LogicalDom.vulcan.util.Registries;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.test.ITestCallback;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

//implements ITickableTileEntity
public class ForgedSteelAnvilTileEntity extends LockableLootTileEntity {

    public static final int INVENTORY_SIZE = 1;
    public static final int MAX_SLOT_SIZE = 64;
    public static final int ENERGY_CAPACITY = Integer.MAX_VALUE;

    // For Internal Usage
    private ItemStackHandler itemHandler = createHandler();
    private CustomEnergyStorage energyStorage = createEnergy();

    // For External Usage
    // Never create lazy optionals in getCapability. Always place them as fields in the tile entity:
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);
    private int counter;
    protected NonNullList<ItemStack> items = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);
    public Inventory inventory;

    protected ForgedSteelAnvilTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        this.inventory = new Inventory(INVENTORY_SIZE);

    }

    public ForgedSteelAnvilTileEntity() {
        this(Registries.FORGED_STEEL_ANVIL_TILE_ENTITY_TYPE.get());
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemsIn) {
        items = itemsIn;
    }

    @Override
    public void read(BlockState blockState, CompoundNBT tag) {
        itemHandler.deserializeNBT(tag.getCompound("inv"));
        energyStorage.deserializeNBT(tag.getCompound("energy"));

        counter = tag.getInt("counter");

        this.items = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);
        if (!this.checkLootAndRead(tag)) {
            ItemStackHelper.loadAllItems(tag, this.items);
        }

        super.read(blockState, tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", itemHandler.serializeNBT());
        tag.put("energy", energyStorage.serializeNBT());

        tag.putInt("counter", counter);

        if(!this.checkLootAndWrite(tag)) {
            ItemStackHelper.saveAllItems(tag, this.items);
        }

        return super.write(tag);
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("screen." + Vulcan.MOD_ID + ".forged_steel_anvil.name");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new ForgedSteelAnvilContainer(id, player, this);
    }

    @Override
    public void remove() {
        super.remove();
        handler.invalidate();
        energy.invalidate();
    }

    /*
    @Override
    public void tick() {
        if(world.isRemote) {
            return;
        }
        counter++;
        if (counter == 20) {
            energyStorage.addEnergy(100_000);
            counter = 0;
            markDirty();
        } else {
            energyStorage.addEnergy(1000);
            markDirty();
        }

    }
     */



    private ItemStackHandler createHandler() {
        return new ItemStackHandler(INVENTORY_SIZE) {

            @Override
            protected void onContentsChanged(int slot) {
                // To make sure the TE persists when the chunk is saved later we need to
                // mark it dirty every time the item handler changes
                markDirty();
            }
        };
    }

    private CustomEnergyStorage createEnergy() {
        return new CustomEnergyStorage(ENERGY_CAPACITY, 0) {
            @Override
            protected void onEnergyChanged() {
                markDirty();
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        if (cap == CapabilityEnergy.ENERGY) {
            return energy.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public int getSizeInventory() {
        return INVENTORY_SIZE;
    }
}

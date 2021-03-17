package com.LogicalDom.vulcan.containers;

import com.LogicalDom.vulcan.util.Registries;
import com.LogicalDom.vulcan.tileentities.ForgedSteelAnvilTileEntity;
import com.LogicalDom.vulcan.util.CustomEnergyStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.NonNullList;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;

import java.util.Objects;


public class ForgedSteelAnvilContainer extends Container {

    public final ForgedSteelAnvilTileEntity tileEntity;
    private final IWorldPosCallable canInteractWithCallable;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;

    public ForgedSteelAnvilContainer(final int windowId, final PlayerInventory playerInv, final ForgedSteelAnvilTileEntity tileEntity) {
        super(Registries.FORGED_STEEL_ANVIL_CONTAINER_TYPE.get(), windowId);
        this.tileEntity = tileEntity;
        this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

        // Tile Entity
        this.addSlot(new Slot((IInventory) tileEntity.inventory, 0, 80, 35));

        //Main Player Inventory:
        for(int row = 0; row < 3; row++) {
            for(int col = 0; col < 9; col++) {
                this.addSlot(new Slot((IInventory) playerInventory,  (9 * (row * col)) + 9, 8 + col * 18, 353 + (36 * row)));
            }
        }

        //Player Hotbar
        for(int col = 0; col < 9; col++) {
            this.addSlot(new Slot((IInventory) playerInventory, col, 8 + col * 18, 466));
        }
        trackPower();
    }

    public ForgedSteelAnvilContainer(final int windowId, final PlayerInventory playerInv, final PacketBuffer data) {
        this(windowId, playerInv, getTileEntity(playerInv, data));
    }

    private static ForgedSteelAnvilTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
        Objects.requireNonNull(playerInv, "Player Inventory cannot be null.");
        Objects.requireNonNull(data, "Packet Buffer cannot be null.");
        final TileEntity te = playerInv.player.world.getTileEntity(data.readBlockPos());
        if (te instanceof ForgedSteelAnvilTileEntity) {
            return (ForgedSteelAnvilTileEntity) te;
        }
        throw new IllegalStateException("Tile Entity Is Not Correct");
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, Registries.FORGED_STEEL_ANVIL.get());
    }

    /*
        //Main Player Inventory:
        for(int row = 0; row < 3; row++) {
            for(int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, row + col * 9 + 9, 8 + col * 18, 353 + (36 * row)));
            }
        }

        //Player Hotbar
        for(int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 466));
        }
     */

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if(slot != null && slot.getHasStack()) {
            ItemStack stack1 = slot.getStack();
            stack = stack1.copy();
            if(index < ForgedSteelAnvilTileEntity.INVENTORY_SIZE && !this.mergeItemStack(stack1, ForgedSteelAnvilTileEntity.INVENTORY_SIZE, this.inventorySlots.size(), false)) {
                return ItemStack.EMPTY;
            }
            if(!this.mergeItemStack(stack1, 0, ForgedSteelAnvilTileEntity.INVENTORY_SIZE, false)) {
                return ItemStack.EMPTY;
            }
            if(stack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return stack;
    }


    public int getEnergy() {
        return tileEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    private void trackPower() {
        // Unfortunately on a dedicated server ints are actually truncated to short so we need
        // to split our integer here (split our 32 bit integer into two 16 bit integers)
        trackInt(new IntReferenceHolder() {
            @Override
            public int get() {
                return getEnergy() & 0xffff;
            }

            @Override
            public void set(int value) {
                tileEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0xffff0000;
                    ((CustomEnergyStorage)h).setEnergy(energyStored + (value & 0xffff));
                });
            }
        });
        trackInt(new IntReferenceHolder() {
            @Override
            public int get() {
                return (getEnergy() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                tileEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0x0000ffff;
                    ((CustomEnergyStorage)h).setEnergy(energyStored | (value << 16));
                });
            }
        });
    }

}

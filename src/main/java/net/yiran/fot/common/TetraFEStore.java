package net.yiran.fot.common;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.IEnergyStorage;
import net.yiran.fot.Config;
import se.mickelus.tetra.items.modular.IModularItem;

public class TetraFEStore implements IEnergyStorage, INBTSerializable<CompoundTag> {
    public static final String KEY = "FOT$energy";
    protected final ItemStack stack;
    protected int energy;
    protected int capacity = 0;
    protected boolean capacityCached = false;

    public TetraFEStore(ItemStack stack) {
        this.stack = stack;
        if (!stack.isEmpty()) {
            CompoundTag tag = stack.getOrCreateTag();
            if (tag.contains(KEY)) {
                this.deserializeNBT(tag.getCompound(KEY));
            }
        }
    }

    @Override
    public boolean canReceive() {
        return true;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int energyReceived = Math.min(this.getMaxEnergyStored() - this.energy, Math.min(this.getMaxReceive(), maxReceive));
        if (!simulate) {
            this.setEnergyStored(this.energy + energyReceived);
        }
        return energyReceived;
    }

    @Override
    public int getEnergyStored() {
        return energy;
    }

    public void setEnergyStored(int energy) {
        this.energy = Math.max(0, Math.min(energy, this.getMaxEnergyStored()));
    }

    @Override
    public int getMaxEnergyStored() {
        if (!this.capacityCached) {
            if (stack.getItem() instanceof IModularItem iModularItem) {
                this.capacity = iModularItem.getEffectLevel(stack, FEItemEffects.FE_STORE);
            } else {
                this.capacity = 0;
            }
            this.capacityCached = true;
        }
        return this.capacity;
    }

    public int getMaxReceive() {
        return Config.maxReceive.get();
    }

    //允许放电(提取能量)
    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int energyExtracted = Math.min(this.getEnergyStored(), Math.min(this.getMaxReceive(), maxExtract));
        if (!simulate) {
            this.setEnergyStored(this.energy - energyExtracted);
        }
        return energyExtracted;
    }

    @Override
    public CompoundTag serializeNBT() {
        var tag = new CompoundTag();
        tag.putInt("energy", this.getEnergyStored());
        stack.getOrCreateTag().put(KEY, tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.setEnergyStored(tag.getInt("energy"));
    }
}

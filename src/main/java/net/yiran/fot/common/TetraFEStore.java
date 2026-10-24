package net.yiran.fot.common;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.IEnergyStorage;
import net.yiran.fot.Config;
import se.mickelus.tetra.items.modular.IModularItem;

public class TetraFEStore implements IEnergyStorage, INBTSerializable<CompoundTag> {
    public static final String KEY = "FOT$energy";
    private static final String TAG_ENERGY = "energy";
    private static final String TAG_CAPACITY = "cap";
    // 重入保护
    private static final ThreadLocal<Boolean> COMPUTING = ThreadLocal.withInitial(() -> false);

    protected final ItemStack stack;
    protected int energy;
    protected int capacity = 0;
    protected boolean capacityPersisted = false;

    public TetraFEStore(ItemStack stack) {
        this.stack = stack;
        if (!stack.isEmpty()) {
            CompoundTag tag = stack.getOrCreateTag();
            if (tag.contains(KEY, Tag.TAG_COMPOUND)) {
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
        int energyReceived = Math.max(0, Math.min(this.getMaxEnergyStored() - this.energy, Math.min(this.getMaxReceive(), maxReceive)));
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
        if (this.capacityPersisted) {
            this.energy = Math.max(0, Math.min(energy, this.capacity));
        } else {
            this.energy = Math.max(0, energy);
        }
    }

    @Override
    public int getMaxEnergyStored() {
        if (!this.capacityPersisted) {
            this.recomputeCapacity();
        }
        return this.capacity;
    }

    public boolean refreshCapacity() {
        return this.recomputeCapacity();
    }

    private boolean recomputeCapacity() {
        if (COMPUTING.get()) {
            return false;
        }
        if (!(this.stack.getItem() instanceof IModularItem modularItem)) {
            return false;
        }
        COMPUTING.set(true);
        try {
            this.capacity = modularItem.getEffectLevel(this.stack, FEItemEffects.FE_STORE);
            this.capacityPersisted = true;
            this.energy = Math.max(0, Math.min(this.energy, this.capacity));
            this.persist();
            return true;
        } finally {
            COMPUTING.set(false);
        }
    }

    private void persist() {
        if (this.stack.isEmpty()) {
            return;
        }
        CompoundTag root = this.stack.getOrCreateTag();
        CompoundTag tag = root.contains(KEY, Tag.TAG_COMPOUND) ? root.getCompound(KEY) : new CompoundTag();
        tag.putInt(TAG_ENERGY, this.energy);
        tag.putInt(TAG_CAPACITY, this.capacity);
        root.put(KEY, tag);
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
        tag.putInt(TAG_ENERGY, this.energy);
        if (this.capacityPersisted) {
            tag.putInt(TAG_CAPACITY, this.capacity);
        }
        stack.getOrCreateTag().put(KEY, tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.energy = Math.max(0, tag.getInt(TAG_ENERGY));
        if (tag.contains(TAG_CAPACITY)) {
            this.capacity = Math.max(0, tag.getInt(TAG_CAPACITY));
            this.capacityPersisted = true;
            this.energy = Math.min(this.energy, this.capacity);
        }
    }
}

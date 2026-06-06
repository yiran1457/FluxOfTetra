package net.yiran.fot.common;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.IEnergyStorage;
import se.mickelus.tetra.items.modular.IModularItem;

public class TetraFEStore implements IEnergyStorage, INBTSerializable<CompoundTag> {
    public static String KEY = "FOT$energy";
    protected ItemStack stack;
    protected int energy;
    protected int capacity = -999;
    protected int maxReceive = 20000;

    public TetraFEStore(ItemStack stack) {
        this.stack = stack;
        if (!stack.isEmpty() && stack.getOrCreateTag().contains(KEY)) {
            this.deserializeNBT(stack.getOrCreateTag().getCompound(KEY));
        }
    }

    @Override
    public boolean canReceive() {
        return true;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        {
            int energyReceived = Math.min(this.getMaxEnergyStored() - this.energy, Math.min(this.maxReceive, maxReceive));
            if (!simulate) {
                this.setEnergyStored(this.energy + energyReceived);
            }
            return energyReceived;
        }
    }

    @Override
    public int getEnergyStored() {
        return energy;
    }

    public void setEnergyStored(int energy) {
        this.energy = energy;
    }

    @Override
    public int getMaxEnergyStored() {
        if (this.capacity == -999) {
            this.capacity = ((IModularItem) stack.getItem()).getEffectLevel(stack, FEItemEffects.FE_STORE);
        }
        return this.capacity;
    }

    //禁止提取能量
    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int energyReceived = Math.min(this.getEnergyStored(), Math.min(this.maxReceive, maxExtract));
        if (!simulate) {
            this.setEnergyStored(this.energy - energyReceived);
        }
        return energyReceived;
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

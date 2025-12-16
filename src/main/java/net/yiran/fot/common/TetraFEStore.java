package net.yiran.fot.common;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.IEnergyStorage;

public class TetraFEStore implements IEnergyStorage {
    public static String KEY = "FOT$energy";
    protected ItemStack stack;
    protected int energy;
    protected int capacity;
    protected int maxReceive = 20000;

    public TetraFEStore(ItemStack stack, int capacity) {
        this.stack = stack;
        this.energy = stack.getTag().getInt(KEY);
        this.capacity = capacity;
    }

    @Override
    public boolean canReceive() {
        return true;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        {
            int energyReceived = Math.min(this.capacity - this.energy, Math.min(this.maxReceive, maxReceive));
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
        stack.getTag().putInt(KEY, energy);
    }

    @Override
    public int getMaxEnergyStored() {
        return this.capacity;
    }

    //禁止提取能量
    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }
}

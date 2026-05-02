package net.yiran.fot.common;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

public class TetraFEProvider implements ICapabilitySerializable<CompoundTag> {
    private final LazyOptional<IEnergyStorage> lazyOptional;
    private final TetraFEStore energyStorage;

    public TetraFEProvider(ItemStack stack) {
        this.energyStorage = new TetraFEStore(stack);
        this.lazyOptional = LazyOptional.of(() -> this.energyStorage);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction direction) {
        if (capability == ForgeCapabilities.ENERGY) {
            return this.lazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return energyStorage.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        energyStorage.deserializeNBT(tag);
    }
}

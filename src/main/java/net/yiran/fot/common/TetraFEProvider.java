package net.yiran.fot.common;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

public class TetraFEProvider implements ICapabilityProvider {
    private final LazyOptional<IEnergyStorage> lazyOptional;
    private final TetraFEStore energyStorage;

    public TetraFEProvider(ItemStack stack, int capacity) {
        this.energyStorage = new TetraFEStore(stack, capacity);
        this.lazyOptional = LazyOptional.of(() -> this.energyStorage);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction direction) {
        if (capability == ForgeCapabilities.ENERGY) {
            return this.lazyOptional.cast();
        }
        return LazyOptional.empty();
    }
}

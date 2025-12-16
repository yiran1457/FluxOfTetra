package net.yiran.fot.util;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.yiran.fot.common.TetraFEStore;

import java.util.Optional;

public class FEUtil {
    public static Optional<TetraFEStore>  getTetraFEStore(ItemStack stack) {
        if(stack.getCapability(ForgeCapabilities.ENERGY).orElse(null)instanceof TetraFEStore store){
            return Optional.of(store);
        }
        return Optional.empty();
    }
}

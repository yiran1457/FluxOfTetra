package net.yiran.fot.common;

import net.yiran.fot.Config;
import net.yiran.fot.util.FEUtil;
import se.mickelus.tetra.event.ModularItemDamageEvent;

public class DamageHook {
    public static void onModularDamage(ModularItemDamageEvent event) {
        FEUtil.getTetraFEStore(event.getItemStack()).ifPresent(store -> {
            int damage = Math.min(store.getEnergyStored() / Config.ForgeEnergy2Durability.get(), event.getAmount());
            store.setEnergyStored(store.getEnergyStored() - damage * Config.ForgeEnergy2Durability.get());
            event.setAmount(event.getAmount() - damage);
        });
    }
}

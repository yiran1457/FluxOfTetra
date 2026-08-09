package net.yiran.fot.common;

import net.yiran.fot.Config;
import net.yiran.fot.util.FEUtil;
import se.mickelus.tetra.event.ModularItemDamageEvent;

public class DamageHook {
    public static void onModularDamage(ModularItemDamageEvent event) {
        FEUtil.getTetraFEStore(event.getItemStack()).ifPresent(store -> {
            int ratio = Config.forgeEnergy2Durability.get();
            int damage = Math.min(store.getEnergyStored() / ratio, event.getAmount());
            store.setEnergyStored(store.getEnergyStored() - damage * ratio);
            event.setAmount(event.getAmount() - damage);
        });
    }
}

package net.yiran.fot.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.yiran.fot.FluxOfTetra;
import se.mickelus.tetra.items.modular.IModularItem;

public class AttachCapability {
    public static ResourceLocation KEY = new ResourceLocation(FluxOfTetra.MODID, "fe");

    public static void attach(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() instanceof IModularItem item) {
            int capacity = item.getEffectLevel(event.getObject(), FEItemEffects.FE_STORE);
            //int capacity = 20 * 1000 * 1000;
            if (capacity > 0) {
                event.addCapability(KEY, new TetraFEProvider(event.getObject(), capacity));
            }
        }
    }
}

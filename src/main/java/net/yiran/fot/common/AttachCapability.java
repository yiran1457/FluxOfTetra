package net.yiran.fot.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.yiran.fot.FluxOfTetra;
import se.mickelus.tetra.items.modular.IModularItem;

public class AttachCapability {
    public static ResourceLocation KEY = new ResourceLocation(FluxOfTetra.MODID, "fe");

    public static void attach(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() instanceof IModularItem) {
            if (event.getObject().is(ItemTags.create(new ResourceLocation(FluxOfTetra.MODID, "not_provider")))) {
                return;
            }
            event.addCapability(KEY, new TetraFEProvider(event.getObject()));
        }
    }
}

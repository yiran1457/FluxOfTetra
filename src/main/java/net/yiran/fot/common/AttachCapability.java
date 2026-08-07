package net.yiran.fot.common;

import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.yiran.fot.Config;
import net.yiran.fot.FluxOfTetra;
import se.mickelus.tetra.items.modular.IModularItem;

public class AttachCapability {
    public static ResourceLocation KEY = new ResourceLocation(FluxOfTetra.MODID, "fe");
    public static Object2BooleanOpenHashMap<Item> cache = new Object2BooleanOpenHashMap<>();

    public static void attach(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() instanceof IModularItem) {
            if (checkInCache(event.getObject().getItem()))
                event.addCapability(KEY, new TetraFEProvider(event.getObject()));
        }
    }

    public static boolean checkInCache(Item item) {
        if (!cache.containsKey(item)) {
            var key = ForgeRegistries.ITEMS.getKey(item).toString();
            cache.put(item, !Config.NotEnergyItems.get().contains(key));
        }
        return cache.getBoolean(item);
    }
}

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
    private static final ResourceLocation KEY = new ResourceLocation(FluxOfTetra.MODID, "fe");
    private static final Object2BooleanOpenHashMap<Item> CACHE = new Object2BooleanOpenHashMap<>();

    public static void attach(AttachCapabilitiesEvent<ItemStack> event) {
        Item item = event.getObject().getItem();
        if (item instanceof IModularItem && checkInCache(item)) {
            event.addCapability(KEY, new TetraFEProvider(event.getObject()));
        }
    }

    private static boolean checkInCache(Item item) {
        if (!CACHE.containsKey(item)) {
            String key = ForgeRegistries.ITEMS.getKey(item).toString();
            CACHE.put(item, !Config.notEnergyItems.get().contains(key));
        }
        return CACHE.getBoolean(item);
    }
}

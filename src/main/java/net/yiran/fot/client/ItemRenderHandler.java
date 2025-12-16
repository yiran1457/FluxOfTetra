package net.yiran.fot.client;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.yiran.fot.common.TetraFEStore;
import net.yiran.fot.util.FEUtil;
import se.mickelus.tetra.items.modular.IModularItem;

import java.util.Optional;

public class ItemRenderHandler {
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (event.getFlags().isAdvanced()) {
            FEUtil.getTetraFEStore(event.getItemStack()).ifPresent(store -> {
                event.getToolTip().add(1, Component.literal(I18n.get("tooltip.fot.fe.format", formatInt(store.getEnergyStored()), formatInt(store.getMaxEnergyStored()))));
            });
        }
    }

    public static void onRegisterItemDecorations(RegisterItemDecorationsEvent event) {
        for (Item item : ForgeRegistries.ITEMS) {
            if ((item instanceof IModularItem)) {
                event.register(item, ItemRenderHandler::drawBar);
            }
        }
    }

    public static boolean drawBar(GuiGraphics guiGraphics, Font font, ItemStack itemStack, int x, int y) {
        Optional<TetraFEStore> feStore = FEUtil.getTetraFEStore(itemStack);
        if (feStore.isEmpty()) return false;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(x + 2, y + 12, 200);
        if (itemStack.getDamageValue() == 0) {
            guiGraphics.fill(0, 0, 13, 2, 0, 0xff000000);
        } else {
            guiGraphics.fill(0, 0, 13, 1, 0, 0xff000000);
        }

        guiGraphics.fill(0, 0, (13 * feStore.get().getEnergyStored()) / feStore.get().getMaxEnergyStored(), 1, 0, 0xFFFF0000);
        guiGraphics.pose().popPose();
        return true;
    }


    public static String formatInt(int value) {
        if (value < 0) {
            return "-" + formatInt(-value);
        }
        if (value < 1_000) {
            return String.valueOf(value);
        }

        String suffix;
        int divisor;

        if (value >= 1_000_000_000) {
            suffix = "G";
            divisor = 100_000_000; // 1e8
        } else if (value >= 1_000_000) {
            suffix = "M";
            divisor = 100_000; // 1e5
        } else {
            suffix = "K";
            divisor = 100; // 1e2
        }

        int scaled = value / divisor;
        int whole = scaled / 10;
        int fraction = scaled % 10;

        if (fraction == 0) {
            return whole + suffix;
        } else {
            return whole + "." + fraction + suffix;
        }
    }
}

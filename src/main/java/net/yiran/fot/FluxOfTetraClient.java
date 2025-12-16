package net.yiran.fot;

import net.minecraftforge.eventbus.api.IEventBus;
import net.yiran.fot.client.FEItemEffectBars;
import net.yiran.fot.client.ItemRenderHandler;

public class FluxOfTetraClient {
    public static void init(IEventBus modBus,IEventBus forgeBus) {
        modBus.addListener(ItemRenderHandler::onRegisterItemDecorations);
        forgeBus.addListener(ItemRenderHandler::onItemTooltip);
        modBus.addListener(FEItemEffectBars::onClientSetup);
    }
}

package net.yiran.fot.client;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.yiran.fot.Config;
import net.yiran.fot.common.FEItemEffects;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.effect.ItemEffect;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.LabelGetterBasic;
import se.mickelus.tetra.gui.stats.getter.StatGetterEffectLevel;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

public class FEItemEffectBars {
    public static void onClientSetup(FMLClientSetupEvent event) {
        var levelStatGetter = new StatGetterEffectLevel(FEItemEffects.FE_STORE);
        addStatBar(new GuiStatBar(
                        0, 0, 59,
                        getEffectDesc(FEItemEffects.FE_STORE),
                        0, 1, false, false, false, levelStatGetter,
                        LabelGetterBasic.integerLabel, new TooltipGetterFormatInt(getEffectTooltip(FEItemEffects.FE_STORE), levelStatGetter, Config.ForgeEnergy2Durability.get())
                ));

    }

    public static String getEffectDesc(ItemEffect effect) {
        return "tetra.stats." + effect.getKey();
    }

    public static String getEffectTooltip(ItemEffect effect) {
        return getEffectDesc(effect) + ".tooltip";
    }

    public static void addStatBar(GuiStatBar bar) {
        WorkbenchStatsGui.addBar(bar);
        HoloStatsGui.addBar(bar);
    }
}

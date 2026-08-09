package net.yiran.fot.client;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import se.mickelus.tetra.gui.stats.getter.IStatGetter;
import se.mickelus.tetra.gui.stats.getter.ITooltipGetter;

public class TooltipGetterFormatInt implements ITooltipGetter {
    protected final IStatGetter statGetter;
    protected final String localizationKey;
    protected final boolean absolute;
    protected final Object[] extraArgs;

    public TooltipGetterFormatInt(String localizationKey, IStatGetter statGetter, boolean absolute, Object... extraArgs) {
        this.absolute = absolute;
        this.localizationKey = localizationKey;
        this.statGetter = statGetter;
        this.extraArgs = extraArgs;
    }

    public TooltipGetterFormatInt(String localizationKey, IStatGetter statGetter, Object... extraArgs) {
        this(localizationKey, statGetter, false, extraArgs);
    }

    public String getTooltipBase(Player player, ItemStack itemStack) {
        return this.absolute
                ? I18n.get(this.localizationKey, ItemRenderHandler.formatInt((int) Math.abs(this.statGetter.getValue(player, itemStack))), extraArgs)
                : I18n.get(this.localizationKey, ItemRenderHandler.formatInt((int) this.statGetter.getValue(player, itemStack)), extraArgs);
    }

    public boolean hasExtendedTooltip(Player player, ItemStack itemStack) {
        return I18n.exists(this.localizationKey + "_extended");
    }

    public String getTooltipExtension(Player player, ItemStack itemStack) {
        return I18n.get(this.localizationKey + "_extended");
    }
}

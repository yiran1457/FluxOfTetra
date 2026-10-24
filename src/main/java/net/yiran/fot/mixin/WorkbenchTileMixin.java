package net.yiran.fot.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.yiran.fot.util.FEUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import se.mickelus.tetra.blocks.workbench.WorkbenchTile;

@Mixin(WorkbenchTile.class)
public abstract class WorkbenchTileMixin {
    @Shadow(remap = false)
    public abstract ItemStack getTargetItemStack();

    @Inject(method = "craft", at = @At("TAIL"), remap = false)
    private void fot$refreshFECapacity(Player player, CallbackInfo ci) {
        FEUtil.getTetraFEStore(this.getTargetItemStack()).ifPresent(store -> store.refreshCapacity());
    }
}

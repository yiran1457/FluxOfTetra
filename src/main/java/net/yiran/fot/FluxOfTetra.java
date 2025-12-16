package net.yiran.fot;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.yiran.fot.common.AttachCapability;
import net.yiran.fot.common.DamageHook;
import org.slf4j.Logger;

@SuppressWarnings("removal")
@Mod(FluxOfTetra.MODID)
public class FluxOfTetra {
    public static final String MODID = "fluxoftetra";
    private static final Logger LOGGER = LogUtils.getLogger();

    public FluxOfTetra() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        if(FMLEnvironment.dist == Dist.CLIENT) {
            FluxOfTetraClient.init(modBus, forgeBus);
        }

        FMLJavaModLoadingContext.get().registerConfig(ModConfig.Type.COMMON,Config.SPEC);

        forgeBus.addGenericListener(ItemStack.class,AttachCapability::attach);
        forgeBus.addListener(DamageHook::onModularDamage);
    }
}

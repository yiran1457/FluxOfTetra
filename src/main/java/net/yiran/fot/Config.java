package net.yiran.fot;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class Config {
    public static final ForgeConfigSpec SPEC;
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.ConfigValue<Integer> forgeEnergy2Durability;
    public static final ForgeConfigSpec.ConfigValue<Integer> maxReceive;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> notEnergyItems;

    static {
        forgeEnergy2Durability = BUILDER
                .comment("电量抵扣耐久的比例")
                .comment("default : 320")
                .define("ForgeEnergy2Durability", 320);
        maxReceive = BUILDER
                .comment("每次充放电的速率上限")
                .comment("default : 20000")
                .define("MaxReceive", 20000);
        notEnergyItems = BUILDER
                .comment("不附加电力的物品id")
                .defineList("NotEnergyItems", ObjectArrayList::new, o -> true);
        SPEC = BUILDER.build();
    }
}

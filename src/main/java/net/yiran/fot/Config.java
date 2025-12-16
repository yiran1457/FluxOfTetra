package net.yiran.fot;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static final ForgeConfigSpec SPEC;
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.ConfigValue<Integer> ForgeEnergy2Durability;

    static {
        ForgeEnergy2Durability = BUILDER
                .comment("电量抵扣耐久的比例")
                .comment("default : 320")
                .define("ForgeEnergy2Durability", 320);
        SPEC = BUILDER.build();
    }
}

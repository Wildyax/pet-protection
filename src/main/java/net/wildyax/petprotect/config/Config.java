package net.wildyax.petprotect.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {

    public static class Common {

        public final ModConfigSpec.BooleanValue ironMedalProtectFromOwner;
        public final ModConfigSpec.BooleanValue ironMedalProtectFromTeam;
        public final ModConfigSpec.BooleanValue ironMedalRenderOnPet;

        public final ModConfigSpec.BooleanValue goldMedalProtectFromOwner;
        public final ModConfigSpec.BooleanValue goldMedalProtectFromTeam;
        public final ModConfigSpec.BooleanValue goldMedalRenderOnPet;

        public final ModConfigSpec.BooleanValue diamondMedalProtectFromOwner;
        public final ModConfigSpec.BooleanValue diamondMedalProtectFromTeam;
        public final ModConfigSpec.BooleanValue diamondMedalRenderOnPet;

        Common(ModConfigSpec.Builder builder) {

            builder.push("medals");

            builder.push("iron_medal");
            ironMedalProtectFromOwner = builder
                    .comment("The medal protect pet from his owner's damages")
                    .define("protect_from_owner", true);
            ironMedalProtectFromTeam = builder
                    .comment("The medal protect pet from players of his owner's team if PVP is turned off")
                    .define("protect_from_team", false);
            ironMedalRenderOnPet = builder
                    .comment("Display the medal on the pet")
                    .define("render_on_pet", true);
            builder.pop();

            builder.push("gold_medal");
            goldMedalProtectFromOwner = builder
                    .comment("The medal protect pet from his owner's damages")
                    .define("protect_from_owner", true);
            goldMedalProtectFromTeam = builder
                    .comment("The medal protect pet from players of his owner's team if PVP is turned off")
                    .define("protect_from_team", true);
            goldMedalRenderOnPet = builder
                    .comment("Display the medal on the pet")
                    .define("render_on_pet", true);
            builder.pop();

            builder.push("diamond_medal");
            diamondMedalProtectFromOwner = builder
                    .comment("The medal protect pet from his owner's damages")
                    .define("protect_from_owner", true);
            diamondMedalProtectFromTeam = builder
                    .comment("The medal protect pet from players of his owner's team if PVP is turned off")
                    .define("protect_from_team", true);
            diamondMedalRenderOnPet = builder
                    .comment("Display the medal on the pet")
                    .define("render_on_pet", true);
            builder.pop();

            builder.pop();
        }
    }

    public static final ModConfigSpec COMMON_SPEC;
    public static final Common COMMON;
    static {
        final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
        COMMON = specPair.getLeft();
        COMMON_SPEC = specPair.getRight();
    }
}

package simplesweep;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

public class Config {

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> whitelist;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> blacklist;

    static {
        whitelist = COMMON_BUILDER
            .comment(" List of items to block the sweep attack for, if no Sweeping Edge.",
                " Items should be in the form of registry name, i.e. 'minecraft:diamond_sword'")
            .defineList("whitelist", Collections.emptyList(),
                it -> it instanceof String && ResourceLocation.isValidResourceLocation((String) it)
            );

        blacklist = COMMON_BUILDER
            .comment(" List of items to not block the sweep attack for, if no Sweeping Edge.",
                " If whitelist is not empty, the blacklist will be ignored.",
                " Items should be in the form of registry name, i.e. 'minecraft:diamond_sword'")
            .defineList("blacklist", Collections.emptyList(),
                it -> it instanceof String && ResourceLocation.isValidResourceLocation((String) it)
            );

        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }

}

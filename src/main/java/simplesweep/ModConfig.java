package simplesweep;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = SimpleSweep.MOD_ID, type = Type.INSTANCE)
public class ModConfig {

    @Comment({
        "List of items to block the sweep attack for, if no Sweeping Edge.",
        "Items should be in the form of registry name, i.e. 'minecraft:diamond_sword'"
    })
    public static String[] whitelist = {};

    @Comment({
        "List of items to not block the sweep attack for, if no Sweeping Edge.",
        "If whitelist is not empty, the blacklist will be ignored.",
        "Items should be in the form of registry name, i.e. 'minecraft:diamond_sword'"
    })
    public static String[] blacklist = {};

}

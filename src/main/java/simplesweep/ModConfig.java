package simplesweep;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = SimpleSweep.MOD_ID, type = Type.INSTANCE)
public class ModConfig {

    @Comment("Whitelist, will be read first")
    public static String[] whitelist = {"minecraft:diamond_sword", "minecraft:iron_sword"};

    @Comment("Blacklist")
    public static String[] blacklist = {};

}

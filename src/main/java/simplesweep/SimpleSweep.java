package simplesweep;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(
        modid = SimpleSweep.MOD_ID,
        name = SimpleSweep.MOD_NAME,
        version = SimpleSweep.VERSION
)
public class SimpleSweep {

    static final String MOD_ID = "simplesweep";
    static final String MOD_NAME = "SimpleSweep";
    static final String VERSION = "1.2";

    public SimpleSweep() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void interceptAttack(AttackEntityEvent event) {
        EntityPlayer entityPlayer = event.getEntityPlayer();
        // If the player cannot do any sweeping damage, they do not have the enchantment.
        if (EnchantmentHelper.getSweepingDamageRatio(entityPlayer) == 0.0F) {
            ItemStack heldItemMainhand = entityPlayer.getHeldItemMainhand();
            ResourceLocation weaponLocation = heldItemMainhand.getItem().getRegistryName();
            String[] whitelist = ModConfig.whitelist;
            boolean isInWhitelist = false;
            for (String item : whitelist) {
                if (weaponLocation.equals(new ResourceLocation(item))) {
                    isInWhitelist = true;
                    break;
                }
            }

            String[] blacklist = ModConfig.blacklist;
            boolean isInBlacklist = false;
            for (String item : blacklist) {
                if (weaponLocation.equals(new ResourceLocation(item))) {
                    isInBlacklist = true;
                    break;
                }
            }

            if ((isInWhitelist || (whitelist.length == 0 && !isInBlacklist)) && entityPlayer.onGround) {
                // Set player (for this tick) to onGround = false
                entityPlayer.onGround = false;
            }
        }
    }

    @SubscribeEvent
    public void onConfigChangedEvent(OnConfigChangedEvent event) {
        if (event.getModID().equals(MOD_ID)) {
            ConfigManager.sync(MOD_ID, Type.INSTANCE);
        }
    }
}

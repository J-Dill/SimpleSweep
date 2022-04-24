package simplesweep;

import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(SimpleSweep.MOD_ID)
public class SimpleSweep {

    static final String MOD_ID = "simplesweep";

    public SimpleSweep() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("simplesweep-common.toml"));
    }

    @SubscribeEvent
    public void interceptAttack(AttackEntityEvent event) {
        Player entityPlayer = event.getPlayer();
        // If the player cannot do any sweeping damage, they do not have the enchantment.
        if ((Config.onlyCrouch.get() && !entityPlayer.isCrouching()) ||
                (!Config.onlyCrouch.get() && EnchantmentHelper.getSweepingDamageRatio(entityPlayer) == 0.0F)) {
            ItemStack heldItemMainHand = entityPlayer.getMainHandItem();
            ResourceLocation weaponLocation = heldItemMainHand.getItem().getRegistryName();
            assert weaponLocation != null;
            List<? extends String> whitelist = Config.whitelist.get();
            boolean isInWhitelist = false;
            for (String item : whitelist) {
                if (weaponLocation.equals(new ResourceLocation(item))) {
                    isInWhitelist = true;
                    break;
                }
            }

            List<? extends String> blacklist = Config.blacklist.get();
            boolean isInBlacklist = false;
            for (String item : blacklist) {
                if (weaponLocation.equals(new ResourceLocation(item))) {
                    isInBlacklist = true;
                    break;
                }
            }

            if ((isInWhitelist || (whitelist.size() == 0 && !isInBlacklist)) && entityPlayer.isOnGround()) {
                // Set player (for this tick) to onGround = false
                entityPlayer.setOnGround(false);
            }
        }
    }

}

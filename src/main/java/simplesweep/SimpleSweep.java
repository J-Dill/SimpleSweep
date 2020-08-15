package simplesweep;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("simplesweep")
public class SimpleSweep {

    public SimpleSweep() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onAttackEntity(final AttackEntityEvent evt) {
        // If the player cannot do any sweeping damage, they do not have the enchantment.
        if (EnchantmentHelper.getSweepingDamageRatio(evt.getPlayer()) == 0.0F) {
            PlayerEntity player = evt.getPlayer();
            // If player is on ground, set them (for this tick) to onGround = false;
            if (player.func_233570_aj_()) {
                player.func_230245_c_(false);
            }
        }
    }

}

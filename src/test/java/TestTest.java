import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import simplesweep.ModConfig;
import simplesweep.SimpleSweep;

public class TestTest {

    private final SimpleSweep simpleSweep = mock(SimpleSweep.class);
    private MockedStatic<EnchantmentHelper> utilities;

    @Before
    public void before() {
        utilities = Mockito.mockStatic(EnchantmentHelper.class);
        utilities.when(() -> EnchantmentHelper.getSweepingDamageRatio(any()))
            .thenReturn(0.0F);
    }

    @After
    public void after() {
        utilities.close();
    }

    @Test
    public void testWhitelist() {
        ModConfig.whitelist = new String[]{"minecraft:diamond_sword"};

        EntityPlayer player = mock(EntityPlayer.class);
        AttackEntityEvent event = mock(AttackEntityEvent.class);
        doMocks(player, event, "minecraft:diamond_sword");

        player.onGround = true;
        simpleSweep.interceptAttack(event);

        assertFalse(player.onGround);
    }

    @Test
    public void testBlacklist() {
        ModConfig.blacklist = new String[]{"minecraft:diamond_sword"};
        ModConfig.whitelist = new String[]{};

        EntityPlayer player = mock(EntityPlayer.class);
        AttackEntityEvent event = mock(AttackEntityEvent.class);
        doMocks(player, event, "minecraft:wooden_sword");

        player.onGround = true;
        simpleSweep.interceptAttack(event);

        assertFalse(player.onGround);
    }

    @Test
    public void testNeither() {
        ModConfig.blacklist = new String[]{};
        ModConfig.whitelist = new String[]{};

        EntityPlayer player = mock(EntityPlayer.class);
        AttackEntityEvent event = mock(AttackEntityEvent.class);
        doMocks(player, event, "minecraft:wooden_sword");

        player.onGround = true;
        simpleSweep.interceptAttack(event);

        assertFalse(player.onGround);
    }

    private void doMocks(EntityPlayer player, AttackEntityEvent event, String name) {
        ItemStack itemStack = mock(ItemStack.class);
        Item item = mock(Item.class);
        when(itemStack.getItem()).thenReturn(item);
        when(item.getRegistryName()).thenReturn(new ResourceLocation(name));
        when(player.getHeldItemMainhand()).thenReturn(itemStack);
        when(event.getEntityPlayer()).thenReturn(player);
        doCallRealMethod().when(simpleSweep).interceptAttack(event);
    }

}

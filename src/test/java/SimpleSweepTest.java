import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import simplesweep.Config;
import simplesweep.SimpleSweep;

@SuppressWarnings("unchecked")
public class SimpleSweepTest {

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
        Config.whitelist = (ConfigValue<List<? extends String>>) mock(ConfigValue.class);
        List<? extends String> mockWhitelist = createList("minecraft:diamond_sword");
        doReturn(mockWhitelist).when(Config.whitelist).get();

        PlayerEntity player = mock(PlayerEntity.class);
        when(player.isOnGround()).thenReturn(true);
        AttackEntityEvent event = mock(AttackEntityEvent.class);
        doMocks(player, event, "minecraft:diamond_sword");

        simpleSweep.interceptAttack(event);

        verify(player, times(1)).setOnGround(false);
    }

    @Test
    public void testBlacklist() {
        Config.whitelist = (ConfigValue<List<? extends String>>) mock(ConfigValue.class);
        List<? extends String> mockWhitelist = createList();
        doReturn(mockWhitelist).when(Config.whitelist).get();
        Config.blacklist = (ConfigValue<List<? extends String>>) mock(ConfigValue.class);
        List<? extends String> mockBlacklist = createList("minecraft:diamond_sword");
        doReturn(mockBlacklist).when(Config.blacklist).get();

        PlayerEntity player = mock(PlayerEntity.class);
        when(player.isOnGround()).thenReturn(true);
        AttackEntityEvent event = mock(AttackEntityEvent.class);
        doMocks(player, event, "minecraft:wooden_sword");

        simpleSweep.interceptAttack(event);

        verify(player, times(1)).setOnGround(false);
    }

    @Test
    public void testNeither() {
        Config.whitelist = (ConfigValue<List<? extends String>>) mock(ConfigValue.class);
        List<? extends String> mockWhitelist = createList();
        doReturn(mockWhitelist).when(Config.whitelist).get();
        Config.blacklist = (ConfigValue<List<? extends String>>) mock(ConfigValue.class);
        List<? extends String> mockBlacklist = createList();
        doReturn(mockBlacklist).when(Config.blacklist).get();

        PlayerEntity player = mock(PlayerEntity.class);
        when(player.isOnGround()).thenReturn(true);
        AttackEntityEvent event = mock(AttackEntityEvent.class);
        doMocks(player, event, "minecraft:wooden_sword");

        simpleSweep.interceptAttack(event);

        verify(player, times(1)).setOnGround(false);
    }

    @Test
    public void testBoth() {
        Config.whitelist = (ConfigValue<List<? extends String>>) mock(ConfigValue.class);
        List<? extends String> mockWhitelist = createList("minecraft:diamond_sword");
        doReturn(mockWhitelist).when(Config.whitelist).get();
        Config.blacklist = (ConfigValue<List<? extends String>>) mock(ConfigValue.class);
        List<? extends String> mockBlacklist = createList("minecraft:stone_sword");
        doReturn(mockBlacklist).when(Config.blacklist).get();

        PlayerEntity player = mock(PlayerEntity.class);
        when(player.isOnGround()).thenReturn(true);
        AttackEntityEvent event = mock(AttackEntityEvent.class);
        doMocks(player, event, "minecraft:diamond_sword");

        simpleSweep.interceptAttack(event);

        verify(player, times(1)).setOnGround(false);
    }

    @Test
    public void testBothNotInBoth() {
        Config.whitelist = (ConfigValue<List<? extends String>>) mock(ConfigValue.class);
        List<? extends String> mockWhitelist = createList("minecraft:diamond_sword");
        doReturn(mockWhitelist).when(Config.whitelist).get();
        Config.blacklist = (ConfigValue<List<? extends String>>) mock(ConfigValue.class);
        List<? extends String> mockBlacklist = createList("minecraft:stone_sword");
        doReturn(mockBlacklist).when(Config.blacklist).get();

        PlayerEntity player = mock(PlayerEntity.class);
        when(player.isOnGround()).thenReturn(true);
        AttackEntityEvent event = mock(AttackEntityEvent.class);
        doMocks(player, event, "minecraft:wooden_sword");

        simpleSweep.interceptAttack(event);

        verify(player, never()).setOnGround(false);
    }

    private static ArrayList<String> createList(String... names) {
        return new ArrayList<>(Arrays.asList(names));
    }

    private void doMocks(PlayerEntity player, AttackEntityEvent event, String itemName) {
        ItemStack itemStack = mock(ItemStack.class);
        Item item = mock(Item.class);
        when(itemStack.getItem()).thenReturn(item);
        when(item.getRegistryName()).thenReturn(new ResourceLocation(itemName));
        when(player.getHeldItemMainhand()).thenReturn(itemStack);
        when(event.getPlayer()).thenReturn(player);
        doCallRealMethod().when(simpleSweep).interceptAttack(event);
    }

}

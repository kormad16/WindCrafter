package at.kaindorf.windcrafter.event;

import at.kaindorf.windcrafter.WindcrafterMod;
import at.kaindorf.windcrafter.gui.GuiZeldaHealth;
import at.kaindorf.windcrafter.gui.GuiZeldaMagic;
import at.kaindorf.windcrafter.init.ItemManager;
import at.kaindorf.windcrafter.init.SoundManager;
import at.kaindorf.windcrafter.items.ItemHerosSword;
import at.kaindorf.windcrafter.items.ItemMagicJar;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(modid = WindcrafterMod.MODID)
public class EventHandler {

    // Zelda Health System: Keep Hearts on Respawn
    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone e) {
        double health = e.getOriginal().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue();
        e.getEntityPlayer().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(health);
        e.getEntityPlayer().setHealth((float)health);
        e.getEntityPlayer().getEntityData().setInteger("ZeldaMagic", e.getOriginal().getEntityData().getInteger("ZeldaMagicMax"));
        e.getEntityPlayer().getEntityData().setInteger("ZeldaMagicMax", e.getOriginal().getEntityData().getInteger("ZeldaMagicMax"));
    }

    public static final GuiZeldaHealth HEALTH_GUI = new GuiZeldaHealth(Minecraft.getMinecraft());
    public static final GuiZeldaMagic MAGIC_GUI = new GuiZeldaMagic(Minecraft.getMinecraft());

    // Zelda Health System: Modified Health Display
    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Pre e) {
        if(e.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
            e.setCanceled(true);
            HEALTH_GUI.drawHealth(e.getResolution().getScaledWidth(), e.getResolution().getScaledHeight());
        } else if(e.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            e.setCanceled(true);
            MAGIC_GUI.drawMagic(e.getResolution().getScaledWidth(), e.getResolution().getScaledHeight());
        }
    }

    // Player Tick Event
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent e) {
        // Heart
        while (e.player.inventory.hasItemStack(new ItemStack(ItemManager.HEART))) {
            e.player.playSound(SoundManager.heartSoundEvent, 1.0f, 1.0f);
            e.player.heal(2.0f);
            e.player.inventory.clearMatchingItems(ItemManager.HEART, 0, 1, null);
        }
        // Heart Piece
        while (e.player.inventory.hasItemStack(new ItemStack(ItemManager.HEARTPIECE))) {
            IAttributeInstance health = e.player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
            if(health.getBaseValue() < 40) {
                health.setBaseValue(health.getBaseValue() + 0.5f);
                if(health.getBaseValue() % 2 == 0)
                    e.player.playSound(SoundManager.heartContainerSoundEvent, 1.0f, 1.0f);
                else
                    e.player.playSound(SoundManager.itemFanfareSoundEvent, 1.0f, 1.0f);
            }
            e.player.heal((float)health.getBaseValue());
            e.player.inventory.clearMatchingItems(ItemManager.HEARTPIECE, 0, 1, null);
        }
        // Heart Container
        while (e.player.inventory.hasItemStack(new ItemStack(ItemManager.HEARTCONTAINER))) {
            e.player.playSound(SoundManager.heartContainerSoundEvent, 1.0f, 1.0f);
            IAttributeInstance health = e.player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
            if(health.getBaseValue() < 40)
                health.setBaseValue(health.getBaseValue() + 2);
            e.player.heal((float)health.getBaseValue());
            e.player.inventory.clearMatchingItems(ItemManager.HEARTCONTAINER, 0, 1, null);
        }
        // Magic Init
        if(!e.player.getEntityData().hasKey("ZeldaMagic") || e.player.getEntityData().getInteger("ZeldaMagicMax") == 0) {
            e.player.getEntityData().setInteger("ZeldaMagic", 100);
            e.player.getEntityData().setInteger("ZeldaMagicMax", 100);
        }
        // Small Magic Jar
        while(e.player.inventory.hasItemStack(new ItemStack(ItemManager.SMALLMAGIC))) {
            e.player.playSound(SoundManager.smallPickupSoundEvent, 1.0f, 1.0f);
            if(e.player.getEntityData().getInteger("ZeldaMagic") < e.player.getEntityData().getInteger("ZeldaMagicMax")) {
                e.player.getEntityData().setInteger("ZeldaMagic", e.player.getEntityData().getInteger("ZeldaMagic") + 20);
                if (e.player.getEntityData().getInteger("ZeldaMagic") > e.player.getEntityData().getInteger("ZeldaMagicMax"))
                    e.player.getEntityData().setInteger("ZeldaMagic", e.player.getEntityData().getInteger("ZeldaMagicMax"));
            }
            e.player.inventory.clearMatchingItems(ItemManager.SMALLMAGIC, 0, 1, null);
        }
        // Large Magic Jar
        while(e.player.inventory.hasItemStack(new ItemStack(ItemManager.LARGEMAGIC))) {
            e.player.playSound(SoundManager.smallPickupSoundEvent, 1.0f, 1.0f);
            if(e.player.getEntityData().getInteger("ZeldaMagic") < e.player.getEntityData().getInteger("ZeldaMagicMax")) {
                e.player.getEntityData().setInteger("ZeldaMagic", e.player.getEntityData().getInteger("ZeldaMagic") + 50);
                if (e.player.getEntityData().getInteger("ZeldaMagic") > e.player.getEntityData().getInteger("ZeldaMagicMax"))
                    e.player.getEntityData().setInteger("ZeldaMagic", e.player.getEntityData().getInteger("ZeldaMagicMax"));
            }
            e.player.inventory.clearMatchingItems(ItemManager.LARGEMAGIC, 0, 1, null);
        }

        // Low Health Sound
        if(!e.player.getEntityData().hasKey("LowHealthTimer"))
            e.player.getEntityData().setByte("LowHealthTimer", (byte)0);
        if(e.player.getHealth() < 4.0f && e.player.getHealth() > 0.0f) {
            if(e.player.getEntityData().getByte("LowHealthTimer") == 0) {
                e.player.playSound(SoundManager.lowHealthSoundEvent, 1.0f, 1.0f);
                e.player.getEntityData().setByte("LowHealthTimer", (byte)100);
            } else
                e.player.getEntityData().setByte("LowHealthTimer", (byte)(e.player.getEntityData().getByte("LowHealthTimer")-1));
        }
    }

}

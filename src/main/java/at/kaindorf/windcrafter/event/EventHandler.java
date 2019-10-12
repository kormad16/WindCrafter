package at.kaindorf.windcrafter.event;

import at.kaindorf.windcrafter.WindcrafterMod;
import at.kaindorf.windcrafter.gui.GuiZeldaHealth;
import at.kaindorf.windcrafter.items.ItemHeart;
import at.kaindorf.windcrafter.items.ItemHeartContainer;
import at.kaindorf.windcrafter.items.ItemHeartPiece;
import at.kaindorf.windcrafter.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static at.kaindorf.windcrafter.items.ItemHeartPiece.itemFanfareSoundEvent;

@Mod.EventBusSubscriber(modid = WindcrafterMod.MODID)
public class EventHandler {

    // Zelda Health System: Keep Hearts on Respawn
    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone e) {
        double health = e.getOriginal().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue();
        e.getEntityPlayer().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(health);
        e.getEntityPlayer().setHealth((float)health);
    }

    private static final GuiZeldaHealth HEALTH_GUI = new GuiZeldaHealth(Minecraft.getMinecraft());

    // Zelda Health System: Modified Health Display
    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Pre e) {
        if(e.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
            e.setCanceled(true);
            HEALTH_GUI.drawHealth(e.getResolution().getScaledWidth(), e.getResolution().getScaledHeight());
        }
    }

    // Zelda Health System: Pickup Event
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent e) {
        // Heart
        while (e.player.inventory.hasItemStack(new ItemStack(CommonProxy.HEART))) {
            e.player.playSound(ItemHeart.heartSoundEvent, 1.0f, 1.0f);
            e.player.heal(2.0f);
            e.player.inventory.clearMatchingItems(CommonProxy.HEART, 0, 1, null);
        }
        // Heart Piece
        while (e.player.inventory.hasItemStack(new ItemStack(CommonProxy.HEARTPIECE))) {
            IAttributeInstance health = e.player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
            if(health.getBaseValue() < 40) {
                health.setBaseValue(health.getBaseValue() + 0.5f);
                if(health.getBaseValue() % 2 == 0)
                    e.player.playSound(ItemHeartPiece.heartContainerSoundEvent, 1.0f, 1.0f);
                else
                    e.player.playSound(ItemHeartPiece.itemFanfareSoundEvent, 1.0f, 1.0f);
            }
            e.player.heal((float)health.getBaseValue());
            e.player.inventory.clearMatchingItems(CommonProxy.HEARTPIECE, 0, 1, null);
        }
        // Heart Container
        while (e.player.inventory.hasItemStack(new ItemStack(CommonProxy.HEARTCONTAINER))) {
            e.player.playSound(ItemHeartContainer.heartContainerSoundEvent, 1.0f, 1.0f);
            IAttributeInstance health = e.player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
            if(health.getBaseValue() < 40)
                health.setBaseValue(health.getBaseValue() + 2);
            e.player.heal((float)health.getBaseValue());
            e.player.inventory.clearMatchingItems(CommonProxy.HEARTCONTAINER, 0, 1, null);
        }
    }

}

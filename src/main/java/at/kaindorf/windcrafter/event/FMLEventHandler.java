package at.kaindorf.windcrafter.event;

import at.kaindorf.windcrafter.WindcrafterMod;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber(modid = WindcrafterMod.MODID)
public class FMLEventHandler {

    // Zelda Health System: Set hearts on init
    @SubscribeEvent
    public static void onPlayerJoinServer(PlayerEvent.PlayerLoggedInEvent e) {
        if(!e.player.getEntityData().hasKey("ZeldaHealthInit")) {
            e.player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0f);
            e.player.setHealth(12.0f);
            e.player.getEntityData().setBoolean("ZeldaHealthInit", true);
        }
    }

}

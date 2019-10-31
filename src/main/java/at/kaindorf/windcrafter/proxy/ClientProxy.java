package at.kaindorf.windcrafter.proxy;

import at.kaindorf.windcrafter.init.EntityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preinit(FMLPreInitializationEvent e) {
        super.preinit(e);
        EntityManager.initRenderers();
    }

}

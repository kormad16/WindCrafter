package at.kaindorf.windcrafter;

import at.kaindorf.windcrafter.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = WindcrafterMod.MODID, name = WindcrafterMod.NAME, version = WindcrafterMod.VERSION)
public class WindcrafterMod
{
    public static final String MODID = "windcrafter";
    public static final String NAME = "The Windcrafter Mod";
    public static final String VERSION = "1.0";

    @Mod.Instance
    public static WindcrafterMod instance;

    @SidedProxy(clientSide = "at.kaindorf.windcrafter.proxy.ClientProxy",
                serverSide = "at.kaindorf.windcrafter.proxy.ServerProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preinit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postinit(event);
    }
}

package at.kaindorf.windcrafter.proxy;

import at.kaindorf.windcrafter.WindcrafterMod;
import at.kaindorf.windcrafter.init.EntityManager;
import at.kaindorf.windcrafter.init.ItemManager;
import at.kaindorf.windcrafter.items.*;
import at.kaindorf.windcrafter.util.BiomeHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

import java.util.Arrays;
import java.util.logging.LogManager;

@Mod.EventBusSubscriber(modid = WindcrafterMod.MODID)
public class CommonProxy {

    public void preinit(FMLPreInitializationEvent e) {

    }

    public void init(FMLInitializationEvent e) {
    }

    public void postinit(FMLPostInitializationEvent e) {

    }

    @SubscribeEvent
    public static void entityRegistration(RegistryEvent.Register<EntityEntry> e) {
        EntityManager.initEntities(e);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> e) {

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> e) {
        ItemManager.registerItems(e);
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent e) {
        ItemManager.registerRenders(e);
    }

}

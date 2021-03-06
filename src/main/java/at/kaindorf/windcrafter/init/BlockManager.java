package at.kaindorf.windcrafter.init;

import at.kaindorf.windcrafter.blocks.BlockBrokenBarricade;
import at.kaindorf.windcrafter.blocks.BlockWoodenBarricade;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;

public class BlockManager {

    public static Block BROKEN_BARRICADE = new BlockBrokenBarricade();
    public static Block WOODEN_BARRICADE = new BlockWoodenBarricade();

    public static void registerBlocks(RegistryEvent.Register<Block> e) {
        e.getRegistry().register(BROKEN_BARRICADE);
        e.getRegistry().register(WOODEN_BARRICADE);
    }

    public static void registerBlockItems(RegistryEvent.Register<Item> e) {
        e.getRegistry().register(new ItemBlock(BROKEN_BARRICADE).setRegistryName(BROKEN_BARRICADE.getRegistryName()));
        e.getRegistry().register(new ItemBlock(WOODEN_BARRICADE).setRegistryName(WOODEN_BARRICADE.getRegistryName()));
    }

    public static void registerRenders(ModelRegistryEvent event) {
        registerRender(Item.getItemFromBlock(BROKEN_BARRICADE));
        registerRender(Item.getItemFromBlock(WOODEN_BARRICADE));
    }

    private static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

}

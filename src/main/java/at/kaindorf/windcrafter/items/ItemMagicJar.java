package at.kaindorf.windcrafter.items;

import at.kaindorf.windcrafter.creativetab.ModCreativeTabs;
import net.minecraft.item.Item;

public class ItemMagicJar extends Item {

    public ItemMagicJar(boolean large) {
        setUnlocalizedName(large ? "largemagic" : "smallmagic");
        setRegistryName(large ? "largemagic" : "smallmagic");
        setCreativeTab(ModCreativeTabs.MOD_OTHER);
    }

}

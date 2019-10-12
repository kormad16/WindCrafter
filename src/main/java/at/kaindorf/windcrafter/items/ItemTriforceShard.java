package at.kaindorf.windcrafter.items;

import at.kaindorf.windcrafter.creativetab.ModCreativeTabs;
import net.minecraft.item.Item;

public class ItemTriforceShard extends Item {

    public ItemTriforceShard(int number) {
        setUnlocalizedName("triforceshard" + (number+1));
        setRegistryName("triforceshard" + (number+1));
        setCreativeTab(ModCreativeTabs.MOD_OTHER);
        setMaxStackSize(1);
    }
}

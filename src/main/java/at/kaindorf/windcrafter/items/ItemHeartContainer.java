package at.kaindorf.windcrafter.items;

import at.kaindorf.windcrafter.creativetab.ModCreativeTabs;
import net.minecraft.item.Item;

public class ItemHeartContainer extends Item {

    public ItemHeartContainer() {
        setUnlocalizedName("heartcontainer");
        setRegistryName("heartcontainer");
        setCreativeTab(ModCreativeTabs.MOD_OTHER);
        setMaxStackSize(1);
    }

}

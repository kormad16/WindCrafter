package at.kaindorf.windcrafter.items;

import at.kaindorf.windcrafter.creativetab.ModCreativeTabs;
import net.minecraft.item.Item;

public class ItemMobDrop extends Item {

    public static final String[] ids = {"joypendant","skull_necklace"};

    public ItemMobDrop(int num) {
        setUnlocalizedName(ids[num]);
        setRegistryName(ids[num]);
        setCreativeTab(ModCreativeTabs.MOD_MOBS);
    }

}

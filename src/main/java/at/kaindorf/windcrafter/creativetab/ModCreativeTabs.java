package at.kaindorf.windcrafter.creativetab;

import at.kaindorf.windcrafter.init.ItemManager;
import at.kaindorf.windcrafter.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ModCreativeTabs {

    public static final CreativeTabs MOD_WEAPONS = (new CreativeTabs("mod_weapons") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ItemManager.HEROS_SWORD);
        }
    });

    public static final CreativeTabs MOD_OTHER = (new CreativeTabs("mod_other") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ItemManager.TRIFORCESHARD[0]);
        }
    });

}

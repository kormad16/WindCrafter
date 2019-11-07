package at.kaindorf.windcrafter.creativetab;

import at.kaindorf.windcrafter.WindcrafterMod;
import at.kaindorf.windcrafter.init.ItemManager;
import at.kaindorf.windcrafter.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModCreativeTabs {

    public static final CreativeTabs MOD_WEAPONS = (new CreativeTabs("mod_weapons") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ItemManager.HEROS_SWORD);
        }
    });

    public static final CreativeTabs MOD_MOBS = (new CreativeTabs("mod_mobs") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(Items.SKULL);
        }
        @Override
        @SideOnly(Side.CLIENT)
        public void displayAllRelevantItems(NonNullList<ItemStack> itemList) {
            super.displayAllRelevantItems(itemList);
            for (EntityList.EntityEggInfo eggInfo : EntityList.ENTITY_EGGS.values())
            {
                if (eggInfo.spawnedID.getResourceDomain().equals(WindcrafterMod.MODID))
                {
                    ItemStack itemstack = new ItemStack(Items.SPAWN_EGG, 1);
                    ItemMonsterPlacer.applyEntityIdToItemStack(itemstack, eggInfo.spawnedID);
                    itemList.add(itemstack);
                }
            }
        }
    });

    public static final CreativeTabs MOD_OTHER = (new CreativeTabs("mod_other") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ItemManager.TRIFORCESHARD[0]);
        }
    });

}

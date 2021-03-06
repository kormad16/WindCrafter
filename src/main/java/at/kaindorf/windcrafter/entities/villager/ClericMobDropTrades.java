package at.kaindorf.windcrafter.entities.villager;

import at.kaindorf.windcrafter.init.ItemManager;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

import java.util.Random;

public class ClericMobDropTrades implements EntityVillager.ITradeList {

    @Override
    public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
        recipeList.add(new MerchantRecipe(new ItemStack(ItemManager.RED_CHUJELLY, 1), new ItemStack(Items.EMERALD, 1)));
        recipeList.add(new MerchantRecipe(new ItemStack(ItemManager.GREEN_CHUJELLY, 1), new ItemStack(Items.EMERALD, 1)));
        recipeList.add(new MerchantRecipe(new ItemStack(ItemManager.BLUE_CHUJELLY, 1), new ItemStack(Items.EMERALD, 3)));
        recipeList.add(new MerchantRecipe(new ItemStack(ItemManager.BOKO_SEED, 4), new ItemStack(Items.EMERALD, 1), new ItemStack(ItemManager.RED_CHUJELLY, 2)));
    }
}
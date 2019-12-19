package at.kaindorf.windcrafter.entities.villager;

import at.kaindorf.windcrafter.init.ItemManager;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

import java.util.Random;

public class ArmorerEquipmentTrades implements EntityVillager.ITradeList {

    @Override
    public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
        recipeList.add(new MerchantRecipe(new ItemStack(ItemManager.JOY_PENDANT, 40), new ItemStack(ItemManager.HEROS_CHARM, 1)));
    }
}
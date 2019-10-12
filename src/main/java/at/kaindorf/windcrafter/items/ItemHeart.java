package at.kaindorf.windcrafter.items;

import at.kaindorf.windcrafter.WindcrafterMod;
import at.kaindorf.windcrafter.creativetab.ModCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class ItemHeart extends Item {

    private static final SoundEvent heartSoundEvent = new SoundEvent(new ResourceLocation(WindcrafterMod.MODID, "get_heart"));

    public ItemHeart() {
        setUnlocalizedName("heart");
        setRegistryName("heart");
        setCreativeTab(ModCreativeTabs.MOD_OTHER);
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        playerIn.playSound(heartSoundEvent, 1.0f, 1.0f);
        playerIn.heal(2.0f);
        playerIn.getHeldItem(handIn).setCount(0);
        return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
}

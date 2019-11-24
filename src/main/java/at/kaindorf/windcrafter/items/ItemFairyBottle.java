package at.kaindorf.windcrafter.items;

import at.kaindorf.windcrafter.creativetab.ModCreativeTabs;
import at.kaindorf.windcrafter.event.EventHandler;
import at.kaindorf.windcrafter.init.SoundManager;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemFairyBottle extends Item {

    public ItemFairyBottle() {
        setUnlocalizedName("fairybottle");
        setRegistryName("fairybottle");
        setCreativeTab(ModCreativeTabs.MOD_OTHER);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        stack.shrink(1);
        playerIn.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
        playerIn.playSound(SoundManager.fairyHealSoundEvent, 1.0f, 1.0f);
        playerIn.heal((float)(playerIn.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue()/2));
        playerIn.getEntityData().setInteger("FairyParticleTimer",  (int) EventHandler.FAIRY_PARTICLE_MAX);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

}

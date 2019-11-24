package at.kaindorf.windcrafter.items;

import at.kaindorf.windcrafter.creativetab.ModCreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemHerosCharm extends Item {

    public ItemHerosCharm() {
        setUnlocalizedName("heros_charm");
        setRegistryName("heros_charm");
        setCreativeTab(ModCreativeTabs.MOD_OTHER);
        setMaxStackSize(1);
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    @Nullable
    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EntityEquipmentSlot.HEAD;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack held = playerIn.getHeldItem(handIn);
        EntityEquipmentSlot slot = EntityLiving.getSlotForItemStack(held);
        ItemStack equipped = playerIn.getItemStackFromSlot(slot);

        if (equipped.isEmpty())
        {
            playerIn.setItemStackToSlot(slot, held.copy());
            held.setCount(0);
            return new ActionResult<>(EnumActionResult.SUCCESS, held);
        }
        else
            return new ActionResult<>(EnumActionResult.FAIL, held);
    }
}

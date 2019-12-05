package at.kaindorf.windcrafter.items;

import at.kaindorf.windcrafter.creativetab.ModCreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemHealPotion extends Item {

    public static final String[] ids = {"redpotion", "greenpotion", "bluepotion"};

    private int type;

    public ItemHealPotion(int num) {
        setUnlocalizedName(ids[num]);
        setRegistryName(ids[num]);
        setCreativeTab(ModCreativeTabs.MOD_OTHER);
        type = num;
    }

    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }

    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 32;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        playerIn.setActiveHand(handIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        EntityPlayer p = entityLiving instanceof EntityPlayer ? (EntityPlayer)entityLiving : null;
        if (p == null || !p.capabilities.isCreativeMode)
            stack.shrink(1);
        if(p != null) {
            switch (type) {
                case 0: // Red Potion
                    p.heal(20.0f);
                    break;
                case 1: // Green Potion
                    p.getEntityData().setInteger("ZeldaMagic", p.getEntityData().getInteger("ZeldaMagicMax"));
                    break;
                case 2: // Blue Potion
                    p.heal(20.0f);
                    p.getEntityData().setInteger("ZeldaMagic", p.getEntityData().getInteger("ZeldaMagicMax"));
                    break;
            }
        }
        if (p == null || !p.capabilities.isCreativeMode)
        {
            if (stack.isEmpty())
                return new ItemStack(Items.GLASS_BOTTLE);
            if (p != null)
                p.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
        }
        return stack;
    }

}

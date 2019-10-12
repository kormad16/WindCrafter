package at.kaindorf.windcrafter.items;

import at.kaindorf.windcrafter.WindcrafterMod;
import at.kaindorf.windcrafter.creativetab.ModCreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class ItemHeartContainer extends Item {

    private static final SoundEvent heartContainerSoundEvent = new SoundEvent(new ResourceLocation(WindcrafterMod.MODID, "heart_container"));

    public ItemHeartContainer() {
        setUnlocalizedName("heartcontainer");
        setRegistryName("heartcontainer");
        setCreativeTab(ModCreativeTabs.MOD_OTHER);
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        playerIn.playSound(heartContainerSoundEvent, 1.0f, 1.0f);
        playerIn.getHeldItem(handIn).setCount(0);
        IAttributeInstance health = playerIn.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
        if(health.getBaseValue() < 40)
            health.setBaseValue(health.getBaseValue() + 2);
        playerIn.heal((float)health.getBaseValue());
        return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

}

package at.kaindorf.windcrafter.items;

import at.kaindorf.windcrafter.creativetab.ModCreativeTabs;
import at.kaindorf.windcrafter.entities.projectiles.EntityFireArrow;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFireArrow extends ItemArrow {

    public ItemFireArrow() {
        setUnlocalizedName("firearrow");
        setRegistryName("firearrow");
        setCreativeTab(ModCreativeTabs.MOD_WEAPONS);
    }

    @Override
    public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
        return new EntityFireArrow(worldIn, shooter);
    }


}

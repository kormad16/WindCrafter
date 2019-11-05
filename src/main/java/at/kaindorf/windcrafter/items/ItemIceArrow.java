package at.kaindorf.windcrafter.items;

import at.kaindorf.windcrafter.creativetab.ModCreativeTabs;
import at.kaindorf.windcrafter.entities.projectiles.EntityFireArrow;
import at.kaindorf.windcrafter.entities.projectiles.EntityIceArrow;
import at.kaindorf.windcrafter.event.EventHandler;
import at.kaindorf.windcrafter.init.ItemManager;
import at.kaindorf.windcrafter.init.SoundManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemIceArrow extends ItemArrow {

    public ItemIceArrow() {
        setUnlocalizedName("icearrow");
        setRegistryName("icearrow");
        setCreativeTab(ModCreativeTabs.MOD_WEAPONS);
    }

    @Override
    public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
        try {
            EntityPlayer p = (EntityPlayer)shooter;
            if(!p.isCreative()) {
                if (p.getEntityData().getInteger("ZeldaMagic") < 5) {
                    p.playSound(SoundManager.errorSoundEvent, 1.0f, 1.0f);
                    return new EntityTippedArrow(worldIn, shooter);
                } else {
                    p.getEntityData().setInteger("ZeldaMagic", shooter.getEntityData().getInteger("ZeldaMagic") - 5);
                }
            }
        } catch(Exception e) {}
        return new EntityIceArrow(worldIn, shooter);
    }


}

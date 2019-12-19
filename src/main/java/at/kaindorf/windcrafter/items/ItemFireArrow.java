package at.kaindorf.windcrafter.items;

import at.kaindorf.windcrafter.creativetab.ModCreativeTabs;
import at.kaindorf.windcrafter.entities.projectiles.EntityFireArrow;
import at.kaindorf.windcrafter.event.EventHandler;
import at.kaindorf.windcrafter.gui.GuiZeldaMagic;
import at.kaindorf.windcrafter.init.SoundManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.awt.*;

public class ItemFireArrow extends ItemArrow {

    public ItemFireArrow() {
        setUnlocalizedName("firearrow");
        setRegistryName("firearrow");
        setCreativeTab(ModCreativeTabs.MOD_WEAPONS);
    }

    @Override
    public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
        try {
            EntityPlayer p = (EntityPlayer)shooter;
            if(!p.isCreative()) {
                if (p.getDataManager().get(GuiZeldaMagic.ZELDA_MAGIC) < 5) {
                    p.playSound(SoundManager.errorSoundEvent, 1.0f, 1.0f);
                    return new EntityTippedArrow(worldIn, shooter);
                } else {
                    p.getDataManager().set(GuiZeldaMagic.ZELDA_MAGIC, shooter.getDataManager().get(GuiZeldaMagic.ZELDA_MAGIC) - 5);
                }
            }
        } catch(Exception e) {}
        return new EntityFireArrow(worldIn, shooter);
    }


}

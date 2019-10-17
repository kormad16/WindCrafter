package at.kaindorf.windcrafter.entities.projectiles;

import at.kaindorf.windcrafter.init.ItemManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityLightArrow extends EntityArrow {

    public EntityLightArrow(World worldIn)
    {
        super(worldIn);
    }

    public EntityLightArrow(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    public EntityLightArrow(World worldIn, EntityLivingBase shooter) { super(worldIn, shooter); setDamage(1024); }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ItemManager.ICEARROW);
    }

    @Override
    protected void arrowHit(EntityLivingBase living) {
        super.arrowHit(living);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (this.world.isRemote)
        {
            if (this.inGround)
            {
                if (this.timeInGround % 5 == 0)
                {
                    this.spawnPotionParticles(2);
                }
            }
            else
            {
                this.spawnPotionParticles(3);
            }
        }
    }

    private void spawnPotionParticles(int particleCount)
    {
        if (particleCount > 0)
        {
            for (int j = 0; j < particleCount; ++j)
            {
                this.world.spawnParticle(EnumParticleTypes.CRIT, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0,-0.00000001,0);
            }
        }
    }
}

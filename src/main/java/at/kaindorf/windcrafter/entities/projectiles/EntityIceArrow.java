package at.kaindorf.windcrafter.entities.projectiles;

import at.kaindorf.windcrafter.init.ItemManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityIceArrow extends EntityArrow {

    public EntityIceArrow(World worldIn)
    {
        super(worldIn);
    }

    public EntityIceArrow(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    public EntityIceArrow(World worldIn, EntityLivingBase shooter) { super(worldIn, shooter); }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ItemManager.ICEARROW);
    }

    @Override
    protected void arrowHit(EntityLivingBase living) {
        super.arrowHit(living);
        for(PotionEffect p : PotionTypes.SLOWNESS.getEffects())
            living.addPotionEffect(new PotionEffect(p.getPotion(), 75, 50));
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
                this.world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0,-0.00000001,0);
            }
        }
    }
}

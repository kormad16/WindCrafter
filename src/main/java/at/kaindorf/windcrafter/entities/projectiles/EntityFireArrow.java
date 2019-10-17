package at.kaindorf.windcrafter.entities.projectiles;

import at.kaindorf.windcrafter.init.ItemManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityFireArrow extends EntityArrow {

    public EntityFireArrow(World worldIn)
    {
        super(worldIn);
    }

    public EntityFireArrow(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    public EntityFireArrow(World worldIn, EntityLivingBase shooter) { super(worldIn, shooter); }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ItemManager.FIREARROW);
    }

    @Override
    protected void arrowHit(EntityLivingBase living) {
        super.arrowHit(living);
        living.setFire(5);
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
                this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0,-0.0125,0);
            }
        }
    }
}

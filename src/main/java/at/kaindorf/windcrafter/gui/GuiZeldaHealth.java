package at.kaindorf.windcrafter.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.FoodStats;
import net.minecraft.util.math.MathHelper;

public class GuiZeldaHealth extends GuiIngame {

    public GuiZeldaHealth(Minecraft mcIn) {
        super(mcIn);
    }

    public void drawHealth(int width, int height) {
        EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
        int i = MathHelper.ceil(entityplayer.getHealth());
        boolean flag = this.healthUpdateCounter > (long)this.updateCounter && (this.healthUpdateCounter - (long)this.updateCounter) / 3L % 2L == 1L;
        this.playerHealth = i;
        int j = this.lastPlayerHealth;
        this.rand.setSeed((long)(this.updateCounter * 312871));
        FoodStats foodstats = entityplayer.getFoodStats();
        IAttributeInstance iattributeinstance = entityplayer.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
        int l = 5;
        int j1 = 5;
        float f = MathHelper.floor((float)iattributeinstance.getAttributeValue());
        if(f%2 > 0)
            f-=f%2;
        int k1 = MathHelper.ceil(entityplayer.getAbsorptionAmount());
        int l1 = MathHelper.ceil((f + (float)k1) / 2.0F / 10.0F);
        int i2 = Math.max(10 - (l1 - 2), 3);
        int l2 = k1;

        for (int j5 = MathHelper.ceil((f + (float)k1) / 2.0F) - 1; j5 >= 0; --j5)
        {
            int k5 = 16;

            if (entityplayer.isPotionActive(MobEffects.POISON))
            {
                k5 += 36;
            }
            else if (entityplayer.isPotionActive(MobEffects.WITHER))
            {
                k5 += 72;
            }

            int i4 = 0;

            if (flag)
            {
                i4 = 1;
            }

            int j4 = MathHelper.ceil((float)(j5 + 1) / 10.0F) - 1;
            int k4 = l + j5 % 10 * 8;
            int l4 = j1 + j4 * i2;

            int i5 = 0;

            if (entityplayer.world.getWorldInfo().isHardcoreModeEnabled())
            {
                i5 = 5;
            }

            this.drawTexturedModalRect(k4, l4, 16 + i4 * 9, 9 * i5, 9, 9);

            if (flag)
            {
                if (j5 * 2 + 1 < j)
                {
                    this.drawTexturedModalRect(k4, l4, k5 + 54, 9 * i5, 9, 9);
                }

                if (j5 * 2 + 1 == j)
                {
                    this.drawTexturedModalRect(k4, l4, k5 + 63, 9 * i5, 9, 9);
                }
            }

            if (l2 > 0)
            {
                if (l2 == k1 && k1 % 2 == 1)
                {
                    this.drawTexturedModalRect(k4, l4, k5 + 153, 9 * i5, 9, 9);
                    --l2;
                }
                else
                {
                    this.drawTexturedModalRect(k4, l4, k5 + 144, 9 * i5, 9, 9);
                    l2 -= 2;
                }
            }
            else
            {
                if (j5 * 2 + 1 < i)
                {
                    this.drawTexturedModalRect(k4, l4, k5 + 36, 9 * i5, 9, 9);
                }

                if (j5 * 2 + 1 == i)
                {
                    this.drawTexturedModalRect(k4, l4, k5 + 45, 9 * i5, 9, 9);
                }
            }
        }
    }

}

package at.kaindorf.windcrafter.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.SharedMonsterAttributes;

public class GuiZeldaMagic extends GuiIngame {

    public GuiZeldaMagic(Minecraft mcIn) {
        super(mcIn);
    }

    public void drawMagic(int width, int height) {
        if(this.mc.player.capabilities.isCreativeMode)
            return;

        this.mc.getTextureManager().bindTexture(Gui.ICONS);
        int i = this.mc.player.xpBarCap();

        if (i > 0)
        {
            int j = 182;
            int k = 91;
            try {
                k = (int) ((this.mc.player.getEntityData().getInteger("ZeldaMagic") / this.mc.player.getEntityData().getInteger("ZeldaMagicMax")) * 183.0F) / (this.mc.player.getEntityData().getInteger("ZeldaMagicMax") == 100 ? 2 : 1);
            } catch(ArithmeticException e) {}
            int l = 16 + (this.mc.player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue() > 20 ? 10 : 0);
            this.drawTexturedModalRect(5, l, 0, 64, 182/(this.mc.player.getEntityData().getInteger("ZeldaMagicMax")==100?2:1), 5);

            if (k > 0)
            {
                this.drawTexturedModalRect(5, l, 0, 69, k, 5);
            }
        }

        this.mc.mcProfiler.endSection();

        if (this.mc.player.experienceLevel > 0)
        {
            this.mc.mcProfiler.startSection("expLevel");
            String s = "" + this.mc.player.experienceLevel;
            int i1 = (width - this.getFontRenderer().getStringWidth(s)) / 2;
            int j1 = height - 31 - 4;
            this.getFontRenderer().drawString(s, i1 + 1, j1, 0);
            this.getFontRenderer().drawString(s, i1 - 1, j1, 0);
            this.getFontRenderer().drawString(s, i1, j1 + 1, 0);
            this.getFontRenderer().drawString(s, i1, j1 - 1, 0);
            this.getFontRenderer().drawString(s, i1, j1, 8453920);
            this.mc.mcProfiler.endSection();
        }
    }

}

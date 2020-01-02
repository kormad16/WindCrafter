package at.kaindorf.windcrafter.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

public class GuiZeldaMagic extends GuiIngame {

    public GuiZeldaMagic(Minecraft mcIn) {
        super(mcIn);
    }

    public static final DataParameter<Integer> ZELDA_MAGIC = EntityDataManager.<Integer>createKey(EntityPlayer.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> ZELDA_MAGIC_MAX = EntityDataManager.<Integer>createKey(EntityPlayer.class, DataSerializers.VARINT);
    public static final DataParameter<Boolean> ZELDA_MAGIC_INIT = EntityDataManager.<Boolean>createKey(EntityPlayer.class, DataSerializers.BOOLEAN);

    public void drawMagic(int width, int height) {
        if(this.mc.player.capabilities.isCreativeMode)
            return;

        this.mc.mcProfiler.startSection("magicMeter");

        this.mc.getTextureManager().bindTexture(Gui.ICONS);

        try {
            if (this.mc.player.getDataManager().get(ZELDA_MAGIC_INIT)) {

                int k = 0;
                if (this.mc.player.getDataManager().get(ZELDA_MAGIC_MAX) > 0)
                    k = (int) (183.0f * (((double) (this.mc.player.getDataManager().get(ZELDA_MAGIC) / (double) (this.mc.player.getDataManager().get(ZELDA_MAGIC_MAX))) / (double) (this.mc.player.getDataManager().get(ZELDA_MAGIC_MAX) == 100 ? 2 : 1))));
                int l = 16 + (this.mc.player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue() > 20 ? 10 : 0);
                this.drawTexturedModalRect(5, l, 0, 64, 182 / (this.mc.player.getDataManager().get(ZELDA_MAGIC_MAX) == 100 ? 2 : 1), 5);
                if (k > 0) {
                    this.drawTexturedModalRect(5, l, 0, 69, k, 5);
                }

            }
        } catch(Exception e) {
            try {
                this.mc.player.getDataManager().register(GuiZeldaMagic.ZELDA_MAGIC, 100);
                this.mc.player.getDataManager().register(GuiZeldaMagic.ZELDA_MAGIC_MAX, 100);
                this.mc.player.getDataManager().register(GuiZeldaMagic.ZELDA_MAGIC_INIT, Boolean.TRUE);
            } catch(Exception ex) {}
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

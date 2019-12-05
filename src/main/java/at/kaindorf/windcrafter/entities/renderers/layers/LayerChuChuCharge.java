package at.kaindorf.windcrafter.entities.renderers.layers;

import at.kaindorf.windcrafter.entities.enemies.EntityChuChu;
import at.kaindorf.windcrafter.entities.models.ModelChuChu;
import at.kaindorf.windcrafter.entities.renderers.RendererChuChu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerChuChuCharge implements LayerRenderer<EntityChuChu>
{
    private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("windcrafter:textures/entities/chuChu_charge.png");
    private final RendererChuChu chuChuRenderer;
    private final ModelChuChu chuChuModel = new ModelChuChu(0.5f);

    public LayerChuChuCharge(RendererChuChu chuChuRendererIn)
    {
        this.chuChuRenderer = chuChuRendererIn;
    }

    public void doRenderLayer(EntityChuChu chuChu, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (chuChu.isCharged())
        {
            boolean flag = chuChu.isInvisible();
            GlStateManager.depthMask(!flag);
            this.chuChuRenderer.bindTexture(LIGHTNING_TEXTURE);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float f = (float)chuChu.ticksExisted + partialTicks;
            GlStateManager.translate(f * 0.01F, f * 0.01F, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            float f1 = 0.5F;
            GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            this.chuChuModel.setModelAttributes(this.chuChuRenderer.getMainModel());
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            this.chuChuModel.render(chuChu, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(flag);
        }
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}
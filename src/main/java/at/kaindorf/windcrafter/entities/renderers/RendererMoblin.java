package at.kaindorf.windcrafter.entities.renderers;

import at.kaindorf.windcrafter.entities.enemies.EntityMoblin;
import at.kaindorf.windcrafter.entities.models.ModelMoblin;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;

public class RendererMoblin extends RenderLiving<EntityMoblin> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("windcrafter:textures/entities/moblin.png");

    public RendererMoblin(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelMoblin(), ModelMoblin.SHADOW_SIZE);
        this.addLayer(new LayerHeldItem(this) {
            @Override
            protected void translateToHand(EnumHandSide p_191361_1_) {
                ((ModelMoblin) this.livingEntityRenderer.getMainModel()).postRenderArm(0.0625F, p_191361_1_); // Fix Class Cast Exception
            }
        });
    }

    protected ResourceLocation getEntityTexture(EntityMoblin entity) {
        return TEXTURE;
    }

    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.0F, 0.1875F, 0.0F);
    }

}

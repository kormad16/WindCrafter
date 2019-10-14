package at.kaindorf.windcrafter.entities.renderers;

import at.kaindorf.windcrafter.entities.enemies.EntityBokoblin;
import at.kaindorf.windcrafter.entities.models.ModelBokoblin;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RendererBokoblin extends RenderLiving<EntityBokoblin> {

    private static final ResourceLocation BLUE = new ResourceLocation("windcrafter:textures/entities/bokoblin_blue.png");
    private static final ResourceLocation GREEN = new ResourceLocation("windcrafter:textures/entities/bokoblin_green.png");
    private static final ResourceLocation PINK = new ResourceLocation("windcrafter:textures/entities/bokoblin_pink.png");

    public static final Factory FACTORY = new Factory();

    private RendererBokoblin(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelBokoblin(), ModelBokoblin.SHADOW_SIZE);
        this.addLayer(new LayerHeldItem(this) {
            @Override
            protected void translateToHand(EnumHandSide p_191361_1_) {
                ((ModelBokoblin) this.livingEntityRenderer.getMainModel()).postRenderArm(0.0625F, p_191361_1_); // Fix Class Cast Exception
            }
        });
    }

    protected ResourceLocation getEntityTexture(EntityBokoblin entity) {
        switch (entity.getProfession()) {
            case 0:
                return BLUE;
            case 1:
                return GREEN;
            case 2:
                return PINK;
        }
        return BLUE;
    }

    public static class Factory implements IRenderFactory<EntityBokoblin> {
        @Override
        public Render<EntityBokoblin> createRenderFor(RenderManager manager) {
            return new RendererBokoblin(manager);
        }
    }

    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.0F, 0.1875F, 0.0F);
    }

}

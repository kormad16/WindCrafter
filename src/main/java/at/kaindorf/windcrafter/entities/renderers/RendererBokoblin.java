package at.kaindorf.windcrafter.entities.renderers;

import at.kaindorf.windcrafter.entities.enemies.EntityBokoblin;
import at.kaindorf.windcrafter.entities.models.ModelBokoblin;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

public class RendererBokoblin extends RenderLiving<EntityBokoblin> {

    private static final ResourceLocation BLUE = new ResourceLocation("windcrafter:textures/entities/bokoblin_blue.png");

    public static final Factory FACTORY = new Factory();

    private RendererBokoblin(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelBokoblin(), ModelBokoblin.SHADOW_SIZE);
    }

    protected ResourceLocation getEntityTexture(EntityBokoblin entity) {
        return BLUE;
    }

    public static class Factory implements IRenderFactory<EntityBokoblin> {

        @Override
        public Render<? super EntityBokoblin> createRenderFor(RenderManager manager) {
            return new RendererBokoblin(manager);
        }

    }


}

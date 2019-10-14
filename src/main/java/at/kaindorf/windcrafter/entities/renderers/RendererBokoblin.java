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
    private static final ResourceLocation GREEN = new ResourceLocation("windcrafter:textures/entities/bokoblin_green.png");
    private static final ResourceLocation PINK = new ResourceLocation("windcrafter:textures/entities/bokoblin_pink.png");

    public static final Factory FACTORY = new Factory();

    private RendererBokoblin(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelBokoblin(), ModelBokoblin.SHADOW_SIZE);
    }

    protected ResourceLocation getEntityTexture(EntityBokoblin entity) {
        switch (entity.getProfession()) {
            case 0: return BLUE;
            case 1: return GREEN;
            case 2: return PINK;
        }
        return BLUE;
    }

    public static class Factory implements IRenderFactory<EntityBokoblin> {

        @Override
        public Render<? super EntityBokoblin> createRenderFor(RenderManager manager) {
            return new RendererBokoblin(manager);
        }

    }


}

package at.kaindorf.windcrafter.entities.renderers;

import at.kaindorf.windcrafter.entities.projectiles.EntityFireArrow;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RendererFireArrow extends RenderArrow<EntityFireArrow> {

    public static final ResourceLocation FIREARROW = new ResourceLocation("windcrafter:textures/entities/firearrow.png");

    public RendererFireArrow(RenderManager renderManagerIn) { super(renderManagerIn); }

    @Override
    protected ResourceLocation getEntityTexture(EntityFireArrow entity) {
        return FIREARROW;
    }
}

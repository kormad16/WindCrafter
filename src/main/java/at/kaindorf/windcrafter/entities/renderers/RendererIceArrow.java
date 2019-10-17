package at.kaindorf.windcrafter.entities.renderers;

import at.kaindorf.windcrafter.entities.projectiles.EntityIceArrow;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RendererIceArrow extends RenderArrow<EntityIceArrow> {

    public static final ResourceLocation ICEARROW = new ResourceLocation("windcrafter:textures/entities/icearrow.png");

    public RendererIceArrow(RenderManager renderManagerIn) { super(renderManagerIn); }

    @Override
    protected ResourceLocation getEntityTexture(EntityIceArrow entity) {
        return ICEARROW;
    }
}

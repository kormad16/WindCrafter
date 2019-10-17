package at.kaindorf.windcrafter.entities.renderers;

import at.kaindorf.windcrafter.entities.projectiles.EntityFireArrow;
import at.kaindorf.windcrafter.entities.projectiles.EntityLightArrow;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RendererLightArrow extends RenderArrow<EntityLightArrow> {

    public static final ResourceLocation FIREARROW = new ResourceLocation("windcrafter:textures/entities/firearrow.png");

    public RendererLightArrow(RenderManager renderManagerIn) { super(renderManagerIn); }

    @Override
    protected ResourceLocation getEntityTexture(EntityLightArrow entity) {
        return FIREARROW;
    }
}

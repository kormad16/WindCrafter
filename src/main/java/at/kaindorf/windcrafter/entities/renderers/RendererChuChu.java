package at.kaindorf.windcrafter.entities.renderers;

import at.kaindorf.windcrafter.entities.enemies.EntityChuChu;
import at.kaindorf.windcrafter.entities.models.ModelChuChu;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RendererChuChu extends RenderLiving<EntityChuChu> {

    private static final ResourceLocation RED = new ResourceLocation("windcrafter:textures/entities/chuchu_red.png");
    private static final ResourceLocation GREEN = new ResourceLocation("windcrafter:textures/entities/chuchu_red.png");
    private static final ResourceLocation BLUE = new ResourceLocation("windcrafter:textures/entities/chuchu_red.png");
    private static final ResourceLocation BLACK = new ResourceLocation("windcrafter:textures/entities/chuchu_red.png");

    public RendererChuChu(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelChuChu(), ModelChuChu.SHADOW_SIZE);
    }

    @Override
    protected void preRenderCallback(EntityChuChu entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(0.999F, 0.999F, 0.999F);
        float f2 = (entitylivingbaseIn.prevSquishFactor + (entitylivingbaseIn.squishFactor - entitylivingbaseIn.prevSquishFactor) * partialTickTime) / (0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        GlStateManager.scale(f3, 1.0F / f3, f3);
    }

    protected ResourceLocation getEntityTexture(EntityChuChu entity) {
        switch (entity.getProfession()) {
            case 0:
                return RED;
            case 1:
                return GREEN;
            case 2:
                return BLUE;
            case 3:
                return BLACK;
        }
        return RED;
    }

}
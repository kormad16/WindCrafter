package at.kaindorf.windcrafter.entities.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelChuChu extends ModelBase {

	public static final float SHADOW_SIZE = 0.5f;

	private final ModelRenderer base1;
	private final ModelRenderer base2;
	private final ModelRenderer body;
	private final ModelRenderer head;

	public ModelChuChu() {
		this(0);
	}

	public ModelChuChu(float scale) {
		textureWidth = 64;
		textureHeight = 64;

		base1 = new ModelRenderer(this);
		base1.setRotationPoint(0.0F, 24.0F, 0.0F);
		base1.cubeList.add(new ModelBox(base1, 0, 0, -2.0F, -2.0F, -6.0F, 4, 2, 12, scale, false));

		base2 = new ModelRenderer(this);
		base2.setRotationPoint(0.0F, 24.0F, 0.0F);
		base2.cubeList.add(new ModelBox(base2, 0, 14, -6.0F, -2.0F, -2.0F, 12, 2, 4, scale, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 0, 0, -1.0F, -6.0F, -1.0F, 2, 3, 2, scale, false));
		body.cubeList.add(new ModelBox(body, 24, 24, -2.0F, -3.0F, -2.0F, 4, 3, 4, scale, false));
		body.cubeList.add(new ModelBox(body, 20, 0, -2.0F, -9.0F, -2.0F, 4, 3, 4, scale, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 24.0F, 0.0F);
		head.cubeList.add(new ModelBox(head, 0, 20, -3.0F, -15.0F, -3.0F, 6, 6, 6, scale, false));
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
//		setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

		this.base1.render(scale);
		this.base2.render(scale);
		this.body.render(scale);
		this.head.render(scale);
	}

//	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
//	{
//
//
//
//	}

}
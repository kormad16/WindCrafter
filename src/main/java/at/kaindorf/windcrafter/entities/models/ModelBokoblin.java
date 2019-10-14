package at.kaindorf.windcrafter.entities.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModelBokoblin extends ModelBase {

	public static final float SHADOW_SIZE = 0.5f;

	private final ModelRenderer head;
	private final ModelRenderer body;
	private final ModelRenderer right_arm;
	private final ModelRenderer left_arm;
	private final ModelRenderer right_leg;
	private final ModelRenderer left_leg;

	public ModelBokoblin.ArmPose leftArmPose;
	public ModelBokoblin.ArmPose rightArmPose;

	public boolean isSneak;

	public ModelBokoblin() {
		leftArmPose = ModelBokoblin.ArmPose.EMPTY;
		rightArmPose = ModelBokoblin.ArmPose.EMPTY;

		textureWidth = 64;
		textureHeight = 64;

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 0.15F, -0.15F);
		head.cubeList.add(new ModelBox(head, 0, 15, -3.0F, -0.9F, -4.35F, 6, 5, 6, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 24, 15, -3.0F, -1.4F, -4.35F, 6, 1, 6, -1.0F, false));
		head.cubeList.add(new ModelBox(head, 48, 6, 2.0F, -0.4F, -2.35F, 5, 1, 3, -1.0F, false));
		head.cubeList.add(new ModelBox(head, 48, 10, -7.0F, -0.4F, -2.35F, 5, 1, 3, -1.0F, false));
		head.cubeList.add(new ModelBox(head, 46, 0, -2.0F, -1.4F, -7.35F, 4, 1, 5, -1.0F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 2.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 0, 0, -4.0F, 2.1F, -2.0F, 8, 11, 4, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 38, -4.0F, 2.1F, -2.0F, 8, 11, 4, 0.25F, false));

		right_arm = new ModelRenderer(this);
		right_arm.setRotationPoint(-5.0F, 5.5F, 0.0F);
		right_arm.cubeList.add(new ModelBox(right_arm, 24, 0, -2.0F, -1.0F, -1.5F, 3, 12, 3, 0.0F, false));

		left_arm = new ModelRenderer(this);
		left_arm.setRotationPoint(5.0F, 5.5F, 0.0F);
		left_arm.cubeList.add(new ModelBox(left_arm, 21, 23, -1.0F, -1.0F, -1.5F, 3, 12, 3, 0.0F, false));

		right_leg = new ModelRenderer(this);
		right_leg.setRotationPoint(-2.25F, 12.0F, 0.0F);
		right_leg.cubeList.add(new ModelBox(right_leg, 33, 33, -1.75F, 3.0F, -1.5F, 3, 9, 3, 0.0F, false));

		left_leg = new ModelRenderer(this);
		left_leg.setRotationPoint(2.25F, 12.0F, 0.0F);
		left_leg.cubeList.add(new ModelBox(left_leg, 0, 26, -1.25F, 3.0F, -1.5F, 3, 9, 3, 0.0F, false));
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		GlStateManager.pushMatrix();

		if (entityIn.isSneaking())
		{
			GlStateManager.translate(0.0F, 0.2F, 0.0F);
		}

		this.head.render(scale);
		this.body.render(scale);
		this.right_arm.render(scale);
		this.left_arm.render(scale);
		this.right_leg.render(scale);
		this.left_leg.render(scale);

		GlStateManager.popMatrix();
	}

	public void setModelAttributes(ModelBase model)
	{
		super.setModelAttributes(model);

		if (model instanceof ModelBokoblin)
		{
			ModelBokoblin modelBokoblin = (ModelBokoblin)model;
			this.leftArmPose = modelBokoblin.leftArmPose;
			this.rightArmPose = modelBokoblin.rightArmPose;
			this.isSneak = modelBokoblin.isSneak;
		}
	}

	public void setVisible(boolean visible)
	{
		this.head.showModel = visible;
		this.body.showModel = visible;
		this.right_arm.showModel = visible;
		this.left_arm.showModel = visible;
		this.right_leg.showModel = visible;
		this.left_leg.showModel = visible;
	}

	/**
	 * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
	 * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
	 * "far" arms and legs can swing at most.
	 */
	@SuppressWarnings("incomplete-switch")
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		this.head.rotateAngleY = netHeadYaw * 0.017453292F;

		this.head.rotateAngleX = headPitch * 0.017453292F;

		this.body.rotateAngleY = 0.0F;
		this.right_arm.rotationPointZ = 0.0F;
		this.right_arm.rotationPointX = -5.0F;
		this.left_arm.rotationPointZ = 0.0F;
		this.left_arm.rotationPointX = 5.0F;
		float f = 1.0F;

		this.right_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
		this.left_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
		this.right_arm.rotateAngleZ = 0.0F;
		this.left_arm.rotateAngleZ = 0.0F;
		this.right_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
		this.left_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount / f;
		this.right_leg.rotateAngleY = 0.0F;
		this.left_leg.rotateAngleY = 0.0F;
		this.right_leg.rotateAngleZ = 0.0F;
		this.left_leg.rotateAngleZ = 0.0F;

		if (this.isRiding)
		{
			this.right_arm.rotateAngleX += -((float)Math.PI / 5F);
			this.left_arm.rotateAngleX += -((float)Math.PI / 5F);
			this.right_leg.rotateAngleX = -1.4137167F;
			this.right_leg.rotateAngleY = ((float)Math.PI / 10F);
			this.right_leg.rotateAngleZ = 0.07853982F;
			this.left_leg.rotateAngleX = -1.4137167F;
			this.left_leg.rotateAngleY = -((float)Math.PI / 10F);
			this.left_leg.rotateAngleZ = -0.07853982F;
		}

		this.right_arm.rotateAngleY = 0.0F;
		this.right_arm.rotateAngleZ = 0.0F;

		switch (this.leftArmPose)
		{
			case EMPTY:
				this.left_arm.rotateAngleY = 0.0F;
				break;
			case BLOCK:
				this.left_arm.rotateAngleX = this.left_arm.rotateAngleX * 0.5F - 0.9424779F;
				this.left_arm.rotateAngleY = 0.5235988F;
				break;
			case ITEM:
				this.left_arm.rotateAngleX = this.left_arm.rotateAngleX * 0.5F - ((float)Math.PI / 10F);
				this.left_arm.rotateAngleY = 0.0F;
		}

		switch (this.rightArmPose)
		{
			case EMPTY:
				this.right_arm.rotateAngleY = 0.0F;
				break;
			case BLOCK:
				this.right_arm.rotateAngleX = this.right_arm.rotateAngleX * 0.5F - 0.9424779F;
				this.right_arm.rotateAngleY = -0.5235988F;
				break;
			case ITEM:
				this.right_arm.rotateAngleX = this.right_arm.rotateAngleX * 0.5F - ((float)Math.PI / 10F);
				this.right_arm.rotateAngleY = 0.0F;
		}

		if (this.swingProgress > 0.0F)
		{
			EnumHandSide enumhandside = this.getMainHand(entityIn);
			ModelRenderer modelrenderer = this.getArmForSide(enumhandside);
			float f1 = this.swingProgress;
			this.body.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float)Math.PI * 2F)) * 0.2F;

			if (enumhandside == EnumHandSide.LEFT)
			{
				this.body.rotateAngleY *= -1.0F;
			}

			this.right_arm.rotationPointZ = MathHelper.sin(this.body.rotateAngleY) * 5.0F;
			this.right_arm.rotationPointX = -MathHelper.cos(this.body.rotateAngleY) * 5.0F;
			this.left_arm.rotationPointZ = -MathHelper.sin(this.body.rotateAngleY) * 5.0F;
			this.left_arm.rotationPointX = MathHelper.cos(this.body.rotateAngleY) * 5.0F;
			this.right_arm.rotateAngleY += this.body.rotateAngleY;
			this.left_arm.rotateAngleY += this.body.rotateAngleY;
			this.left_arm.rotateAngleX += this.body.rotateAngleY;
			f1 = 1.0F - this.swingProgress;
			f1 = f1 * f1;
			f1 = f1 * f1;
			f1 = 1.0F - f1;
			float f2 = MathHelper.sin(f1 * (float)Math.PI);
			float f3 = MathHelper.sin(this.swingProgress * (float)Math.PI) * -(this.head.rotateAngleX - 0.7F) * 0.75F;
			modelrenderer.rotateAngleX = (float)((double)modelrenderer.rotateAngleX - ((double)f2 * 1.2D + (double)f3));
			modelrenderer.rotateAngleY += this.body.rotateAngleY * 2.0F;
			modelrenderer.rotateAngleZ += MathHelper.sin(this.swingProgress * (float)Math.PI) * -0.4F;
		}

		if (this.isSneak)
		{
			this.body.rotateAngleX = 0.5F;
			this.right_arm.rotateAngleX += 0.4F;
			this.left_arm.rotateAngleX += 0.4F;
			this.right_leg.rotationPointZ = 4.0F;
			this.left_leg.rotationPointZ = 4.0F;
			this.right_leg.rotationPointY = 9.0F;
			this.left_leg.rotationPointY = 9.0F;
			this.head.rotationPointY = 1.0F;
		}
		else
		{
			this.body.rotateAngleX = 0.0F;
			this.right_leg.rotationPointZ = 0.1F;
			this.left_leg.rotationPointZ = 0.1F;
			this.right_leg.rotationPointY = 12.0F;
			this.left_leg.rotationPointY = 12.0F;
			this.head.rotationPointY = 0.0F;
		}

		this.right_arm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.left_arm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.right_arm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.left_arm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;

		if (this.rightArmPose == ModelBokoblin.ArmPose.BOW_AND_ARROW)
		{
			this.right_arm.rotateAngleY = -0.1F + this.head.rotateAngleY;
			this.left_arm.rotateAngleY = 0.1F + this.head.rotateAngleY + 0.4F;
			this.right_arm.rotateAngleX = -((float)Math.PI / 2F) + this.head.rotateAngleX;
			this.left_arm.rotateAngleX = -((float)Math.PI / 2F) + this.head.rotateAngleX;
		}
		else if (this.leftArmPose == ModelBokoblin.ArmPose.BOW_AND_ARROW)
		{
			this.right_arm.rotateAngleY = -0.1F + this.head.rotateAngleY - 0.4F;
			this.left_arm.rotateAngleY = 0.1F + this.head.rotateAngleY;
			this.right_arm.rotateAngleX = -((float)Math.PI / 2F) + this.head.rotateAngleX;
			this.left_arm.rotateAngleX = -((float)Math.PI / 2F) + this.head.rotateAngleX;
		}
	}

	public void postRenderArm(float scale, EnumHandSide side)
	{
		this.getArmForSide(side).postRender(scale);
	}

	protected ModelRenderer getArmForSide(EnumHandSide side)
	{
		return side == EnumHandSide.LEFT ? this.left_arm : this.right_arm;
	}

	protected EnumHandSide getMainHand(Entity entityIn)
	{
		if (entityIn instanceof EntityLivingBase)
		{
			EntityLivingBase entitylivingbase = (EntityLivingBase)entityIn;
			EnumHandSide enumhandside = entitylivingbase.getPrimaryHand();
			return entitylivingbase.swingingHand == EnumHand.MAIN_HAND ? enumhandside : enumhandside.opposite();
		}
		else
		{
			return EnumHandSide.RIGHT;
		}
	}

	@SideOnly(Side.CLIENT)
	public static enum ArmPose
	{
		EMPTY,
		ITEM,
		BLOCK,
		BOW_AND_ARROW;
	}

}
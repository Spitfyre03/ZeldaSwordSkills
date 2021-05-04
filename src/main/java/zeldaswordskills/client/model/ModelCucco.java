package zeldaswordskills.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelCucco extends ModelBase
{
	//fields
	ModelRenderer head;
	ModelRenderer head2;
	ModelRenderer head3;
	ModelRenderer head4;
	ModelRenderer head5;
	ModelRenderer head6;
	ModelRenderer head7;

	ModelRenderer body;
	ModelRenderer body2;
	ModelRenderer body3;
	ModelRenderer body4;

	ModelRenderer chin;
	ModelRenderer chin2;
	ModelRenderer bill;
	ModelRenderer bill2;

	ModelRenderer leftWing;
	ModelRenderer leftWing2;
	ModelRenderer leftWing3;
	ModelRenderer rightWing;
	ModelRenderer rightWing2;
	ModelRenderer rightWing3;
	ModelRenderer rightLeg;
	ModelRenderer leftLeg;

	public ModelCucco()
	{
		this(0.0F);
	}

	public ModelCucco(float f)
	{
		textureWidth = 128;
		textureHeight = 128;

		head = new ModelRenderer(this, 76, 0);
		head.addBox(-2F, -5F, -3F, 4, 6, 3);
		head.setRotationPoint(0F, 18F, 1F);
		head.setTextureSize(128, 128);
		head.mirror = true;
		setRotation(head, 0F, 0F, 0F);
		head2 = new ModelRenderer(this, 76, 18);
		head2.addBox(0F, 0F, 0F, 4, 1, 1);
		head2.setRotationPoint(-2F, 0.5F, -2F);
		head2.setTextureSize(128, 128);
		head2.mirror = true;
		setRotation(head2, 0F, 0F, 0F);
		head.addChild(head2);
		head3 = new ModelRenderer(this, 76, 9);
		head3.addBox(0F, 0F, 0F, 3, 4, 1);
		head3.setRotationPoint(-1.5F, -4.5F, -0.5F);
		head3.setTextureSize(128, 128);
		head3.mirror = true;
		setRotation(head3, 0F, 0F, 0F);
		head.addChild(head3);
		head4 = new ModelRenderer(this, 76, 14);
		head4.addBox(0F, 0F, 0F, 2, 3, 1);
		head4.setRotationPoint(-1F, -4F, 0F);
		head4.setTextureSize(128, 128);
		head4.mirror = true;
		setRotation(head4, 0F, 0F, 0F);
		head.addChild(head4);
		head5 = new ModelRenderer(this, 64, 0);
		head5.addBox(0F, 16.4F - 18F, 0.8F - 1F, 1, 3, 1);
		head5.setRotationPoint(-0.5F, -5.5F, -2F);
		head5.setTextureSize(128, 128);
		head5.mirror = true;
		setRotation(head5, -0.6283185F, 0F, 0F);
		head.addChild(head5);
		head6 = new ModelRenderer(this, 64, 4);
		head6.addBox(0F, 15.5F - 18F, 1.8F - 1F, 1, 2, 1);
		head6.setRotationPoint(-0.5F, -4F, -2F);
		head6.setTextureSize(128, 128);
		head6.mirror = true;
		setRotation(head6, -0.6283185F, 0F, 0F);
		head.addChild(head6);
		head7 = new ModelRenderer(this, 64, 7);
		head7.addBox(0F, 16.5F - 18F, 2.8F - 1F, 1, 1, 1);
		head7.setRotationPoint(-0.5F, -5.5F, -2F);
		head7.setTextureSize(128, 128);
		head7.mirror = true;
		setRotation(head7, -0.6283185F, 0F, 0F);
		head.addChild(head7);

		body = new ModelRenderer(this, 100, 0);
		body.addBox(-3F, -1F, -4F, 6, 5, 8);
		body.setRotationPoint(0F, 18F, 4F);
		body.setTextureSize(128, 128);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		body2 = new ModelRenderer(this, 104, 13);
		body2.addBox(0F, 18F - 18F, 4F - 4F, 5, 1, 7);
		body2.setRotationPoint(-2.5F, 3.5F, -3.5F);
		body2.setTextureSize(128, 128);
		body2.mirror = true;
		setRotation(body2, 0F, 0F, 0F);
		body.addChild(body2);
		body3 = new ModelRenderer(this, 108, 21);
		body3.addBox(0F, 18F - 18F, 4F - 4F, 4, 1, 6);
		body3.setRotationPoint(-2F, 4F, -3F);
		body3.setTextureSize(128, 128);
		body3.mirror = true;
		setRotation(body3, 0F, 0F, 0F);
		body.addChild(body3);
		body4 = new ModelRenderer(this, 116, 28);
		body4.addBox(0F, 17F - 18F, 3F - 4F, 1, 6, 5);
		body4.setRotationPoint(-0.5F, -1F, 2F);
		body4.setTextureSize(128, 128);
		body4.mirror = true;
		setRotation(body4, 0.5585054F, 0F, 0F);
		body.addChild(body4);

		bill = new ModelRenderer(this, 68, 0);
		bill.addBox(0F, 0F, 0F, 3, 1, 1);
		bill.setRotationPoint(-1.5F, -2F, -4F);
		bill.setTextureSize(128, 128);
		bill.mirror = true;
		setRotation(bill, 0.1396263F, 0F, 0F);
		head.addChild(bill);
		bill2 = new ModelRenderer(this, 70, 2);
		bill2.addBox(0F, 0.3F, 0F, 2, 1, 1);
		bill2.setRotationPoint(-1F, -2F, -5F);
		bill2.setTextureSize(128, 128);
		bill2.mirror = true;
		setRotation(bill2, 0.3141593F, 0F, 0F);
		head.addChild(bill2);
		chin = new ModelRenderer(this, 48, 0);
		chin.addBox(0F, 0F, 0F, 2, 1, 1);
		chin.setRotationPoint(-1F, -1F, -4F);
		chin.setTextureSize(128, 128);
		chin.mirror = true;
		setRotation(chin, 0F, 0F, 0F);
		head.addChild(chin);
		chin2 = new ModelRenderer(this, 40, 0);
		chin2.addBox(0F, 0F, 0F, 2, 2, 2);
		chin2.setRotationPoint(-1F, 0F, -4F);
		chin2.setTextureSize(128, 128);
		chin2.mirror = true;
		setRotation(chin2, 0F, 0F, 0F);
		head.addChild(chin2);



		leftWing = new ModelRenderer(this, 90, 0);
		leftWing.addBox(0F, 0F, 0F, 1, 5, 4);
		leftWing.setRotationPoint(3F, 17F, 1F);
		leftWing.setTextureSize(128, 128);
		setRotation(leftWing, 0F, 0F, 0F);
		leftWing2 = new ModelRenderer(this, 94, 9);
		leftWing2.addBox(0F, 0F, 4F, 1, 4, 2);
		leftWing2.setRotationPoint(0F, 0F, 0F);
		leftWing2.setTextureSize(128, 128);
		setRotation(leftWing2, 0F, 0F, 0F);
		leftWing.addChild(leftWing2);
		leftWing3 = new ModelRenderer(this, 90, 9);
		leftWing3.addBox(0F, 1F, 6F, 1, 3, 1);
		leftWing3.setRotationPoint(0F, 0F, 0F);
		leftWing3.setTextureSize(128, 128);
		setRotation(leftWing3, 0F, 0F, 0F);
		leftWing.addChild(leftWing3);

		rightWing = new ModelRenderer(this, 90, 0);
		rightWing.addBox(-1F, 0F, 0F, 1, 5, 4);
		rightWing.setRotationPoint(-3F, 17F, 1F);
		rightWing.setTextureSize(128, 128);
		rightWing.mirror = true;
		setRotation(rightWing, 0F, 0F, 0F);
		rightWing2 = new ModelRenderer(this, 94, 9);
		rightWing2.addBox(-1F, 0F, 4F, 1, 4, 2);
		rightWing2.setRotationPoint(0F, 0F, 0F);
		rightWing2.setTextureSize(128, 128);
		rightWing2.mirror = true;
		setRotation(rightWing2, 0F, 0F, 0F);
		rightWing.addChild(rightWing2);
		rightWing3 = new ModelRenderer(this, 90, 9);
		rightWing3.addBox(-1F, 1F, 6F, 1, 3, 1);
		rightWing3.setRotationPoint(0F, 0F, 0F);
		rightWing3.setTextureSize(128, 128);
		rightWing3.mirror = true;
		setRotation(rightWing3, 0F, 0F, 0F);
		rightWing.addChild(rightWing3);

		rightLeg = new ModelRenderer(this, 54, 0);
		rightLeg.addBox(-1F, 0F, -2F, 2, 1, 3);
		rightLeg.setRotationPoint(-1.5F, 23F, 4F);
		rightLeg.setTextureSize(128, 128);
		rightLeg.mirror = true;
		setRotation(rightLeg, 0F, 0F, 0F);
		leftLeg = new ModelRenderer(this, 54, 0);
		leftLeg.addBox(-1F, 0F, -2F, 2, 1, 3);
		leftLeg.setRotationPoint(1.5F, 23F, 4F);
		leftLeg.setTextureSize(128, 128);
		leftLeg.mirror = true;
		setRotation(leftLeg, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		head.render(f5);
		body.render(f5);
		leftWing.render(f5);
		rightWing.render(f5);
		leftLeg.render(f5);
		rightLeg.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f1, float f2, float f3, float f4, float f5, float f6, Entity entity)
	{
		super.setRotationAngles(f1, f2, f3, f4, f5, f6, entity);
		this.head.rotateAngleX = f5 / (180F / (float)Math.PI);
		this.head.rotateAngleY = f4 / (180F / (float)Math.PI);
		this.rightLeg.rotateAngleX = MathHelper.cos(f1 * 0.6662F) * 1.4F * f2;
		this.leftLeg.rotateAngleX = MathHelper.cos(f1 * 0.6662F + (float)Math.PI) * 1.4F * f2;
		this.rightWing.rotateAngleZ = f3;
		this.leftWing.rotateAngleZ = -f3;

		this.head.offsetZ = -0.25F;
		this.body.offsetZ = -0.25F;
		this.rightWing.offsetZ = -0.25F;
		this.leftWing.offsetZ = -0.25F;
		this.rightLeg.offsetZ = -0.25F;
		this.leftLeg.offsetZ = -0.25F;
	}

}


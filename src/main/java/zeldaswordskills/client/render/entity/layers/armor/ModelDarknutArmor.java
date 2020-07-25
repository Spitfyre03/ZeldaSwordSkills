package zeldaswordskills.client.render.entity.layers.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

/**
 * @author TheRedMajora
 */
public class ModelDarknutArmor extends ModelBiped {

    public ModelRenderer foreheadPlate;
    public ModelRenderer leftPiece;
    public ModelRenderer leftCheek;
    public ModelRenderer rightPiece;
    public ModelRenderer rightCheek;
    public ModelRenderer nose;
    public ModelRenderer leftPanel;
    public ModelRenderer leftHornHolder;
    public ModelRenderer leftHorn;
    public ModelRenderer rightPanel;
    public ModelRenderer rightHornHolder;
    public ModelRenderer rightHorn;
    public ModelRenderer topPanel;
    public ModelRenderer backPanel;
    public ModelRenderer armorTop;
    public ModelRenderer armorBottom;
    public ModelRenderer leftBrace;
    public ModelRenderer rightBrace;

    public ModelDarknutArmor(float modelSize) {
        super(modelSize, 0, 64, 64);
        this.foreheadPlate = new ModelRenderer(this, 40, 40);
        this.foreheadPlate.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.foreheadPlate.addBox(-4.5F, -8.5F, -4.6F, 9, 2, 1);
        this.leftPiece = new ModelRenderer(this, 40, 36);
        this.leftPiece.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.leftPiece.addBox(3.5F, -6.5F, -4.6F, 1, 2, 1);
        this.leftCheek = new ModelRenderer(this, 32, 36);
        this.leftCheek.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.leftCheek.mirror = true;
        this.leftCheek.addBox(1.5F, -4.5F, -4.6F, 3, 5, 1);
        this.rightPiece = new ModelRenderer(this, 40, 36);
        this.rightPiece.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rightPiece.addBox(-4.5F, -6.5F, -4.6F, 1, 2, 1);
        this.rightCheek = new ModelRenderer(this, 32, 36);
        this.rightCheek.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rightCheek.addBox(-4.5F, -4.5F, -4.6F, 3, 5, 1);
        this.nose = new ModelRenderer(this, 56, 32);
        this.nose.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.nose.addBox(-1.0F, -7.0F, -5.4F, 2, 4, 1);
        this.setRotateAngle(nose, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
        this.leftPanel = new ModelRenderer(this, 44, 44);
        this.leftPanel.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.leftPanel.addBox(3.5F, -8.5F, -3.6F, 1, 9, 8);
        this.leftHornHolder = new ModelRenderer(this, 44, 32);
        this.leftHornHolder.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.leftHornHolder.addBox(3.0F, -6.0F, -1.5F, 3, 3, 3);
        this.leftHorn = new ModelRenderer(this, 32, 32);
        this.leftHorn.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.leftHorn.addBox(6.0F, -3.5F, -1.0F, 4, 2, 2);
        this.setRotateAngle(leftHorn, 0.0F, 0.0F, (float)Math.toRadians(-18.75));
        this.rightPanel = new ModelRenderer(this, 44, 44);
        this.rightPanel.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rightPanel.addBox(-4.5F, -8.5F, -3.6F, 1, 9, 8);
        this.rightHornHolder = new ModelRenderer(this, 44, 32);
        this.rightHornHolder.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rightHornHolder.addBox(-6.0F, -6.0F, -1.5F, 3, 3, 3);
        this.rightHorn = new ModelRenderer(this, 32, 32);
        this.rightHorn.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rightHorn.addBox(-10.0F, -3.5F, -1.0F, 4, 2, 2);
        this.setRotateAngle(rightHorn, 0.0F, 0.0F, (float)Math.toRadians(18.75));
        this.topPanel = new ModelRenderer(this, 22, 56);
        this.topPanel.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.topPanel.addBox(-3.5F, -8.5F, -3.6F, 7, 1, 7);
        this.backPanel = new ModelRenderer(this, 28, 46);
        this.backPanel.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.backPanel.addBox(-3.5F, -8.5F, 3.4F, 7, 9, 1);
        this.armorTop = new ModelRenderer(this, 0, 32);
        this.armorTop.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.armorTop.addBox(-5.0F, -0.2F, -3.0F, 10, 7, 6);
        this.armorBottom = new ModelRenderer(this, 0, 46);
        this.armorBottom.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.armorBottom.addBox(-4.5F, 6.8F, -2.5F, 9, 6, 5);
        this.leftBrace = new ModelRenderer(this, 0, 58);
        this.leftBrace.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.leftBrace.addBox(-1.0F, -3.0F, -2.5F, 6, 1, 5);
        this.setRotateAngle(leftBrace, 0.0F, 0.0F, (float)Math.toRadians(-2.0));
        this.rightBrace = new ModelRenderer(this, 0, 58);
        this.rightBrace.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rightBrace.addBox(-5.0F, -3.0F, -2.5F, 6, 1, 5);
        this.setRotateAngle(rightBrace, 0.0F, 0.0F, (float)Math.toRadians(2.0));

        this.bipedHead.addChild(this.foreheadPlate);
        this.bipedHead.addChild(this.leftPiece);
        this.bipedHead.addChild(this.leftCheek);
        this.bipedHead.addChild(this.rightPiece);
        this.bipedHead.addChild(this.rightCheek);
        this.bipedHead.addChild(this.nose);
        this.bipedHead.addChild(this.leftPanel);
        this.bipedHead.addChild(this.leftHornHolder);
        this.bipedHead.addChild(this.leftHorn);
        this.bipedHead.addChild(this.rightPanel);
        this.bipedHead.addChild(this.rightHornHolder);
        this.bipedHead.addChild(this.rightHorn);
        this.bipedHead.addChild(this.topPanel);
        this.bipedHead.addChild(this.backPanel);
        this.bipedBody.addChild(this.armorTop);
        this.bipedBody.addChild(this.armorBottom);
        this.bipedLeftArm.addChild(this.leftBrace);
        this.bipedRightArm.addChild(this.rightBrace);
    }

    private void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

package zeldaswordskills.client.render.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import zeldaswordskills.client.model.ModelCucco;
import zeldaswordskills.entity.passive.EntityCucco;
import zeldaswordskills.ref.ModInfo;

@SideOnly(Side.CLIENT)
public class RenderCucco extends RenderLiving<EntityCucco> {

	private static final ResourceLocation texture1 = new ResourceLocation(ModInfo.ID + ":" + "textures/entity/cucco.png");
	private static final ResourceLocation texture2 = new ResourceLocation(ModInfo.ID + ":" + "textures/entity/cucco_angry.png");
	//private static final ResourceLocation texture3 = new ResourceLocation(ZSSAddon.ModID + ":" + "textures/entity/cucco_golden.png");

	public RenderCucco(RenderManager renderManager) {
		super(renderManager, new ModelCucco(), 0.2F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCucco entity) {
		return (entity.getDataWatcher().getWatchableObjectByte(10) & 1) == 0 ? texture1 : texture2;
	}

	protected float handleRotationFloat(EntityCucco entity, float partialTicks) {
		// field_70888_h is wingRotationOld
		// field_70884_g is destPosOld
		float f1 = entity.field_70888_h + (entity.wingRotation - entity.field_70888_h) * partialTicks;
		float f2 = entity.field_70884_g + (entity.destPos - entity.field_70884_g) * partialTicks;
		return (MathHelper.sin(f1) + 1.0F) * f2;
	}

	@Override
	public void doRender(EntityCucco entity, double dx, double dy, double dz, float yaw, float partialTick) {
		GL11.glPushMatrix();
		this.renderCucco(entity, dx, dy, dz, yaw, partialTick);
		super.doRender(entity, dx, dy, dz, yaw, partialTick);
		GL11.glPopMatrix();
	}

	public void renderCucco(EntityCucco entity, double dx, double dy, double dz, float yaw, float partialTick) {
		if(entity.isRiding()) {
			GL11.glTranslatef(0, -1.1F, 0);
		}
	}

	public static class Factory implements IRenderFactory<EntityCucco> {
		@Override
		public Render<? super EntityCucco> createRenderFor(RenderManager manager) {
			return new RenderCucco(manager);
		}
	}
}
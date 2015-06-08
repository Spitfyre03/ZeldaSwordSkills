/**
    Copyright (C) <2015> <coolAlias>

    This file is part of coolAlias' Zelda Sword Skills Minecraft Mod; as such,
    you can redistribute it and/or modify it under the terms of the GNU
    General Public License as published by the Free Software Foundation,
    either version 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package zeldaswordskills.client.render.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import zeldaswordskills.client.model.ModelCube;
import zeldaswordskills.entity.projectile.EntityMagicSpell;

@SideOnly(Side.CLIENT)
public class RenderEntityMagicSpell extends Render
{
	private final ModelCube box1 = new ModelCube(4);
	private final ModelCube box2 = new ModelCube(4);

	public RenderEntityMagicSpell(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(Entity entity, double dx, double dy, double dz, float yaw, float partialTick) {
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		float scale = ((EntityMagicSpell) entity).getArea();
		float roll = ((float) entity.ticksExisted + partialTick) * 40;
		while (roll > 360) roll -= 360;
		GlStateManager.translate(dx, dy, dz);
		GlStateManager.scale(scale, scale, scale);
		GlStateManager.rotate(yaw, 0, 1, 0);
		GlStateManager.rotate(roll, 0.8F, 0F, -0.6F);
		bindEntityTexture(entity);
		Tessellator.getInstance().getWorldRenderer().setBrightness(0xf000f0);
		box1.render(entity);
		GlStateManager.rotate(45, 1, 0, 1);
		box2.render(entity);
		GlStateManager.disableRescaleNormal();
		GlStateManager.disableLighting();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ((EntityMagicSpell) entity).getType().getEntityTexture();
	}
}

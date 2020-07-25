/**
    Copyright (C) <2019> <coolAlias>

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

package zeldaswordskills.item;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zeldaswordskills.client.render.entity.layers.armor.ModelDarknutArmor;
import zeldaswordskills.creativetab.ZSSCreativeTabs;
import zeldaswordskills.ref.ModInfo;
import zeldaswordskills.util.StringUtils;

import java.util.List;

public class ItemArmorDarknut extends ItemModArmor {

	public ItemArmorDarknut(int renderIndex, int armorType) {
		super(ArmorMaterial.IRON, renderIndex, armorType);
		setCreativeTab(ZSSCreativeTabs.tabCombat);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return String.format("%s:textures/armor/darknut_armor_layer_%d.png", ModInfo.ID, (slot == 2 ? 2 : 1));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot, ModelBiped defaultModel) {
		ModelDarknutArmor armorModel;
		int type = ((ItemArmorDarknut)itemStack.getItem()).armorType;
		armorModel = type == 0 ? new ModelDarknutArmor(1.1f) : type == 1 ? new ModelDarknutArmor(1.0f) : new ModelDarknutArmor(0.1f);
		armorModel.setModelAttributes(defaultModel);
		return armorModel;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(StringUtils.translateKey("tooltip." + getUnlocalizedName().substring(5) + ".desc"));
	}
}

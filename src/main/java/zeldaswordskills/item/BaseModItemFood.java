/**
    Copyright (C) <2018> <coolAlias>

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

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zeldaswordskills.ref.ModInfo;

public class BaseModItemFood extends ItemFood implements IModItem {

	// TODO maybe extend from Item and rebuild food design
	// List of potion effects?
	// If list, safeguard against effects of same type
	public BaseModItemFood(int foodAmount, float saturation, boolean isWolfFood) {
		super(foodAmount, saturation, isWolfFood);
	}

	/**
	 * Returns "item.zss.unlocalized_name" for translation purposes
	 */
	@Override
	public String getUnlocalizedName() {
		return super.getUnlocalizedName().replaceFirst("item.", "item.zss.");
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName();
	}

	/**
	 * Default behavior returns NULL to not register any variants
	 */
	@Override
	public String[] getVariants() {
		return null;
	}

	/**
	 * Default implementation suggested by {@link IModItem#registerResources()}
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void registerResources() {
		String[] variants = getVariants();
		if (variants == null || variants.length < 1) {
			String name = getUnlocalizedName();
			variants = new String[]{ModInfo.ID + ":" + name.substring(name.lastIndexOf(".") + 1)};
		}
		for (int i = 0; i < variants.length; ++i) {
			ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(variants[i], "inventory"));
		}
	}
}

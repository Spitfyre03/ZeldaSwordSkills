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

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zeldaswordskills.api.item.IFairyUpgrade;
import zeldaswordskills.api.item.IItemNet;
import zeldaswordskills.block.tileentity.TileEntityDungeonCore;
import zeldaswordskills.creativetab.ZSSCreativeTabs;
import zeldaswordskills.ref.Sounds;
import zeldaswordskills.util.PlayerUtils;
import zeldaswordskills.util.WorldUtils;

public class ItemBugNet extends BaseModItem implements IFairyUpgrade, IItemNet {

	public ItemBugNet() {
		setMaxStackSize(1);
		setCreativeTab(ZSSCreativeTabs.tabTools);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;// Cool 3D model?
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldRotateAroundWhenRendering() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced) {
		String[] tooltips = StatCollector.translateToLocal("tooltip." + getUnlocalizedName().substring(5) + ".desc").split("\\\\n");
		for (String tooltip : tooltips) {
			list.add(tooltip);
		}
	}

	@Override
	public void handleFairyUpgrade(EntityItem item, EntityPlayer player, TileEntityDungeonCore core) {
		ItemStack stack = item.getEntityItem();
		if (stack != null && stack.getItem() == ZSSItems.bugNet) {
			if (core.consumeRupees(64)) {
				item.setDead();
				WorldUtils.spawnItemWithRandom(core.getWorld(), new ItemStack(ZSSItems.bigBugNet), core.getPos().getX(),
						core.getPos().getY() + 2, core.getPos().getZ());
				core.getWorld().playSoundEffect(core.getPos().getX() + 0.5D, core.getPos().getY() + 1,
						core.getPos().getZ() + 0.5D, Sounds.SECRET_MEDLEY, 1.0F, 1.0F);
			} else {
				core.getWorld().playSoundEffect(core.getPos().getX() + 0.5D, core.getPos().getY() + 1,
						core.getPos().getZ() + 0.5D, Sounds.FAIRY_LAUGH, 1.0F, 1.0F);
				PlayerUtils.sendTranslatedChat(player, "chat.zss.fairy.laugh.unworthy");
			}
		}
	}

	@Override
	public boolean hasFairyUpgrade(ItemStack stack) {
		return this == ZSSItems.bugNet && stack != null && !stack.isItemDamaged();
	}

	@Override
	public boolean onCapturedEntity(ItemStack stack, EntityPlayer player, Entity target) {
		return true;
	}

	@Override
	public NetStrength getNetStrength() {
		return NetStrength.NORMAL;
	}
}

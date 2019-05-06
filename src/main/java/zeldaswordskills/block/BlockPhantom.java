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

package zeldaswordskills.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import zeldaswordskills.api.capabilities.sandhours.ISandHoursHandler;
import zeldaswordskills.capabilities.sandhours.CapabilitySandHours;
import zeldaswordskills.creativetab.ZSSCreativeTabs;

public class BlockPhantom extends Block {

	public BlockPhantom() {
		super(Material.rock);
		setCreativeTab(ZSSCreativeTabs.tabBlocks);
		setStepSound(soundTypeStone);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity entity) {
		if (!world.isRemote && entity instanceof EntityLivingBase) {
			EntityLivingBase target = (EntityLivingBase)entity;
			ItemStack stack = target.getHeldItem();
			if (stack != null && stack.hasCapability(CapabilitySandHours.CAP, null)) {
				ISandHoursHandler handler = stack.getCapability(CapabilitySandHours.CAP, null);
				if (handler.removeAmount(2) == 0) {
					return;
				}
			}
			if (entity instanceof EntityPlayer && !((EntityPlayer)target).capabilities.isCreativeMode) {
				target.addPotionEffect(new PotionEffect(Potion.confusion.id, 160, 1));
			}
			target.attackEntityFrom(DamageSource.magic, 2);
		}
	}
}

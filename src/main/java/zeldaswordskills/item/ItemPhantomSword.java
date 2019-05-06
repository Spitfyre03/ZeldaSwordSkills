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

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zeldaswordskills.api.capabilities.sandhours.ISandHoursHandler;
import zeldaswordskills.capabilities.sandhours.CapabilitySandHours;
import zeldaswordskills.network.PacketDispatcher;
import zeldaswordskills.network.client.capabilities.sandhours.SandHoursUpdatePacket;

public class ItemPhantomSword extends ItemZeldaSword {

	public ItemPhantomSword(ToolMaterial material, float bonusDamage) {
		super(material, bonusDamage);
		setMasterSword();
		setNoItemOnBreak();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advancedTooltips) {
		super.addInformation(stack, player, list, advancedTooltips);
		ISandHoursHandler handler = stack.getCapability(CapabilitySandHours.CAP, null);
		if (handler != null) {
			int max = handler.getMaxSands();
			int current = handler.getCurrentSands();
			EnumChatFormatting color = current < (max / 3) ? EnumChatFormatting.RED
					: current < (0.9 * max) ? EnumChatFormatting.YELLOW : EnumChatFormatting.WHITE;
			String sands = EnumChatFormatting.GOLD + "Sands: " + color + current + EnumChatFormatting.WHITE + "/" + max;
			list.add(sands);
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (entity instanceof EntityPlayer) {
			if (stack.hasCapability(CapabilitySandHours.CAP, null)) {
				ISandHoursHandler handler = stack.getCapability(CapabilitySandHours.CAP, null);
				if (!world.isRemote) {
					if (handler.addAmount(1) == 0) {
						EntityPlayerMP player = (EntityPlayerMP)entity;
						PacketDispatcher.sendTo(new SandHoursUpdatePacket(handler, player.currentWindowId, itemSlot), player);
					}
				}
			}
		}
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		ICapabilityProvider provider = CapabilitySandHours.createProvider();
		provider.getCapability(CapabilitySandHours.CAP, null).setMaxSands(600);
		return provider;
	}
}

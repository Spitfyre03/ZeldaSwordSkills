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

package zeldaswordskills.capabilities.sandhours;

import java.util.concurrent.Callable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import zeldaswordskills.api.capabilities.sandhours.ISandHoursHandler;
import zeldaswordskills.capabilities.SerializableCapabilityProvider;

public class CapabilitySandHours {

	@CapabilityInject(ISandHoursHandler.class)
	public static final Capability<ISandHoursHandler> CAP = null;

	private static final EnumFacing DEFAULT_FACING = null;

	public static void register() {
		CapabilityManager.INSTANCE.register(ISandHoursHandler.class,
			new Capability.IStorage<ISandHoursHandler>() {
				@Override
				public NBTBase writeNBT(Capability<ISandHoursHandler> capability, ISandHoursHandler instance, EnumFacing side) {
					NBTTagCompound tag = new NBTTagCompound();
					tag.setInteger("max", instance.getMaxSands());
					tag.setInteger("sands", instance.getCurrentSands());
					return tag;
				}
		
				@Override
				public void readNBT(Capability<ISandHoursHandler> capability, ISandHoursHandler instance, EnumFacing side, NBTBase nbt) {
					NBTTagCompound tag = (NBTTagCompound)nbt;
					instance.setMaxSands(tag.getInteger("max"));
					instance.setCurrentSands(tag.getInteger("sands"));
				}
			},
			// TODO rewrite as ::new
			new Callable<SandHoursHandler>() {
				public SandHoursHandler call() {
					return new SandHoursHandler();
				}
			}
		);
	}

	public static ICapabilitySerializable<NBTBase> createProvider() {
		return new SerializableCapabilityProvider<ISandHoursHandler>(CAP, DEFAULT_FACING, new SandHoursHandler());
	}
}

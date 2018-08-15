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

package zeldaswordskills.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

/**
 * A general, serializable ICapabilityProvider to be used for internal ZSS capabilities.</br>
 * 
 * For storage, modders have the option of providing a custom IStorage instance. If one is not provided,
 * the Capability's default storage will be used to save and load the handler.
 *
 * @param <H> the type of the handler for the Capability that this exposes
 *
 * @author Choonster
 * @author Spitfyre03
 */
public class SerializableCapabilityProvider<H> implements ICapabilitySerializable<NBTBase> {

	private final Capability<H> capability;
	private final EnumFacing facing;
	private final H instance;
	private final IStorage<H> storage;

	/**
	 * Creates a provider with the provided Capability, facing, handler, and the Capability's default storage
	 * implementation.
	 */
	public SerializableCapabilityProvider(Capability<H> capability, EnumFacing facing, H instance) {
		this(capability, facing, instance, capability.getStorage());
	}

	/**
	 * Creates a provider with the provided Capability, facing, handler, and custom storage instance
	 */
	public SerializableCapabilityProvider(Capability<H> capability, EnumFacing facing, H instance, IStorage<H> storage) {
		this.capability = capability;
		this.facing = facing;
		this.instance = instance;
		this.storage = storage;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == this.capability;
	}

	/**
	 * @param <T> the type of the Capability in question
	 *
	 * @return a handler instance for the Capability
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (hasCapability(capability, facing)) {
			return (T)instance;
		}
		return null;
	}

	@Override
	public NBTBase serializeNBT() {
		return this.storage.writeNBT(this.capability, this.instance, this.facing);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		this.storage.readNBT(this.capability, this.instance, this.facing, nbt);
	}

	public final Capability<H> getCapability() {
		return this.capability;
	}

	public EnumFacing getFacing() {
		return this.facing;
	}

	public final H getInstance() {
		return this.instance;
	}

	public final IStorage<H> getStorage() {
		return this.storage;
	}
}

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

package zeldaswordskills.network.client.capabilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import zeldaswordskills.network.AbstractMessage.AbstractClientMessage;

public abstract class ContainerUpdateCapabilitiesPacket<T extends ContainerUpdateCapabilitiesPacket<T, H>, H> extends AbstractClientMessage<T> {

	private final Capability<H> capability;
	private int containerID;
	private final Map<Integer, NBTTagCompound> dataMap = new HashMap<>();

	public ContainerUpdateCapabilitiesPacket(Capability<H> cap) {
		this.capability = cap;
	}

	public ContainerUpdateCapabilitiesPacket(Capability<H> cap, List<ItemStack> stackList, int windowID) {
		this.capability = cap;
		this.containerID = windowID;
		for (int index = 0; index < stackList.size(); index++) {
			ItemStack stack = stackList.get(index);
			if (stack != null && stack.hasCapability(this.capability, null)) {
				NBTTagCompound tag = new NBTTagCompound();
				this.writeCapToNBT(tag, stack.getCapability(this.capability, null));
				this.dataMap.put(index, tag);
			}
		}
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.containerID = buffer.readInt();
		int setSize = buffer.readInt();
		for (int index = 0; index < setSize; index++) {
			int slotIndex = buffer.readInt();
			NBTTagCompound tag = buffer.readNBTTagCompoundFromBuffer();
			this.dataMap.put(slotIndex, tag);
		}
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(this.containerID);
		Set<Map.Entry<Integer, NBTTagCompound>> entries = this.dataMap.entrySet();
		buffer.writeInt(entries.size());
		for (Map.Entry<Integer, NBTTagCompound> entry : entries) {
			buffer.writeInt(entry.getKey());
			buffer.writeNBTTagCompoundToBuffer(entry.getValue());
		}
	}

	@Override
	protected void process(EntityPlayer player, Side side) {
		Container container;
		if (this.containerID == 0) {
			container = player.inventoryContainer;
		} else if (this.containerID == player.openContainer.windowId) {
			container = player.openContainer;
		} else {
			return;
		}
		for (Map.Entry<Integer, NBTTagCompound> entry : this.dataMap.entrySet()) {
			int index = entry.getKey();
			NBTTagCompound newData = entry.getValue();
			ItemStack oldStack = container.getSlot(index).getStack();
			if (oldStack != null && oldStack.hasCapability(this.capability, null)) {
				ItemStack newStack = oldStack.copy();
				H newHandler = newStack.getCapability(this.capability, null);
				this.writeNBTToCap(newHandler, newData);
				container.putStackInSlot(index, newStack);
			}
		}
	}

	protected abstract void writeCapToNBT(NBTTagCompound tag, H handler);

	protected abstract void writeNBTToCap(H newHandler, NBTTagCompound newData);

}

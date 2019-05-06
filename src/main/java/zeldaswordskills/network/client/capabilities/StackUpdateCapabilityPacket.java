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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import zeldaswordskills.network.AbstractMessage.AbstractClientMessage;

public abstract class StackUpdateCapabilityPacket<M extends StackUpdateCapabilityPacket<M, H>, H> extends AbstractClientMessage<M> {

	private final Capability<H> capability;
	private NBTTagCompound data;
	private int containerID;
	private int slotIndex;

	public StackUpdateCapabilityPacket(Capability<H> cap) {
		this.capability = cap;
	}

	public StackUpdateCapabilityPacket(Capability<H> cap, H handler, int windowID, int itemSlot) {
		this.capability = cap;
		NBTTagCompound tag = new NBTTagCompound();
		this.writeCapToNBT(tag, handler);
		this.data = tag;
		this.containerID = windowID;
		this.slotIndex = itemSlot;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.containerID = buffer.readInt();
		this.slotIndex = buffer.readInt();
		this.data = buffer.readNBTTagCompoundFromBuffer();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(this.containerID);
		buffer.writeInt(this.slotIndex);
		buffer.writeNBTTagCompoundToBuffer(this.data);
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
		ItemStack oldStack = container.getSlot(this.slotIndex).getStack();
		if (oldStack != null && oldStack.hasCapability(this.capability, null)) {
			ItemStack newStack = oldStack.copy();
			H newData = newStack.getCapability(this.capability, null);
			this.writeNBTToCap(newData, this.data);
			player.inventory.setInventorySlotContents(this.slotIndex, newStack);
		}
	}

	protected abstract void writeCapToNBT(NBTTagCompound tag, H handler);

	protected abstract void writeNBTToCap(H newHandler, NBTTagCompound newData);

}

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
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import zeldaswordskills.network.AbstractMessage.AbstractClientMessage;

public abstract class StackUpdateCapabilityPacket<M extends StackUpdateCapabilityPacket<M, H>, H> extends AbstractClientMessage<M> {

	private final Capability<H> capability;
	private H data;
	private int slotIndex;

	public StackUpdateCapabilityPacket(Capability<H> cap) {
		this.capability = cap;
	}

	public StackUpdateCapabilityPacket(Capability<H> cap, H handler, int itemSlot) {
		this.capability = cap;
		this.data = handler;
		this.slotIndex = itemSlot;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.slotIndex = buffer.readInt();
		this.data = this.readCapData(buffer);
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(this.slotIndex);
		this.writeCapData(buffer, this.data);
	}

	@Override
	protected void process(EntityPlayer player, Side side) {
		ItemStack oldStack = player.inventory.getStackInSlot(this.slotIndex);
		if (oldStack != null && oldStack.hasCapability(this.capability, null)) {
			ItemStack newStack = oldStack.copy();
			H newData = newStack.getCapability(this.capability, null);
			this.applyCapData(newData, this.data);
			player.inventory.setInventorySlotContents(this.slotIndex, newStack);
		}
	}

	protected abstract H readCapData(PacketBuffer buffer);

	protected abstract void writeCapData(PacketBuffer buffer, H data);

	protected abstract void applyCapData(H targetHandler, H messageHandler);
}

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

package zeldaswordskills.network.client.capabilities.sandhours;

import net.minecraft.network.PacketBuffer;
import zeldaswordskills.api.capabilities.sandhours.ISandHoursHandler;
import zeldaswordskills.capabilities.sandhours.CapabilitySandHours;
import zeldaswordskills.capabilities.sandhours.SandHoursHandler;
import zeldaswordskills.network.client.capabilities.StackUpdateCapabilityPacket;

public class SandHoursUpdatePacket extends StackUpdateCapabilityPacket<SandHoursUpdatePacket, ISandHoursHandler> {

	public SandHoursUpdatePacket() {
		super(CapabilitySandHours.CAP);
	}

	public SandHoursUpdatePacket(ISandHoursHandler handler, int windowID, int itemSlot) {
		super(CapabilitySandHours.CAP, handler, itemSlot);
	}

	@Override
	protected ISandHoursHandler readCapData(PacketBuffer buffer) {
		ISandHoursHandler handler = new SandHoursHandler();
		handler.setMaxSands(buffer.readInt());
		handler.setCurrentSands(buffer.readInt());
		return handler;
	}

	@Override
	protected void writeCapData(PacketBuffer buffer, ISandHoursHandler data) {
		buffer.writeInt(data.getMaxSands());
		buffer.writeInt(data.getCurrentSands());
	}

	@Override
	protected void applyCapData(ISandHoursHandler targetHandler, ISandHoursHandler messageHandler) {
		targetHandler.setMaxSands(messageHandler.getMaxSands());
		targetHandler.setCurrentSands(messageHandler.getCurrentSands());
	}

}

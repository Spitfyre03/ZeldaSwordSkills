package zeldaswordskills.capabilities;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import zeldaswordskills.network.PacketDispatcher;
import zeldaswordskills.network.client.capabilities.ContainerUpdateCapabilitiesPacket;
import zeldaswordskills.network.client.capabilities.StackUpdateCapabilityPacket;

public abstract class AbstractCapabilityContainerListener<H> implements ICrafting {

	private final Capability<H> capability;

	private final EntityPlayerMP player;

	public AbstractCapabilityContainerListener(Capability<H> cap, EntityPlayerMP target) {
		this.capability = cap;
		this.player = target;
	}

	@Override
	public void updateCraftingInventory(Container container, List<ItemStack> stackList) {
		ContainerUpdateCapabilitiesPacket<?, H> message = this.getContainerUpdateMessage(stackList, container.windowId);
		if (message != null) {
			PacketDispatcher.sendTo(message, this.player);
		}
	}

	@Override
	public void sendSlotContents(Container container, int slotIndex, ItemStack stack) {
		if (stack != null && stack.hasCapability(this.capability, null)) {
			StackUpdateCapabilityPacket<?, H> message = this.getStackUpdateMessage(stack.getCapability(this.capability, null), container.windowId, slotIndex);
			if (message != null) {
				PacketDispatcher.sendTo(message, this.player);
			}
		}
	}

	@Override
	public void sendProgressBarUpdate(Container container, int varToUpdate, int newValue) {}

	@Override
	public void sendAllWindowProperties(Container p1, IInventory p2) {}

	public abstract ContainerUpdateCapabilitiesPacket<?, H> getContainerUpdateMessage(List<ItemStack> stackList, int containerID);

	public abstract StackUpdateCapabilityPacket<?, H> getStackUpdateMessage(H handler, int windowID, int slotIndex);
}

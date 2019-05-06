package zeldaswordskills.capabilities;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraftforge.event.entity.player.PlayerOpenContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import zeldaswordskills.ZSSMain;

public enum ContainerCapabilityListenersManager {

	INSTANCE;

	private final Set<Function<EntityPlayerMP, AbstractCapabilityContainerListener<?>>> listenerFactories = new HashSet<>();

	public void registerFactory(Function<EntityPlayerMP, AbstractCapabilityContainerListener<?>> factory) {
		if (listenerFactories.contains(factory)) {
			ZSSMain.logger.error("Attempted to register duplicate listener factory");
			return;
		} else {
			listenerFactories.add(factory);
		}
	}

	private void attachListeners(EntityPlayerMP player, Container container) {
		for (Function<EntityPlayerMP, AbstractCapabilityContainerListener<?>> function : listenerFactories) {
			container.onCraftGuiOpened(function.apply(player));
		}
	}

	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
		if (event.player instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) event.player;
			attachListeners(player, player.inventoryContainer);
		}
	}

	@SubscribeEvent
	public void onPlayerCloned(PlayerEvent.Clone event) {
		if (event.entityPlayer instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) event.entityPlayer;
			attachListeners(player, player.inventoryContainer);
		}
	}

	@SubscribeEvent
	public void onContainerOpened(PlayerOpenContainerEvent event) {
		if (event.entityPlayer instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) event.entityPlayer;
			attachListeners(player, player.openContainer);
		}
	}
}

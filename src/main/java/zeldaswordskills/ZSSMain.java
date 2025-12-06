/**
    Copyright (C) <2017> <coolAlias>

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

package zeldaswordskills;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import zeldaswordskills.ref.ModInfo;

/**
 * 
 * A mod that adds Zelda-like sword skills to Minecraft. Players will start with only the
 * ability to 'lock-on' to targets and perform combos; they must learn the other skills
 * throughout the game by getting rare 'skill orb' drops from different kinds of mobs.
 * 
 * Other Zelda-like features such as bombs, secret dungeons, heart pieces, and more are also
 * included.
 *
 */
@Mod(ModInfo.ID)
public class ZSSMain {

	public static final Logger LOGGER = LogManager.getLogger(ModInfo.ID);

	public ZSSMain() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
	}

	private void onCommonSetup(FMLCommonSetupEvent event) {
		LOGGER.info("Capturing Common Setup event in ZSS");
	}
	/*
	@Mod.Instance(ModInfo.ID)
	public static ZSSMain instance;

	@SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.SERVER_PROXY)
	public static CommonProxy proxy;


	/** Helper class for registering custom tiles with Antique Atlas mod if loaded
	public static AntiqueAtlasHelper atlasHelper = new AntiqueAtlasHelper();
	/** Whether Antique Atlas mod is loaded
	public static boolean isAtlasEnabled;
	/** Whether Battlegear2 mod is loaded
	public static boolean isBG2Enabled;
	*/

	/*
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Config.preInit(event);
		isAtlasEnabled = Loader.isModLoaded("antiqueatlas");
		isBG2Enabled = Loader.isModLoaded("battlegear2");
		CapabilityRegistry.regsiter();
		ZSSBlocks.preInit();
		ZSSItems.preInit();
		ZSSEntities.preInit();
		ZSSAchievements.preInit();
		proxy.preInit();
		PacketDispatcher.preInit();
	}

	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.init();
		ZSSItems.init();
		MinecraftForge.EVENT_BUS.register(instance);
		MinecraftForge.EVENT_BUS.register(new ZSSCombatEvents());
		MinecraftForge.EVENT_BUS.register(new ZSSEntityEvents());
		MinecraftForge.EVENT_BUS.register(new ZSSItemEvents());
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		MinecraftForge.EVENT_BUS.register(new ZSSWorldGenEvent());
		if (Config.areBossDungeonsEnabled()) {
			ZSSBossDungeonGen dungeonGen = new ZSSBossDungeonGen();
			MinecraftForge.EVENT_BUS.register(dungeonGen);
			MinecraftForge.TERRAIN_GEN_BUS.register(dungeonGen);
		}
		if (Config.getGossipStoneRate() > 0) {
			MinecraftForge.EVENT_BUS.register(WorldGenGossipStones.INSTANCE);
		}
		MinecraftForge.EVENT_BUS.register(WorldGenJars.INSTANCE);
		FMLInterModComms.sendRuntimeMessage(ModInfo.ID, "VersionChecker", "addVersionCheck", ModInfo.VERSION_LIST);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		Config.postInit();
		if (isBG2Enabled) {
			ItemHeroBow.registerBG2();
			MinecraftForge.EVENT_BUS.register(new BattlegearEvents());
		}
		DungeonLootLists.init();
	}

	@Mod.EventHandler
	public void onServerStarting(FMLServerStartingEvent event) {
		ZSSItems.onServerStarting();
		ZSSCommands.registerCommands(event);
	}

	@Mod.EventHandler
	public void processMessages(FMLInterModComms.IMCEvent event) {
		for (final FMLInterModComms.IMCMessage msg : event.getMessages()) {
			WeaponRegistry.INSTANCE.processMessage(msg);
		}
	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event){
		if(event.modID.equals(ModInfo.ID)){
			Config.init();
			//Has no effect yet
			Config.postPropInit();
		}
	}
	*/
}

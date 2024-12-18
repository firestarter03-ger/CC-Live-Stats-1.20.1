package net.firestarter03.cclivestats;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CC_Live_Stats implements ModInitializer {
	public static final String MOD_ID = "cclivestats";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {


		LOGGER.info("Mod wird gestartet!");
	}
}
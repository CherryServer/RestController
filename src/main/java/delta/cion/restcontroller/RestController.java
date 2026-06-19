package delta.cion.restcontroller;

import delta.cion.cherry.api.plugin.Plugin;
import delta.cion.restcontroller.server.ServerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class RestController extends Plugin {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestController.class);
	private static final String PROPERTY_NAME = "webapi.properties";

	private static ServerController serverController;

	private static Plugin instance;

	public static Plugin getInstance() {
		return instance;
	}

	public RestController(String id, String name, String version) {
		super(id, name, version);
	}

	@Override
	public void onEnable() {
		instance = this;
		saveFromResources(PROPERTY_NAME);
		ServerController.setServerPort(getPort());
		LOGGER.info("Trying to start Cherry WebAPI");
		serverController = new ServerController();
	}

	private static int getPort() {
		try (InputStreamReader reader = new FileReader(PROPERTY_NAME)) {
			Properties properties = new Properties();
			properties.load(reader);

			var raw = properties.getOrDefault("port", 0);
			return Integer.parseInt(raw.toString());
		} catch (IOException e) {
			return 0;
		}
	}

	@Override
	public void onDisable() {
		serverController.close();
	}
}

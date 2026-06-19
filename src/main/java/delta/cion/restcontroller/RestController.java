package delta.cion.restcontroller;

import delta.cion.cherry.api.plugin.Plugin;
import delta.cion.restcontroller.server.SecretKey;
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

	public static String getPropertyName() {
		return PROPERTY_NAME;
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
		serverController = new ServerController(getSecret());
	}

	private static Object getFromConfig(String key) {
		try (InputStreamReader reader = new FileReader(PROPERTY_NAME)) {
			Properties properties = new Properties();
			properties.load(reader);

			return properties.getOrDefault(key, null);
		} catch (IOException e) {
			return e;
		}
	}

	private static int getPort() {
		Object data = getFromConfig("port");
		if (data instanceof IOException) {
			LOGGER.error("Cannot load port from {}! Using random free port.", PROPERTY_NAME, (IOException) data);
			return 0;
		}
		if (data == null) return 0;
		return Integer.parseInt(data.toString());
	}

	private static String getSecret() {
		var data = getFromConfig("secret-key");
		if (data instanceof IOException) {
			LOGGER.error("Cannot load secret-key from {}!", PROPERTY_NAME, (IOException) data);
			return SecretKey.checkKey(data.toString());
		}
		return SecretKey.checkKey(data.toString());
	}

	public static String getSecretKey() {
		if (serverController == null) return null;
		return serverController.getSecretKey();
	}

	@Override
	public void onDisable() {
		serverController.close();
	}
}

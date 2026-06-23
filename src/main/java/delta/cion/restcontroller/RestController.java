package delta.cion.restcontroller;

import delta.cion.tokyo.api.plugin.Plugin;
import delta.cion.restcontroller.server.SecretKey;
import delta.cion.restcontroller.server.ServerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class RestController extends Plugin {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestController.class);

	private static final String PROPERTY_NAME = "webapi.properties";
	private static File CONFIG_FILE;

	private static ServerController serverController;

	private static Plugin instance;

	public static Plugin getInstance() {
		return instance;
	}

	public static File getPropertyFile() {
		return CONFIG_FILE;
	}

	public RestController(String id, String name, String version) {
		super(id, name, version);
	}

	@Override
	public void onEnable() {
		instance = this;
		CONFIG_FILE = new File(getInstance().getPluginDirectory(), PROPERTY_NAME);

		if (!(new File(getPluginDirectory(), PROPERTY_NAME).exists()))
			saveFromResources(getPluginDirectory(), PROPERTY_NAME);
		ServerController.setServerPort(getPort());
		LOGGER.info("Trying to start Tokyo WebAPI");
		serverController = new ServerController(getSecret());
	}

	private static Object getFromConfig(String key) {
		try (InputStreamReader reader = new FileReader(CONFIG_FILE)) {
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
			return SecretKey.checkKey(null);
		}
		String key = data != null ? data.toString() : null;
		return SecretKey.checkKey(key);
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

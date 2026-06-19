package delta.cion.restcontroller.server;

import delta.cion.restcontroller.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;
import java.util.UUID;

public class SecretKey {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecretKey.class);

	private static String SECRET_KEY = null;

	public static String checkKey(String key) {
		if (key != null && !key.equalsIgnoreCase("REPLACE_IT")) return key;

		UUID uuid = UUID.randomUUID();
		try (InputStreamReader reader = new FileReader(RestController.getPropertyName())) {
			Properties properties = new Properties();
			properties.load(reader);

			properties.setProperty("secret-key", uuid.toString());

			Writer writer = new FileWriter(RestController.getPropertyName());

			properties.store(writer, "Changed secret-key to random UUID");

			SECRET_KEY = uuid.toString();
			return SECRET_KEY;
		} catch (IOException e) {
			LOGGER.error("secret-key null or default and cannot set secret-key to random uuid.", e);
			return null;
		}
	}

	public static boolean validate(String key) {
		return SECRET_KEY.equalsIgnoreCase(key);
	}

}

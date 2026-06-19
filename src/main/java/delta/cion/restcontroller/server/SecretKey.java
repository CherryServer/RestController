package delta.cion.restcontroller.server;

import com.sun.net.httpserver.HttpExchange;
import delta.cion.restcontroller.RestController;
import delta.cion.restcontroller.local_api.response.R401;
import delta.cion.restcontroller.local_api.request.Response;
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

	public static boolean validate(HttpExchange exchange) {
		String header = exchange.getRequestHeaders().getFirst("Authorization");
		if (header == null || !header.startsWith("Bearer "))
			Response.sendResponse(exchange, 401, new R401().response());

		assert header != null;
		String secret = header.substring(7);

		if (SECRET_KEY.equalsIgnoreCase(secret)) return true;
		Response.sendResponse(exchange, 401, new R401().response());
		return false;
	}

}

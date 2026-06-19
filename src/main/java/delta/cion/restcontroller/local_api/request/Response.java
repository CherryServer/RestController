package delta.cion.restcontroller.local_api.request;

import com.sun.net.httpserver.HttpExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Response {

	private static final Logger LOGGER = LoggerFactory.getLogger(Response.class);

	public static void sendResponse(HttpExchange exchange, int statusCode, String jsonResponse) {
		try {
			exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
			byte[] bytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
			exchange.sendResponseHeaders(statusCode, bytes.length);
			try (OutputStream os = exchange.getResponseBody()) {
				os.write(bytes);
			}
		} catch (IOException exception) {
			LOGGER.error("Cannot send response", exception);
		}
	}

}

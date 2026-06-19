package delta.cion.restcontroller.local_api;

import com.sun.net.httpserver.HttpExchange;
import delta.cion.restcontroller.local_api.request.Request;
import delta.cion.restcontroller.local_api.response.ErrorResponse;
import delta.cion.restcontroller.local_api.request.Response;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class CheckRequest {

	/**
	 * Check request type
	 * @param request request
	 * @param exchange exchange
	 * @param response if it f4cked request
	 * @return boolean lol
	 */
	public static boolean checkType(Request request, HttpExchange exchange, ErrorResponse response) {
		if (request.request.equalsIgnoreCase(exchange.getRequestMethod())) {
			Response.sendResponse(exchange, response.status(), response.response());
			return false;
		}
		return true;
	}

	/**
	 * Validate and get data
	 * @param exchange request
	 * @return json data from request
	 */
	public static JSONObject validateJson(HttpExchange exchange) {
		try (InputStreamReader reader = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8)) {
			return new JSONObject(new JSONTokener(reader));
		} catch (Exception e) {
			Response.sendResponse(exchange, 400, ErrorResponse.BASE.formatted("Invalid Json"));
			return null;
		}
	}

}

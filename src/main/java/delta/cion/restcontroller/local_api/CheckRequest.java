package delta.cion.restcontroller.local_api;

import com.sun.net.httpserver.HttpExchange;
import delta.cion.restcontroller.local_api.response.ErrorResponse;
import delta.cion.restcontroller.local_api.response.Response;

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

}

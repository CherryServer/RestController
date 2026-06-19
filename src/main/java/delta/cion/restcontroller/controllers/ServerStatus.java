package delta.cion.restcontroller.controllers;

import com.sun.net.httpserver.HttpExchange;
import delta.cion.restcontroller.local_api.CheckRequest;
import delta.cion.restcontroller.local_api.Controller;
import delta.cion.restcontroller.local_api.request.Response;
import delta.cion.restcontroller.local_api.response.R401;
import delta.cion.restcontroller.server.SecretKey;
import org.json.JSONObject;

import static delta.cion.restcontroller.local_api.request.Request.GET;

public class ServerStatus implements Controller {

	@Override
	public String path() {
		return "/server/status";
	}

	@Override
	public void handle(HttpExchange exchange) {
		if (CheckRequest.checkType(GET, exchange, new R401())) return;
		if (!SecretKey.validate(exchange)) return;

		JSONObject data = CheckRequest.validateJson(exchange);

		if (data == null) return;
		Response.sendResponse(exchange, 200, data.toString());
	}
}

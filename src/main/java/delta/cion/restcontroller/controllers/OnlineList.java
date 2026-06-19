package delta.cion.restcontroller.controllers;

import com.sun.net.httpserver.HttpExchange;
import delta.cion.restcontroller.local_api.CheckRequest;
import delta.cion.restcontroller.local_api.Controller;
import delta.cion.restcontroller.local_api.request.Response;
import delta.cion.restcontroller.local_api.response.R401;
import delta.cion.restcontroller.server.SecretKey;
import net.minestom.server.MinecraftServer;
import org.json.JSONArray;
import org.json.JSONObject;

import static delta.cion.restcontroller.local_api.request.Request.GET;

public class OnlineList implements Controller {

	@Override
	public String path() {
		return "/server/online";
	}

	@Override
	public void handle(HttpExchange exchange) {
		if (CheckRequest.checkType(GET, exchange, new R401())) return;
		if (!SecretKey.validate(exchange)) return;

		JSONObject response = new JSONObject();
		response.put("timestamp", System.currentTimeMillis());

		JSONArray players = new JSONArray();
		MinecraftServer.getConnectionManager().getOnlinePlayers().forEach(player -> {
			players.put("%s: %s".formatted(player.getUsername(), player.getUuid()));
		});

		response.put("online", players);
		Response.sendResponse(exchange, 200, response.toString());
	}
}

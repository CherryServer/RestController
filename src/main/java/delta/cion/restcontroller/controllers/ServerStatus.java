package delta.cion.restcontroller.controllers;

import com.sun.net.httpserver.HttpExchange;
import delta.cion.restcontroller.local_api.Controller;

public class ServerStatus implements Controller {

	@Override
	public String path() {
		return "/server/status";
	}

	@Override
	public void handle(HttpExchange exchange) {

	}
}

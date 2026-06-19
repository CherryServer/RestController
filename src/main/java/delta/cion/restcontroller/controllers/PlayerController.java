package delta.cion.restcontroller.controllers;

import com.sun.net.httpserver.HttpExchange;
import delta.cion.restcontroller.local_api.Controller;

public class PlayerController implements Controller {

	@Override
	public String path() {
		return "/player";
	}

	@Override
	public void handle(HttpExchange exchange) {

	}
}

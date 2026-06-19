package delta.cion.restcontroller.controllers;

import com.sun.net.httpserver.HttpExchange;
import delta.cion.restcontroller.local_api.Controller;

public class OnlineList implements Controller {

	@Override
	public String path() {
		return "/server/online";
	}

	@Override
	public void handle(HttpExchange exchange) {

	}
}

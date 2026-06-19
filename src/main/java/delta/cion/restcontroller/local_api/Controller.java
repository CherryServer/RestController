package delta.cion.restcontroller.local_api;

import com.sun.net.httpserver.HttpHandler;

public interface Controller extends HttpHandler {

	default String path() { return null; };

}

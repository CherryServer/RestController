package delta.cion.restcontroller.server;

import com.sun.net.httpserver.HttpServer;
import delta.cion.restcontroller.RestController;
import delta.cion.restcontroller.controllers.OnlineList;
import delta.cion.restcontroller.controllers.PlayerController;
import delta.cion.restcontroller.controllers.ServerStatus;
import delta.cion.restcontroller.local_api.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;

public class ServerController implements AutoCloseable {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServerController.class);

	private static HttpServer HTTP_SERVER;
	private final String SECRET_KEY;

	private static int SERVER_PORT = 0;

	private static final ArrayList<Controller> CONTROLLERS = new ArrayList<>();

	static {
		CONTROLLERS.add(new OnlineList());
		CONTROLLERS.add(new ServerStatus());
		CONTROLLERS.add(new PlayerController());
	}

	public ServerController(String secretKey) {
		this.SECRET_KEY = secretKey;

		if (HTTP_SERVER == null) createServer(SERVER_PORT);
		else this.close();
	}

	private void createServer(int port) {
		try {
			HTTP_SERVER = HttpServer.create(new InetSocketAddress(SERVER_PORT), 0);
			registerControllers();

			HTTP_SERVER.setExecutor(null);
			HTTP_SERVER.start();

			LOGGER.info("WebAPI created on: {}", port);
		} catch (IOException e) {
			LOGGER.error("Cannot create WebAPI. Disabling plugin.", e);
			RestController.getInstance().onDisable();
		}
	}

	public String getSecretKey() {
		return SECRET_KEY;
	}

	private void registerControllers() {
		CONTROLLERS.forEach(controller -> {
			HTTP_SERVER.createContext(controller.path(), controller);});
	}

	public static void setServerPort(int port) {
		SERVER_PORT = port;
	}

	@Override
	public void close() {
		if (HTTP_SERVER == null) return;
		HTTP_SERVER.stop(0);
		LOGGER.info("WebAPI closed");
	}
}

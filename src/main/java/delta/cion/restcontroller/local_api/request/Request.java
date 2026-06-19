package delta.cion.restcontroller.local_api.request;

public enum Request {

	POST("POST"),
	GET("GET");

	public final String request;

	Request(String request) {
		this.request = request;
	}

}

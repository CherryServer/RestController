package delta.cion.restcontroller.local_api.response;

public interface ErrorResponse {

	String BASE = "{ \"error\": \"%s\" }";

	default int status() { return 505; }

	default String response() { return String.format(BASE, "Method Not Allowed"); }

}

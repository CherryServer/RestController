package delta.cion.restcontroller.local_api.response;

public class R404 implements ErrorResponse {

	@Override
	public int status() {
		return 404;
	}

	@Override
	public String response() {
		return BASE.formatted("Not Found");
	}
}

package delta.cion.restcontroller.local_api.response;

public class R401 implements ErrorResponse {

	@Override
	public int status() {
		return 401;
	}

	@Override
	public String response() {
		return BASE.formatted("Unauthorized");
	}
}

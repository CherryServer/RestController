package delta.cion.restcontroller.local_api.response;

public class R418 implements ErrorResponse {

	@Override
	public int status() {
		return 418;
	}

	@Override
	public String response() {
		return BASE.formatted("I'm a teapot");
	}
}

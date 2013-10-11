package trains.app;

public class KiwiRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -1978617350983491160L;

	public KiwiRuntimeException(String message) {
		super(message);
	}

	public KiwiRuntimeException(Exception e) {
		super(e);
	}

}

package trains.app;

public class PathNotFoundException extends RuntimeException {

	public PathNotFoundException(String message){
		super(message);
	}
	public PathNotFoundException(Exception e){
		super(e);
	}
}

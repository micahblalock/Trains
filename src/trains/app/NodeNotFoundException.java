package trains.app;

public class NodeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7287698787387006784L;


	public NodeNotFoundException(String message){
		super(message);
	}


	public NodeNotFoundException(Exception e){
		super(e);
	}

}

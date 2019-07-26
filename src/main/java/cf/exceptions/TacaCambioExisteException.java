package cf.exceptions;

public class TacaCambioExisteException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public TacaCambioExisteException(String msg) {
		super(msg);
	}

}

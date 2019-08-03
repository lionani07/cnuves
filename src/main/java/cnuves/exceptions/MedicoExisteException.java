package cnuves.exceptions;

public class MedicoExisteException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public MedicoExisteException(String msg) {
		super(msg);
	}

}

package cnuves.exceptions;

public class AgendaNaoExisteException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public AgendaNaoExisteException(String msg) {
		super(msg);
	}

}

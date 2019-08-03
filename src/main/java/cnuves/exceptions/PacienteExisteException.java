package cnuves.exceptions;

public class PacienteExisteException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public PacienteExisteException(String msg) {
		super(msg);
	}

}

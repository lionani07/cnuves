package cf.exceptions;

public class EmpresaExisteException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public EmpresaExisteException(String msg) {
		super(msg);
	}

}

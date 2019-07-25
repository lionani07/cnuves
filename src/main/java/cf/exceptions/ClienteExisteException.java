package cf.exceptions;

public class ClienteExisteException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ClienteExisteException(String msg) {
		super(msg);
	}
	
}

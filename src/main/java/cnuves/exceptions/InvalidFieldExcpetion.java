package cnuves.exceptions;

public class InvalidFieldExcpetion extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private String field;
	private String msg;
	
	public InvalidFieldExcpetion(String field, String msg) {
		super(msg);
		this.field = field;		
	}
	
	public String getField() {
		return field;
	}
	
	public String getMsg() {
		return msg;
	}

}

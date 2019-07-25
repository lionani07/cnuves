package cf.exceptions;

public class MoedaExisteException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private String field;
	private String msg;
	
	public MoedaExisteException(String field, String msg) {		
		this.field = field;
		this.msg = msg;
	}
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}

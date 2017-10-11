package a03.errors;

public class HTKError extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2448083609069118311L;
	
	public HTKError() {
		super("HTK failed to record any sound");
	}
	
}

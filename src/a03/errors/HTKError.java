package a03.errors;

public class HTKError extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2448083609069118311L;
	
	/**
	 * The error to be thrown if HTK does not pick up any recorded noise.
	 */
	public HTKError() {
		super("HTK failed to record any sound");
	}
	
}

package compiler;

public class SymbolAlreadyDefinedException extends Exception {
	
	private static final long serialVersionUID = -6843253251623535936L;

	public SymbolAlreadyDefinedException() {
	}

	public SymbolAlreadyDefinedException(String message) {
		super(message);
	}

	public SymbolAlreadyDefinedException(Throwable cause) {
		super(cause);
	}

	public SymbolAlreadyDefinedException(String message, Throwable cause) {
		super(message, cause);
	}
}

package compiler;

public class UnknownSymbolException extends Exception {

	private static final long serialVersionUID = 1092404602922214929L;

	public UnknownSymbolException() {
	}

	public UnknownSymbolException(String message) {
		super(message);
	}

	public UnknownSymbolException(Throwable cause) {
		super(cause);
	}

	public UnknownSymbolException(String message, Throwable cause) {
		super(message, cause);
	}
}

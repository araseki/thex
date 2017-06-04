package ara.thex;

/**
 *
 */
public class ThexException extends RuntimeException {

	/**
	 *
	 */
	public ThexException() {
		super();
	}

	/**
	 * @param message message
	 * @param cause cause
	 * @param enableSuppression e
	 * @param writableStackTrace e
	 */
	public ThexException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message message
	 * @param cause cause
	 */
	public ThexException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message message
	 */
	public ThexException(String... message) {
		super(concat(message));
	}

	private static String concat(String... messages) {
		final StringBuilder ret = new StringBuilder();
		for (String message : messages) {
			ret.append(message);
		}
		return ret.toString();
	}

	/**
	 * @param cause cause
	 */
	public ThexException(Throwable cause) {
		super(cause);
	}

}

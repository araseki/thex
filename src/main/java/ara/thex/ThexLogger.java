package ara.thex;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ロガー<br>
 * ThymeleafがSLF4Jを使っているのでそれに合わせることにした。<br>
 * アダプタもついでに初めてのLog4j2にしてみる。
 */
public final class ThexLogger {
	private static final Logger APP = LoggerFactory.getLogger("thex.app");
	private static final Logger ERR = LoggerFactory.getLogger("thex.err");

	private ThexLogger() {
	}

	/**
	 * INFO
	 * @param words words
	 */
	public static void info(String... words) {
		APP.info(concat(words));
	}

	/**
	 * WARN
	 * @param words words
	 */
	public static void warn(String... words) {
		ERR.warn(concat(words));
	}

	/**
	 * DEBUG
	 * @param words words
	 */
	public static void debug(String... words) {
		APP.debug(concat(words));
	}

	/**
	 * ERROR
	 * @param words words
	 */
	public static void error(String... words) {
		ERR.error(concat(words));
	}

	/**
	 * ERROR
	 * @param t t
	 */
	public static void error(Throwable t) {
		final StringWriter writer = new StringWriter();
		t.printStackTrace(new PrintWriter(writer));
		ERR.error(writer.toString());
	}

	private static String concat(String... words) {
		final StringBuilder ret = new StringBuilder();
		for (String word : words) {
			ret.append(word);
		}
		return ret.toString();
	}
}

package ara.thex;

import javax.servlet.http.HttpServletRequest;

import org.thymeleaf.util.StringUtils;

/**
 * テンプレートファイルの置き場所
 */
public class TemplateResource {
	private String base;
	private String prefix;

	/**
	 * コンストラクタ
	 * @param base baseディレクトリ
	 */
	public TemplateResource(String base) {
		this.base = base;
		fixPrefix();
	}

	/**
	 * @return prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * テンプレートの絶対パスを決める
	 * @param req req
	 * @return path
	 */
	public String resolve(HttpServletRequest req) {
		final String contextPath = req.getServletPath();
		final String uri = req.getRequestURI();
		String name = uri.substring(contextPath.length());
		if (name.startsWith("/")) {
			name = name.substring(1);
		}
		if (name.endsWith("/") || StringUtils.isEmptyOrWhitespace(name)) {
			name = "index.html";
		}
		return name;
	}

	private void fixPrefix() {
		if ("JVM-PARAM".equals(base)) {
			final String spec = System.getProperty("thex.base");
			if (!StringUtils.isEmptyOrWhitespace(spec)) {
				prefix = adjustBase(spec);
			} else {
				throw new ThexException("specify 'thex.base' by JVM parameter(-D): top folder of templates");
			}
		} else {
			prefix = adjustBase(base);
		}
	}

	private String adjustBase(String base) {
		if (base.endsWith("/")) {
			return base;
		} else {
			return base + "/";
		}
	}
}

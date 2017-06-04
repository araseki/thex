package ara.thex;

import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * Thymeleafを使ったHTML生成<br>
 * テンプレートの場所はサーブレットの外にある任意のフォルダ。<br>
 * これはJVMオプション「thex.base」で指定されていなくてはいけない。<br>
 * また、主にデバッグ用として/WEB-INF/templatesも探す。
 */
public class TemplateProcessor {
	private TemplateEngine engine;
	private String encoding;
	private String prefix;

	/**
	 * コンストラクタ
	 * @param prefix prefix
	 * @param encoding encoding
	 */
	public TemplateProcessor(String prefix, String encoding, ServletContext servletContext) {
		this.encoding = encoding;
		this.prefix = prefix;
		engine = new TemplateEngine();
		engine.setTemplateResolver(createFileResolver());
		engine.addTemplateResolver(createServletResolver(servletContext));
	}

	private ITemplateResolver createFileResolver() {
		final FileTemplateResolver resolver = new FileTemplateResolver();
		resolver.setCacheable(false);
		resolver.setCharacterEncoding(encoding);
		resolver.setTemplateMode(TemplateMode.HTML);
		resolver.setPrefix(prefix);
		resolver.setOrder(Integer.valueOf(1));
		return resolver;
	}

	private ITemplateResolver createServletResolver(ServletContext servletContext) {
		final ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(servletContext);
		resolver.setCacheable(false);
		resolver.setCharacterEncoding(encoding);
		resolver.setTemplateMode(TemplateMode.HTML);
		resolver.setPrefix("/WEB-INF/templates/");
		resolver.setOrder(Integer.valueOf(2));
		return resolver;
	}

	/**
	 * テンプレート処理
	 * @param templateName name
	 * @param req req
	 * @param res res
	 * @throws IOException e
	 */
	public void process(String templateName, HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("text/html; charset=" + encoding);
		res.setHeader("Pragma", "private, no-store, no-cache, must-revalidate");
		res.setHeader("Cache-Control", "no-cache");
		res.setDateHeader("Expires", 0);
		final WebContext ctx = new WebContext(req, res, req.getServletContext());
		ctx.setVariable("request", req);
		ctx.setVariable("forwardUrl", req.getAttribute("javax.servlet.forward.request_uri"));
		engine.process(templateName, ctx, new OutputStreamWriter(res.getOutputStream(), encoding));
	}
}

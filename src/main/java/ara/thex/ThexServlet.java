package ara.thex;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.exceptions.TemplateEngineException;

/**
 * サーブレット
 *
 */
public class ThexServlet extends HttpServlet {
	private TemplateProcessor processor;
	private OthersProcessor other;
	private TemplateResource template;

	/* (非 Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	/* (非 Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			final String name = template.resolve(req);
			if (name.endsWith(".html")) {
				processor.process(name, req, resp);
			} else {
				other.process(name, req, resp);
			}
			if ("404.html".equals(name) || "500.html".equals(name)) {
				return;
			} else {
				ThexLogger.info("(200) ", req.getRequestURI());
			}
		} catch (TemplateEngineException e) {
			if (e.getCause() instanceof FileNotFoundException) {
				ThexLogger.error("(404) ", req.getRequestURI());
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			} else {
				ThexLogger.error("(500) ", req.getRequestURI());
				ThexLogger.error(e);
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}
	}

	/* (非 Javadoc)
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		template = new TemplateResource(config.getInitParameter("base"));
		ThexLogger.info("template-base:", template.getPrefix());
		processor = new TemplateProcessor(template.getPrefix(),
		        config.getInitParameter("characterEncoding"), config.getServletContext());
		other = new OthersProcessor(template.getPrefix());
	}
}

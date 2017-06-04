/**
 *
 */
package ara.thex;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * html以外のリクエスト処理<br>
 * cssやjsは文字セットをHTTPヘッダに出力すべきだろうか？
 */
public class OthersProcessor {
	private String base;

	/**
	 * コンストラクタ
	 * @param base base
	 */
	public OthersProcessor(String base) {
		this.base = base;
	}

	public void process(String path, HttpServletRequest req, HttpServletResponse res) throws IOException {
		final File input = new File(resolvePath(path));
		if (!input.exists()) {
			response404(res, path);
		} else {
			Files.copy(input.toPath(), res.getOutputStream());
		}
	}

	private void response404(HttpServletResponse res, String path) throws IOException {
		res.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	private String resolvePath(String path) {
		return base + path;
	}
}

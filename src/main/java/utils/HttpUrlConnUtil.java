package utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * java程序连接互联网工具类
 * <p>
 * 使用HttpURLConnection
 * 
 * @author wangxinyu
 *
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class HttpUrlConnUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpUrlConnUtil.class);
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36";

	/**
	 *
	 * @param strUrl
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param method
	 *            请求方法 GET POST
	 * @return 网络请求字符串
	 * @throws Exception
	 * @description http://www.oschina.net/question/565065_81309?fromerr=7bNcYdxi
	 */
	public static String send(String strUrl, Map<String, Object> params, String method) throws Exception {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String rs = null;
		try {
			// GET请求,有参数就放在url中 (默认为GET方式)
			if (method == null & "GET".equals(method) & !params.isEmpty()) {
				strUrl = strUrl + "?" + urlencode(params);
			}
			logger.info("strUrl:" + strUrl);

			// 设置连接
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();

			/* 请求行 */
			if (method == null || method.equals("GET")) {
			} else {
				conn.setRequestMethod("POST");
				conn.setDoOutput(true); // 将参数要放在http正文内(post必需)
			}
			// 置是否从httpUrlConnection读入，默认情况下是true;
			conn.setDoInput(true);

			/* 请求头 */
			// 设置 HttpURLConnection的接收的文件类型
			conn.setRequestProperty("Accept",
					"image/gif, image/jpeg, image/pjpeg, image/pjpeg, "
							+ "application/x-shockwave-flash, application/xaml+xml, "
							+ "application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, "
							+ "application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			// 设置 HttpURLConnection的接收语音
			conn.setRequestProperty("Accept-Language", Locale.getDefault().toString());
			// 指定请求uri的源资源地址(告诉服务器你是从哪儿来的)
			conn.setRequestProperty("Referer", "你大爷");
			// 设置 HttpURLConnection的字符编码
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			// 持续连接
			conn.setRequestProperty("Connection", "Keep-Alive");
			// 设定传送的内容类型 MIME
			conn.setRequestProperty("Content-type", "text/html");
			// 浏览器类型
			conn.setRequestProperty("User-agent", userAgent);
			conn.setUseCaches(false); // 不使用缓存

			// 超时设置，防止网络异常的情况下，可能会导致程序僵死而不继续往下执行
			conn.setConnectTimeout(DEF_CONN_TIMEOUT); // 连接被阻塞时长(连接主机超时),防卡死
			conn.setReadTimeout(DEF_READ_TIMEOUT); // 从主机读取数据超时

			conn.setInstanceFollowRedirects(false); // 系统不自动处理重定向

			// 请求头参数配置必须要在connect之前完成，
			conn.connect(); // 建立连接

			/* 请求正文 */
			// post方式 发送请求参数
			if (params != null && method.equals("POST")) {
				try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {
					String pendParamsString = urlencode(params);
					logger.info("POST请求的参数:" + pendParamsString);
					out.writeBytes(pendParamsString);
				}
			}

			// 获取响应
			InputStream is = conn.getInputStream();
			// int state = conn.getResponseCode(); // 获取响应状态码

			reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));

			// 获取响应体
			StringBuffer sb = new StringBuffer();
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sb.append(strRead);
			}
			rs = sb.toString();
			printResponseHeader(conn);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rs;
	}

	/** 打印出响应头 */
	private static void printResponseHeader(HttpURLConnection http) throws UnsupportedEncodingException {
		Map<String, String> header = getHttpResponseHeader(http);
		for (Map.Entry<String, String> entry : header.entrySet()) {
			String key = entry.getKey() != null ? entry.getKey() + ":" : "";
			logger.info(key + entry.getValue());
		}
	}

	private static Map<String, String> getHttpResponseHeader(HttpURLConnection http)
			throws UnsupportedEncodingException {
		Map<String, String> header = new LinkedHashMap<String, String>();
		for (int i = 0;; i++) {
			String mine = http.getHeaderField(i);
			if (mine == null)
				break;
			header.put(http.getHeaderFieldKey(i), mine);
		}
		return header;
	}

	/** 将map型转为请求参数型 */
	private static String urlencode(Map<String, ?> data) {
		StringBuilder sb = new StringBuilder();
		// map.entrySet() 可遍历K和V
		// 见:http://blog.csdn.net/wangxy799/article/details/49991023
		for (Map.Entry<String, ?> i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		sb.deleteCharAt(sb.length() - 1); // 删除最后多余的一个&
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		// params.put("wd", "爱奇艺");
		// String string = HttpUrlConnUtil.send("https://www.baidu.com/s",params,"GET");
		System.out.println("params.size:" + params.size() + "; params.isEmpty:" + params.isEmpty());
		String string = HttpUrlConnUtil.send("https://www.hao123.com/", params, "GET");

		System.out.println(string);
	}

}

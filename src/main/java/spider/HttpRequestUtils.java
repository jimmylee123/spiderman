package spider;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

@SuppressWarnings("deprecation")
public class HttpRequestUtils {
	private static Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class); // 日志记录

	/**
	 * post请求
	 * 
	 * @param url
	 *            url地址
	 * @param jsonParam
	 *            参数
	 * @return
	 */
	public static JSONObject port(String url, JSONObject jsonParam) {
		// post请求返回结果
		HttpClient httpClient = new HttpClient();
		JSONObject jsonResult = null;

		try {
			PostMethod postMethod = new PostMethod(url);

			if (null != jsonParam) {
				// 解决中文乱码问题
				// 设置请求头Authorization
				// postMethod.setRequestHeader("Authorization", "Basic " +
				// authorization);
				// 设置请求头 Content-Type
				postMethod.setRequestHeader("Content-Type", "application/json");
				postMethod.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

				RequestEntity requestEntity = new StringRequestEntity(jsonParam.toString()); // 请求体
				postMethod.setRequestEntity(requestEntity);
			}

			int statusCode = httpClient.executeMethod(postMethod);
			url = URLDecoder.decode(url, "UTF-8");
			/** 请求发送成功，并得到响应 **/
			if (statusCode == 200) {
				try {
					/** 读取服务器返回过来的json字符串数据 **/
					/** 把json字符串转换成json对象 **/
					jsonResult = JSONObject.fromObject(postMethod.getResponseBodyAsString());
				} catch (Exception e) {
					logger.error("post请求提交失败:" + url, e);
				}
			}
		} catch (IOException e) {
			logger.error("post请求提交失败:" + url, e);
		}
		return jsonResult;
	}
	
	

	/**
	 * 发送get请求
	 * 
	 * @param url
	 *            路径
	 * @return
	 */
	public static JSONObject get(String url) {
		// get请求返回结果
		JSONObject jsonResult = null;
		try {
			HttpClient client = new HttpClient();
			// 发送get请求
			GetMethod method = new GetMethod(url);
			int statusCode = client.executeMethod(method);

			/** 请求发送成功，并得到响应 **/
			if (statusCode == HttpStatus.SC_OK) {
				/** 读取服务器返回过来的json字符串数据 **/
				/** 把json字符串转换成json对象 **/
				String result = method.getResponseBodyAsString();
				
			            String line = "";
			            File f = new File("C:\\wjp\\test5.txt");
			            if(f.exists()){
			            	f.createNewFile();
			            }
			            
			            BufferedWriter bw =  new BufferedWriter(new FileWriter(f));
			            	bw.write(result);
			            	System.out.println(result);
			            bw.close();
//				jsonResult = JSONObject.fromObject(method.getResponseBodyAsString());
//				url = URLDecoder.decode(url, "UTF-8");
			} else {
				logger.error("get请求提交失败:" + url);
			}
		} catch (IOException e) {
			logger.error("get请求提交失败:" + url, e);
		}
		return jsonResult;
	}

	public static String web(String url) {
		HttpURLConnection conn = null;
		try {
			URL realUrl = new URL(url);
			conn = (HttpURLConnection) realUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.setUseCaches(false);
			conn.setReadTimeout(8000);
			conn.setConnectTimeout(8000);
			conn.setInstanceFollowRedirects(false);
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");
			int code = conn.getResponseCode();
			if (code == 200) {
				InputStream is = conn.getInputStream();
				BufferedReader in = new BufferedReader(new InputStreamReader(is));
				StringBuffer buffer = new StringBuffer();
				char[] line = new char[1024];
				int ret = 0;
				while ((ret = in.read(line)) > 0) {
					buffer.append(new String(line, 0, ret));
				}

				return buffer.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		String content = web("https://www.ph.com.cn/index.shtml");
		
		get("https://fe-api.zhaopin.com/c/i/sou?start=120&pageSize=60&cityId=489&workExperience=-1&education=-1&companyType=-1&employmentType=-1&jobWelfareTag=-1&kw=java&kt=3&lastUrlQuery=%7B%22p%22:3,%22pageSize%22:%2260%22,%22jl%22:%22489%22,%22kw%22:%22java%22,%22kt%22:%223%22%7D");
		
		
		Connection.Response response = null;
		/*try {
			response = Jsoup.connect(content).postDataCharset("UTF-8").execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		Document doc = Jsoup.parse(content);
//		doc.children();
//		 Element element = doc.body();
		 System.out.println(doc.html());
	

	}

	public static void getUrl(String urls) throws Exception {
		String url = urls;
		print("Fetching %s...", url);

		Document doc = Jsoup.connect(url).get();
		Elements links = doc.select("a[href]");
		Elements media = doc.select("[src]");
		Elements imports = doc.select("link[href]");

		print("\nMedia: (%d)", media.size());
		for (Element src : media) {
			if (src.tagName().equals("img"))
				print(" * %s: <%s> %sx%s (%s)", src.tagName(), src.attr("abs:src"), src.attr("width"),
						src.attr("height"), trim(src.attr("alt"), 20));
			else
				print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
		}

		print("\nImports: (%d)", imports.size());
		for (Element link : imports) {
			print(" * %s <%s> (%s)", link.tagName(), link.attr("abs:href"), link.attr("rel"));
		}

		print("\nLinks: (%d)", links.size());
		for (Element link : links) {
			print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
		}
	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}
	
	
	
	

}
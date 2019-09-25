package spider;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.filefilter.RegexFileFilter;

public class TestConn {

	public static String urlStr = "";
	
	public static void main(String[] args) {
		
		
		test2();
	}

	private static void test3() {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(urlStr);
		try {
			method.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			method.setRequestHeader("Content-Type", "application/json");
			client.executeMethod(method);
			RequestEntity entity = new StringRequestEntity("jsonParam.toString()");
			client.executeMethod(method);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void test2(){
		Pattern p = Pattern.compile("da?");
		String str = "daaaa";
		String str1 = "d";
		Matcher match = p.matcher(str1);
		System.out.println(match.matches());
	}

	private static void test1() {
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("get");
			conn.setReadTimeout(8000);
			conn.setReadTimeout(8000);
			conn.setUseCaches(false);
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");
		
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

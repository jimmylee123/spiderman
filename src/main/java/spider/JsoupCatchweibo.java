package spider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class JsoupCatchweibo {

	public static void main(String[] args) {
		Map<String,String> map = new HashMap<String,String>();
		try {
			Response resp = Jsoup.connect("http://weibo.com/u/6072715169").cookies(map).method(Method.GET).execute();
			String s = resp.body();
			
			System.out.println(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

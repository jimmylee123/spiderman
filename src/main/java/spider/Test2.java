package spider;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test2 {

	public static void main(String[] args) {
		String oldLinkHost = ""; //host
		  
	    Pattern p = Pattern.compile("(https?://)?[^/\\s]*"); //比如：http://www.zifangsky.cn
	    Matcher m = p.matcher("https://www.ph.com.cn/index.shtml");
	    if (m.find()) {
	      oldLinkHost = m.group();
	    }
	    
	    System.out.println(oldLinkHost);
//	    test();
	}
	
	public static void test(){
		String oldLinkHost = "www.ph.com.cn";
		Map<String,Boolean> oldMap = new HashMap<String,Boolean>();
        String line = "<a id='ADS-P10012006' href='https://www.10100000.com/daikuancms/wenti/1425454233530.shtml' target='_blank' title='常见问题' otype='button' otitle='常见问题'>常见问题</a>";
		 Pattern pattern = Pattern
	                .compile("<a.*?href=[\"']?((https?://)?/?[^\"']+)[\"']?.*?>(.+)</a>");
	            Matcher matcher = null;
	              matcher = pattern.matcher(line);
	              boolean result = matcher.matches();
	              System.out.println(result);
	              
	              matcher = pattern.matcher(line);
	              if (matcher.find()) {
	                String newLink = matcher.group(1).trim(); // 链接
	                // String title = matcher.group(3).trim(); //标题
	                // 判断获取到的链接是否以http开头
	                if (!newLink.startsWith("http")) {
	                  if (newLink.startsWith("/"))
	                    newLink = "dsd" + newLink;
	                  else
	                    newLink = "dsss" + "/" + newLink;
	                }
	              }
	              
	             
	              
	}
}

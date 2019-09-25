package spider;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

@SuppressWarnings("deprecation")
public class HttpRequestUtils1 {
	private static Logger logger = LoggerFactory.getLogger(HttpRequestUtils1.class); // 日志记录

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
				jsonResult = JSONObject.fromObject(method.getResponseBodyAsString());
				url = URLDecoder.decode(url, "UTF-8");
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

	

	private void getContent(String link) {
		String content = web(link);
		if(content != null){
		Document doc = Jsoup.parse(content);
		 Element element = doc.body();
		 String text = element.text();
//		 System.out.println(doc.html());
		 String filePath = "C:/pingan/ljs/";
		 String id = String.valueOf(System.currentTimeMillis());
		 String path = filePath + id + ".txt";
		 WriteStringToFile2(path,text);
		}
	}

	
	
	
	 public static void WriteStringToFile2(String filePath,String text) {
	        try {
//	        	System.out.println(text);
	            FileWriter fw = new FileWriter(filePath, true);
	            BufferedWriter bw = new BufferedWriter(fw);
//	            bw.append("在已有的基础上添加字符串");
	            bw.write(text);// 往已有的文件上添加字符串
//	            bw.write("def\r\n ");
//	            bw.write("hijk ");
	            bw.close();
	            fw.close();
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	 
	 
	 
	 public void myPrint(String baseUrl) {
		    Map<String, Boolean> oldMap = new LinkedHashMap<String, Boolean>(); // 存储链接-是否被遍历
		                                      // 键值对
		    String oldLinkHost = ""; //host
		  
		    Pattern p = Pattern.compile("(https?://)?[^/\\s]*"); //比如：http://www.zifangsky.cn
		    Matcher m = p.matcher(baseUrl);
		    if (m.find()) {
		      oldLinkHost = m.group();
		    }
		  
		    oldMap.put(baseUrl, false);
		    oldMap = crawlLinks(oldLinkHost, oldMap);
		    for (Map.Entry<String, Boolean> mapping : oldMap.entrySet()) {
		      System.out.println("链接：" + mapping.getKey());
		  
		    }
		  
		  }
		  
		  /**
		   * 抓取一个网站所有可以抓取的网页链接，在思路上使用了广度优先算法
		   * 对未遍历过的新链接不断发起GET请求，一直到遍历完整个集合都没能发现新的链接
		   * 则表示不能发现新的链接了，任务结束
		   * @param oldLinkHost 域名，如：http://www.zifangsky.cn
		   * @param oldMap 待遍历的链接集合
		   * 
		   * @return 返回所有抓取到的链接集合
		   * */
	 	private Map<String, Boolean> crawlLinks(String oldLinkHost,
		      Map<String, Boolean> oldMap) {
		    Map<String, Boolean> newMap = new LinkedHashMap<String, Boolean>();
		    String oldLink = "";
		  
		    for (Map.Entry<String, Boolean> mapping : oldMap.entrySet()) {
		      System.out.println("link:" + mapping.getKey() + "--------check:"
		          + mapping.getValue());
		      // 如果没有被遍历过
		      if (!mapping.getValue()) {
		        oldLink = mapping.getKey();
		        
		        getContent(oldLink);
		        // 发起GET请求
		        try {
		          URL url = new URL(oldLink);
		          HttpURLConnection connection = (HttpURLConnection) url
		              .openConnection();
		          connection.setRequestMethod("GET");
		          connection.setConnectTimeout(2000);
		          connection.setReadTimeout(2000);
		  
		          
		          if (connection.getResponseCode() == 200) {
		            InputStream inputStream = connection.getInputStream();
		            BufferedReader reader = new BufferedReader(
		                new InputStreamReader(inputStream, "UTF-8"));
		            String line = "";
		            Pattern pattern = Pattern
		                .compile("<a.*?href=[\"']?((https?://)?/?[^\"']+)[\"']?.*?>(.+)</a>");
		            Matcher matcher = null;
		            while ((line = reader.readLine()) != null) {
		              matcher = pattern.matcher(line);
		              if (matcher.find()) {
		                String newLink = matcher.group(1).trim(); // 链接
		                // String title = matcher.group(3).trim(); //标题
		                // 判断获取到的链接是否以http开头
		                if (!newLink.startsWith("http")) {
		                  if (newLink.startsWith("/"))
		                    newLink = oldLinkHost + newLink;
		                  else
		                    newLink = oldLinkHost + "/" + newLink;
		                }
		                //去除链接末尾的 /
		                if(newLink.endsWith("/"))
		                  newLink = newLink.substring(0, newLink.length() - 1);
		                //去重，并且丢弃其他网站的链接
		                if (!oldMap.containsKey(newLink)
		                    && !newMap.containsKey(newLink)
		                    && (newLink.startsWith(oldLinkHost) || newLink.startsWith("https://promo.lu.com") || newLink.startsWith("https://www.lup2p.com")))  {
		                  // System.out.println("temp2: " + newLink);
		                  newMap.put(newLink, false);
		                }
		              }
		            }
		          }
		        } catch (MalformedURLException e) {
		          e.printStackTrace();
		        } catch (IOException e) {
		          e.printStackTrace();
		        }
		  
		        try {
		          Thread.sleep(1000);
		        } catch (InterruptedException e) {
		          e.printStackTrace();
		        }
		        oldMap.replace(oldLink, false, true);
		      }
		    }
		    //有新链接，继续遍历
		    if (!newMap.isEmpty()) {
		      oldMap.putAll(newMap);
		      oldMap.putAll(crawlLinks(oldLinkHost, oldMap)); //由于Map的特性，不会导致出现重复的键值对
		    }
		    return oldMap;
		  }
	 
		  public static void main(String[] args)  {
				new HttpRequestUtils1().myPrint("https://www.ph.com.cn/index.shtml?WT.mc_id=CXX-BDPP-PHPC-001");
//				new HttpRequestUtils1().myPrint("https://list.lu.com/list/all?minMoney=&maxMoney=&minDays=0&maxDays=90&minRate=&maxRate=&mode=&tradingMode=&isOverdueTransfer=&isCx=&currentPage=1&orderCondition=&isShared=&canRealized=&productCategoryEnum=&notHasBuyFeeRate=&riskLevel=");
			}

}
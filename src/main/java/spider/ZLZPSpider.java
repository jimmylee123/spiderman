package spider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.python.antlr.PythonParser.continue_stmt_return;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ZLZPSpider {
	private static Logger logger = LoggerFactory.getLogger(ZLZPSpider.class); // 日志记录

	public static void main(String[] args) {
		for(int m=60;;m+=60){
		 File f = new File("C:\\lihaixiang\\zlzp\\company\\zlzp"+m/60+".txt");
      	try {
      		if(!f.exists()){
				f.createNewFile();
      		}
      		BufferedWriter bw =  new BufferedWriter(new FileWriter(f));
			JSONObject resultObject =  get("https://fe-api.zhaopin.com/c/i/sou?start="+m+"&pageSize=60&cityId=489&workExperience=-1&education=-1&companyType=-1&employmentType=-1&jobWelfareTag=-1&kw=java&kt=3&lastUrlQuery=%7B%22p%22:3,%22pageSize%22:%2260%22,%22jl%22:%22489%22,%22kw%22:%22java%22,%22kt%22:%223%22%7D");
			int code = resultObject.getInt("code");
			
					if(code == 200){
						JSONObject jsonData = resultObject.getJSONObject("data");
						JSONArray resultsArray = jsonData.getJSONArray("results");
						for(int i = 0;i<resultsArray.size();i++){
							JSONObject dataResult =  (JSONObject) resultsArray.get(i);
							String positionURL = dataResult.getString("positionURL");
							String message = getJAVAMessage(positionURL,bw);
							System.out.println(positionURL);
						}
					}
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
         
         	
		
	}
	
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
	
	public static String getJAVAMessage(String url,BufferedWriter bw){
		String message = null;
		String gwzz = "岗位+";
		String rzyq = "职位+";
		String yq = "要求+";
		String s= "[1-9]{1,}[\\.|\\、|\\，|\\）|\\．|\\)]";
		
		String s1 = "\\s*";
		Pattern pattern = Pattern.compile(s);
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
			           
			            	System.out.println(result);
			            Document doc = Jsoup.parse(result);// 解析HTML字符串返回一个Document实现
			    		Elements jobMessageEles = doc.getElementsByClass("tab-inner-cont");
			    		//遍历elements得到所有p
			    		for (Element element : jobMessageEles) {
			    			Elements pElements = element.children();
			    			for (Element element2 : pElements) {
//			    				System.out.println(element2);
								String text = element2.text();
//								if("".equals(text)||"工作职责：".equals(text)||"职位要求".equals(text)||"岗位职责：".equals(text)||"招聘要求:".equals(text)||"任职要求：".equals(text)||"职位描述：".equals(text)||"岗位描述:".equals(text)||"任职资格：".equals(text)||"职责：".equals(text)||"岗位职责".equals(text)||"职位要求：".equals(text)||"岗位要求：".equals(text)||"【岗位职责】".equals(text)||"【任职要求】".equals(text)||"岗位描述:".equals(text)){
								if("".equals(text)||text.contains("描述")||text.contains("工作职责")||text.contains("要求")||text.contains("岗位职责")||text.contains("任职要求")||text.contains("描述")||text.contains("任职资格")||"职责：".equals(text)||"岗位职责".equals(text)||"职位要求：".equals(text)||"岗位要求：".equals(text)||text.contains("岗位描述")){
									continue;
								}
//								if("工作地址：".equals(text)||text.contains("公司")||"福利待遇：".equals(text)||"福利待遇:".equals(text)||"福利待遇".equals(text)||"【福利待遇】：".equals(text)||"福利待遇：".equals(text)||"福利待遇：".equals(text)||text.contains("地址")){
								if(text.contains("地点")||text.contains("地址")||text.contains("公司")||text.contains("福利")||text.contains("时间")||text.contains("待遇")){
									break;
								}
								Matcher matcher = pattern.matcher(text);
								if(matcher.find()){
									text = text.replaceAll(s, "");
								}
								pattern = Pattern.compile(s1);
								matcher = pattern.matcher(text);
								if(matcher.find()){
									text = text.replaceAll(s1, "");
								}
								bw.write(text);
								bw.write("\n");
								/*if("福利待遇：".equals(text)||"任职要求：".equals(text)||"晋升空间：".equals(text)||"工作时间：".equals(text)){
									break;
								}*/
							
							}
						}
			    		
			    		String text = doc.body().text(); 
					
							
			} else {
				logger.error("get请求提交失败:" + url);
			}
		} catch (IOException e) {
			logger.error("get请求提交失败:" + url, e);
		}
		return message;
	}	
}

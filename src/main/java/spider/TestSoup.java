package spider;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.UUID;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

public class TestSoup {
	public static void main(String[] args) {
//		String oldLink = "https://so.csdn.net/so/search/s.do?q=Java%E9%9D%A2%E8%AF%95%E9%A2%98&t=blog&o=&s=&l=";
		String oldLink = "https://zhannei-dm.csdn.net/recommend/baidu_zhannei_search?keyword=Java%E9%9D%A2%E8%AF%95%E9%A2%98&domain_type=bbs";

		 doget(oldLink);
//		 sendPost1("https://zhannei-dm.csdn.net/recommend/baidu_zhannei_search", "keyword=Java%E9%9D%A2%E8%AF%95%E9%A2%98&domain_type=bbs");
//		System.out.println(result);
	}

	private static void doget(String oldLink) {
		try {
	          URL url = new URL(oldLink);
	          HttpURLConnection connection = (HttpURLConnection) url
	              .openConnection();
//	          connection.setRequestMethod("GET");
//	          connection.setRequestProperty(key, value);
	          connection.setConnectTimeout(2000);
	          connection.setReadTimeout(2000);
	          
	          if (connection.getResponseCode() == 200) {
	            InputStream inputStream = connection.getInputStream();
	            BufferedReader reader = new BufferedReader(
	                new InputStreamReader(inputStream, "UTF-8"));
	            String line = "";
	            File f = new File("C:\\wjp\\test4.txt");
	            if(f.exists()){
	            	f.createNewFile();
	            }
	            
	            BufferedWriter bw =  new BufferedWriter(new FileWriter(f));
	            while ((line = reader.readLine()) != null) {
	            	bw.write(line);
	            	System.out.println(line);
	            	
	            }
	            bw.close();
	            inputStream.close();
	          }
	          
	        } catch (MalformedURLException e) {
	          e.printStackTrace();
	        } catch (IOException e) {
	          e.printStackTrace();
	        }
	}
	
	private static void test3() {
		String url = "https://zhannei-dm.csdn.net/recommend/baidu_zhannei_search?keyword=Java%E9%9D%A2%E8%AF%95%E9%A2%98&domain_type=blog";
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		try {
			method.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			method.setRequestHeader("Content-Type", "application/json");
			client.executeMethod(method);
			RequestEntity entity = new StringRequestEntity("jsonParam.toString()");
//			client.executeMethod(method);
			
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            System.out.println("--------");
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println("result:"+result);
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
	// 构建唯一会话Id
	public static boolean httpPostWithJson(String url,String appId){
	    boolean isSuccess = false;
	    
	    HttpPost post = null;
	    try {
	        DefaultHttpClient httpClient = new DefaultHttpClient();

	        // 设置超时时间
	        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
	        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);
	            
	        post = new HttpPost(url);
	        // 构造消息头
	        post.setHeader("Content-type", "application/json; charset=utf-8");
	        post.setHeader("Connection", "Close");
	        String sessionId = getSessionId();
	        post.setHeader("SessionId", sessionId);
	        post.setHeader("appid", "");
	        // 构建消息实体
	        StringEntity entity = new StringEntity("keyword=Java%E9%9D%A2%E8%AF%95%E9%A2%98&domain_type=blog", Charset.forName("UTF-8"));
	        entity.setContentEncoding("UTF-8");
	        // 发送Json格式的数据请求
	        entity.setContentType("text/plain");
	        post.setEntity(entity);
	            
	        HttpResponse response = httpClient.execute(post);
	            
	        // 检验返回码
	        int statusCode = response.getStatusLine().getStatusCode();
	        if(statusCode != HttpStatus.SC_OK){
//	            LogUtil.info("请求出错: "+statusCode);
	            isSuccess = false;
	            
	        }else{
	            int retCode = 0;
	            String sessendId = "";
	            // 返回码中包含retCode及会话Id
	            for(Header header : response.getAllHeaders()){
	                if(header.getName().equals("retcode")){
	                    retCode = Integer.parseInt(header.getValue());
	                }
	                if(header.getName().equals("SessionId")){
	                    sessendId = header.getValue();
	                }
	                HttpEntity message = response.getEntity();
	                if(message != null){
	                	String body = EntityUtils.toString(message);
	                	System.out.println("message"+body);
	                }
	            }
	            
//	            if(ErrorCodeHelper.IAS_SUCCESS != retCode ){
//	                // 日志打印
////	                LogUtil.info("error return code,  sessionId: "sessendId"\t"+"retCode: "+retCode);
//	                isSuccess = false;
//	            }else{
//	                isSuccess = true;
//	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        isSuccess = false;
	    }finally{
	        if(post != null){
	            try {
	                post.releaseConnection();
	                Thread.sleep(500);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return isSuccess;
	}

	// 构建唯一会话Id
	public static String getSessionId(){
	    UUID uuid = UUID.randomUUID();
	    String str = uuid.toString();
	    return str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
	}
	
	public static String sendPost1(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println("result:"+result);
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
}

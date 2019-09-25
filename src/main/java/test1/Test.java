package test1;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Test {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		args = new String[] { "D:\\test.exe" };  
        if (args.length != 1) {  
            System.out.println("File path not given");  
            System.exit(1);  
        }  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            HttpPost httppost = new HttpPost(  
                    "http://localhost:8080/servlet/Servlet");  
  
            FileBody bin = new FileBody(new File(args[0]));  
            StringBody comment = new StringBody("A binary file of some kind",  
            ContentType.TEXT_PLAIN);  
  
            HttpEntity reqEntity = MultipartEntityBuilder.create()  
                    .addPart("bin", bin).addPart("comment", comment).build();  
  
            httppost.setEntity(reqEntity);  
  
            System.out.println("executing request " + httppost.getRequestLine());  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
                System.out.println("----------------------------------------");  
                System.out.println(response.getStatusLine());  
                HttpEntity resEntity = response.getEntity();  
                if (resEntity != null) {  
                    System.out.println("Response content length: "  
                            + resEntity.getContentLength());  
                    System.out.println(EntityUtils.toString(resEntity));  
                }  
                EntityUtils.consume(resEntity);  
            } finally {  
                response.close();  
            }  
        } finally {  
            httpclient.close();  
        }  
	}
}

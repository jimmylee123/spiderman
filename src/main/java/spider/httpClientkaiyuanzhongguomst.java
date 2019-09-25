package spider;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class httpClientkaiyuanzhongguomst {
	
	
	public static void main(String[] args) {
		httpClientkaiyuanzhongguomst client = new httpClientkaiyuanzhongguomst();
//		String result1 = new httpClient().get2("https://so.csdn.net/so/search/s.do?p=2&q=Java%E9%9D%A2%E8%AF%95%E9%A2%98&t=blog&domain=&o=&s=&u=&l=&f=&rbg=0");
//		
//		System.out.println(result1);
		Properties pro = new Properties();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:/Users/Administrator/Desktop/Spiderman2/src/main/java/spider2.properties")),"utf-8"));
			pro.load(br);
			
//			System.out.println("rsult:"+s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String src = pro.getProperty("spider.resource.src");
		for(int j=1;;j++){
			String keyWord = pro.getProperty("spider.keyword"+j);
			try {
				if(keyWord == null)
					break;
				keyWord = new String(keyWord.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			System.out.println(keyWord);
			for(int i=1;i<=50;i++){
	//			String result = client.get("https://zzk.cnblogs.com/s/blogpost?Keywords=java%E9%9D%A2%E8%AF%95%E9%A2%98&pageindex="+i);
				
//				String result = client.get(src+"?Keywords="+keyWord+"&pageindex="+i);
//				Map<String,String> map = new HashMap<String,String>();
//				map.put("Keywords", keyWord);
//				map.put("pageindex", String.valueOf(i));
//				String result = client.get1(src+"?scope=all&q="+keyWord+"&p="+i);
				String result = client.get1("https://www.oschina.net/search?q=java%E9%9D%A2%E8%AF%95%E9%A2%98&scope=all&fromerr=VTM9k5BV");

				//				String result = client.getMap(src,map);
//				https://www.oschina.net/search?q=java%E9%9D%A2%E8%AF%95%E9%A2%98&scope=all&fromerr=32A9XuVf
				getMessage(client, result,""+j+i, pro);
			}
		}
//		System.out.println(result);
	}
	private static void getMessage(httpClientkaiyuanzhongguomst client, String result,String i,Properties pro) {
		Document doc = Jsoup.parse(result);
		 BufferedWriter bw  = null;
		 BufferedWriter bw1  = null;
		 try {
		 String htmlResource = pro.getProperty("spider.html.resource");
		 String textResource = pro.getProperty("spider.text.resource");
		 File f = new File(htmlResource+i+".txt");
		 File f1 = new File(textResource+i+".txt");
		 if(doc == null){
			 System.out.println("doc 为空");
				f.delete();
				f1.delete();
			 return;
		 }	
		 Elements titleElements = doc.getElementsByClass("searchItemTitle");
			 if(titleElements == null){
				 System.out.println("titleElements 为空");
				 return;
			 }
		
		
			
			 if(f.exists()){
	            	f.createNewFile();
	            }
	            
	            bw  =  new BufferedWriter(new FileWriter(f));
	            bw1  =  new BufferedWriter(new FileWriter(f1));
//	            	System.out.println(text);
	            
	            String s= "[1-9]{1,}[\\.|\\、|\\，|\\）|\\．|\\)]";
	            Pattern pattern = Pattern.compile(s);
		 for (Element element : titleElements) {
			 Element  aElement = element.select("a").first();
			 if(aElement!=null){
				String url = aElement.attr("href");
				if(url != null){
					String javaResult = client.get1(url);
					if(javaResult==null){
						f.delete();
						f1.delete();
						return;
					}
					doc = Jsoup.parse(javaResult);
					bw1.write(doc.toString());
					Element bodyElement= doc.getElementById("cnblogs_post_body");
					if(bodyElement==null){
						f.delete();
						f1.delete();
						break;
					}
					Elements pElement = bodyElement.getElementsByTag("p");
					for (Element element2 : pElement) {
						String text = element2.text();
						if(text.contains("http")||text.contains("?")||text.length()==0)
							continue;
//						Elements e = element2.getElementsByTag("h");
//						if(e!=null){
//							
//						}
						Matcher	matcher = pattern.matcher(text);
						if(matcher.find()){
							text = text.replaceAll(s, "");
						}
						text = text.replaceAll("。", "\n");
						if(text.equals("")){
							
						}
//						text = text.replaceAll(" ", "");
						bw.write(text);
						 System.out.println(text);
					}
					
//					Pattern pattern = Pattern.compile("(?<=\\{)(.+?)(?=\\})");
//					Matcher mactcher = pattern.matcher(text);
//					if(mactcher.find()){
//						String message = mactcher.group();
//						System.out.println(message);
//						text.replaceAll(regex, replacement);
//					}
				}
////				System.out.println("url:"+url);
			 }
		}
	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					bw.close();
					bw1.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	/**
     * 创建一个可以访问Https类型URL的工具类，返回一个CloseableHttpClient实例
     */
    public static CloseableHttpClient createSSLClientDefault(){
        try {
            SSLContext sslContext=new SSLContextBuilder().loadTrustMaterial(
                    null,new TrustStrategy() {
                        //信任所有

						@Override
						public boolean isTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
								throws java.security.cert.CertificateException {
							// TODO Auto-generated method stub
							return true;
						}

                    }).build();
            SSLConnectionSocketFactory sslsf=new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }
	public String get(String url)
	      {
	          String result = null;
	          CloseableHttpClient httpClient = HttpClients.createDefault();
	         CloseableHttpResponse response = null;
	         try {
	        	 HttpGet get = new HttpGet(url);
	        	 

//	        	 get.setHeader("https(Request-Line)", "GET /s?t=b&w=java%E9%9D%A2%E8%AF%95%E9%A2%98 HTTP/1.1");
//	        	 get.setHeader("Host", "zzk.cnblogs.com");
	        	 get.setHeader("Connection", "keep-alive");
	        	 get.setHeader("Cache-Control", "max-age=0");
	        	 get.setHeader("Upgrade-Insecure-Requests", "1");
	        	 get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
	        	 get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
	        	 get.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
	        	 get.setHeader("Cookie", "ga=GA1.2.1580019499.1537520798; __gads=ID=923053300046ed07:T=1539240113:S=ALNI_MbPUhHyYnakb1eg10h3_fi-6Mz5EQ; NoRobotCookie=CfDJ8J0rgDI0eRtJkfTEZKR_e817YHT4ghzOhpUYtYlPDdl-2J2OrxKtvn1b6KVvfd-WDYdTedGIJA-3CH2ip8uH1DeF_F0Mw7a-dhQak24zgsdR_5DBYdpcuiszRRPk1Re1dQ; gr_user_id=231ce40c-cf51-451d-9722-8dbef345c1e7; grwng_uid=82d4eabc-113f-4505-87a6-3d653a75e301; __utmc=59123430; _gid=GA1.2.1291327819.1541470426; __utma=59123430.1580019499.1537520798.1541473435.1541488954.6; __utmz=59123430.1541488954.6.4.utmcsr=cnblogs.com|utmccn=(referral)|utmcmd=referral|utmcct=/");
	        	 get.setHeader("Accept-Encoding", "gzip, deflate");

	        	 response = httpClient.execute(get);
	             if(response != null && response.getStatusLine().getStatusCode() == 200)
	             {
	                 HttpEntity entity = response.getEntity();
	                 result = entityToString(entity);
	             }
	             return result;
	         } catch (IOException e) {
	             e.printStackTrace();
	         }catch (Exception e) {
	        	 e.printStackTrace();
	        	 byte[] bs = e.getMessage().getBytes();
	        	 System.out.println(bs[94]);
	        	 
	         }finally {
	             try {
	                 httpClient.close();
	                 if(response != null)
	                 {
	                     response.close();
	                 }
	             } catch (IOException e) {
	                 e.printStackTrace();
	             }
	         }
	         return null;
	     }
	
	public String get2(String url)
    {
        String result = null;
        CloseableHttpClient httpClient =createSSLClientDefault();
       HttpGet get = new HttpGet(url);
       CloseableHttpResponse response = null;
       try {
      	 

      	 get.setHeader("https(Request-Line)", "GET /s?t=b&w=java%E9%9D%A2%E8%AF%95%E9%A2%98 HTTP/1.1");
      	 get.setHeader("Host", "so.csdn.net");
      	 get.setHeader("Connection", "keep-alive");
      	 get.setHeader("Cache-Control", "max-age=0");
      	 get.setHeader("Upgrade-Insecure-Requests", "1");
      	 get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
      	 get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
      	 get.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
      	 get.setHeader("Cookie", "TY_SESSION_ID=288fea4d-e005-497a-8ec1-9a2933c400f5; JSESSIONID=D9C4873FEAF3F157CFC3CD7E4276FF5D; uuid_tt_dd=10_20054814410-1538039023415-757489; Hm_ct_6bcd52f51e9b3dce32bec4a3997715ac=1788*1*PC_VC; smidV2=20180927170851684250d9f18782752353a39d9b6cd56a0070373e94eb06e90; ARK_ID=JS5279ed972f6a24d25f2ffd25cfbe073a5279; __yadk_uid=H7OXrw4K5sh8yYFmQPl9FTnrp5SavCcg; dc_session_id=10_1540189715273.566026; UM_distinctid=166ebf4642020f-0a7cb6f0e4cf1d-323b5b03-100200-166ebf46421381; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1541556911,1541556947,1541559922,1541560196; _ga=GA1.2.186830833.1541562118; _gid=GA1.2.2034406790.1541562118; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1541569024; dc_tos=pht69s");
      	 get.setHeader("Accept-Encoding", "gzip, deflate");

      	 response = httpClient.execute(get);
           if(response != null && response.getStatusLine().getStatusCode() == 200)
           {
               HttpEntity entity = response.getEntity();
               result = entityToString(entity);
           }
           return result;
       } catch (IOException e) {
           e.printStackTrace();
       }finally {
           try {
               httpClient.close();
               if(response != null)
               {
                   response.close();
               }
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       return null;
   }
	
	
	public String get1(String url)
    {
        String result = null;
        CloseableHttpClient httpClient = createSSLClientDefault();
       HttpGet get = new HttpGet(url);
//       get.setHeader("Host", "so.csdn.net");
       CloseableHttpResponse response = null;
       try {
      	 response = httpClient.execute(get);
           if(response != null && response.getStatusLine().getStatusCode() == 200)
           {
               HttpEntity entity = response.getEntity();
               result = entityToString(entity);
           }
           return result;
       } catch (IOException e) {
           e.printStackTrace();
       }finally {
           try {
               httpClient.close();
               if(response != null)
               {
                   response.close();
               }
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       return null;
   }
	
	
	 public String getMap(String url,Map<String,String> map)
	      {
	          String result = null;
	         CloseableHttpClient httpClient = HttpClients.createDefault();
	         List<NameValuePair> pairs = new ArrayList<NameValuePair>();
	         for(Map.Entry<String,String> entry : map.entrySet())
	         {
	             pairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
	         }
	         CloseableHttpResponse response = null;
	         try {
	             URIBuilder builder = new URIBuilder(url);
	             builder.setParameters(pairs);
	             HttpGet get = new HttpGet(builder.build());
	             response = httpClient.execute(get);
	             if(response != null && response.getStatusLine().getStatusCode() == 200)
	             {
	                 HttpEntity entity = response.getEntity();
	                 result = entityToString(entity);
	             }
	             return result;
	         } catch (URISyntaxException e) {
	             e.printStackTrace();
	         } catch (ClientProtocolException e) {
	             e.printStackTrace();
	         } catch (IOException e) {
	             e.printStackTrace();
	         }finally {
	             try {
	                 httpClient.close();
	                 if(response != null)
	                 {
	                     response.close();
	                 }
	             } catch (IOException e) {
	                 e.printStackTrace();
	             }
	         }
	 
	         return null;
	     }
	
	
	 private String entityToString(HttpEntity entity) throws IOException {
		          String result = null;
		          if(entity != null)
		          {
		              long lenth = entity.getContentLength();
		              if(lenth != -1 && lenth < 2048)
		              {
		                  result = EntityUtils.toString(entity,"UTF-8");
		              }else {
		                 InputStreamReader reader = new InputStreamReader(entity.getContent(), "UTF-8");
		                 CharArrayBuffer buffer = new CharArrayBuffer(2048);
		                 char[] tmp = new char[1024];
		                 int l;
		                 while((l = reader.read(tmp)) != -1) {
		                     buffer.append(tmp,0 , l);
		                 }
		                 result = buffer.toString();
		             }
		         }
		         return result;
		     }
	 
	 
	 public String postJson(String url,String jsonString)
	      {
	          String result = null;
	         CloseableHttpClient httpClient = HttpClients.createDefault();
	         HttpPost post = new HttpPost(url);
	         CloseableHttpResponse response = null;
	         try {
	             post.setEntity(new ByteArrayEntity(jsonString.getBytes("UTF-8")));
	             response = httpClient.execute(post);
	             if(response != null && response.getStatusLine().getStatusCode() == 200)
	             {
	                 HttpEntity entity = response.getEntity();
	                 result = entityToString(entity);
	             }
	             return result;
	         } catch (UnsupportedEncodingException e) {
	             e.printStackTrace();
	         } catch (ClientProtocolException e) {
	             e.printStackTrace();
	         } catch (IOException e) {
	             e.printStackTrace();
	         }finally {
	             try {
	                 httpClient.close();
	                 if(response != null)
	                 {
	                     response.close();
	                 }
	             } catch (IOException e) {
	                 e.printStackTrace();
	             }
	         }
	         return null;
	     }
	 
	 public String postMap(String url,Map<String,String> map) {
		          String result = null;
		          CloseableHttpClient httpClient = HttpClients.createDefault();
		         HttpPost post = new HttpPost(url);
		         List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		         for(Map.Entry<String,String> entry : map.entrySet())
		         {
		             pairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
		         }
		         CloseableHttpResponse response = null;
		         try {
		             post.setEntity(new UrlEncodedFormEntity(pairs,"UTF-8"));
		             response = httpClient.execute(post);
		             if(response != null && response.getStatusLine().getStatusCode() == 200)
		             {
		                 HttpEntity entity = response.getEntity();
		                 result = entityToString(entity);
		            }
		            return result;
		        } catch (UnsupportedEncodingException e) {
		           e.printStackTrace();
		        } catch (ClientProtocolException e) {
		             e.printStackTrace();
		        } catch (IOException e) {
		             e.printStackTrace();
		         }finally {
		             try {
		                 httpClient.close();
		                 if(response != null)
		                 {
		                    response.close();
		                 }
		             } catch (IOException e) {
		                 e.printStackTrace();
		            }
		 
		         }
		         return null;
		     }
}

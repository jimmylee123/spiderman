package test1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Test3 {

	public static void main(String[] args) {
		Properties pro = new Properties();
		InputStream is;
		try {
			is = new FileInputStream(new File("C:/Users/Administrator/Desktop/Spiderman2/src/main/java/spider.properties"));
			pro.load(is);
			String s = pro.getProperty("spider.resource.src");
			System.out.println("rsult:"+s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

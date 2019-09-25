package spider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Test3 {

	public static void main(String[] args) throws IOException {
		String str = HttpRequestUtils.web("https://bbs.51credit.com/forum.php?mod=forumdisplay&fid=216");
//		System.out.println(str);
		Document doc = Jsoup.parse(str);
//		doc.children();
//		 Element element = doc.body();
//		Element ele = doc.getElementById("react-root");
//		ele.childNodes();
//		System.out.println(ele.html());
		 System.out.println(doc.html());	
		 File f = new File("");
		 FileWriter fi = new FileWriter(f);
		 BufferedWriter bw = new BufferedWriter(fi);
		 //要知道怎么用
	}
}

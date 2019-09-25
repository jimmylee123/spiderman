package spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupTest {

	public static void main(String[] args) {
		String html1 = "<div class='LR_list_item LR_list_item2'>" + " <ul id='ADS-P10010803'>"
				+ "     <li><a href='https://www.10100000.com/daikuancms/gerenchuangye/1447318839059.shtml' target='newblank'>你知道专业借款怎么办理吗</a></li>"
				+ "    <li><a href='https://www.10100000.com/daikuancms/gerenchuangye/1447318790599.shtml' target='newblank'>可以申请无抵押无担保创业借款吗</a></li>"
				+ "    <li><a href='https://www.10100000.com/daikuancms/gerenchuangye/1447318704199.shtml' target='newblank'>个人申请创业借款需要满足什么条件</a></li>"
				+ "    <li><a target='_blank' title='创业者要提防办理创业借款反被骗的情况' href='https://www.10100000.com/daikuancms/gerenchuangye/1463649926816.shtml '>平安普惠创业借款，助您创造出更美好的未来</a></li>"
				+ "    <li><a target='_blank' title='创业者要具备的几个要素有哪些' href='https://www.10100000.com/daikuancms/gerenchuangye/1463649860595.shtml'>刚大学毕业没有工作能申请创业借款吗？</a></li>"
				+ "    <li><a target='_blank' title='我想借款10万有哪些途径' href='https://www.10100000.com/daikuancms/gerenchuangye/1463117164647.shtml'>在平安普惠申请创业借款容易吗</a></li>"
				+ "     <!-- <li><a id='ADS-P10010819' target='_blank' title='个人创业贷款是热血青年的首选' href='https://www.10100000.com/daikuancms/gerenchuangye/1473300807011.shtml'>个人创业贷款是热血青年的首选</a></li> -->"
				+ "  </ul>" + " </div>" + "<div class='LR_list_item'>" + " <ul id='ADS-P10010802'>"
				+ "  <li><a href='https://www.10100000.com/daikuancms/gerenchuangye/1473299581730.shtml' target='newblank'>据说有下岗证可以借款是真的吗</a></li>"
				+ "  <li><a href='https://www.10100000.com/daikuancms/gerenchuangye/1473299814565.shtml' target='newblank'>每位少女都有一个甜品梦，平安普惠创业借款助您圆梦</a></li>"
				+ " <li><a target='_blank' title='农村小额借款怎么贷额度最高是多少' href='https://www.10100000.com/daikuancms/gerenchuangye/1473300579702.shtml'>农村小额借款怎么贷额度最高是多少</a></li>"
				+ " <li><a target='_blank' title='青年创业基金借款条件是什么' href='https://www.10100000.com/daikuancms/gerenchuangye/1473300807011.shtml'>青年创业基金借款条件是什么</a></li>"
				+ " <li><a href='https://www.10100000.com/daikuancms/gerenchuangye/1435824946510.shtml' target='newblank'>怎样申请个人创业无息借款</a></li>"
				+ " <li><a href='https://www.10100000.com/daikuancms/gerenchuangye/1435824794090.shtml' target='newblank'>个人创业无息借款申请条件</a></li>"
				+ "   <li><a href='https://www.10100000.com/daikuancms/gerenchuangye/1435824635994.shtml' target='newblank'>个人创业无息贷款申请流程</a></li>"
				+ "  <li><a href='https://www.10100000.com/daikuancms/gerenchuangye/1448414785371.shtml' target='newblank'>个人创业贷款怎么贷最合适</a></li>"
				+ "  <li><a href='https://www.10100000.com/daikuancms/gerenchuangye/1447319095007.shtml' target='newblank'>银行有创业贷款吗</a></li>"
				+ "  <li><a href='https://www.10100000.com/daikuancms/gerenchuangye/1447318982298.shtml' target='newblank'>我想创业怎么贷款</a></li> "
				+ "   <li><a id='ADS-P10010810' href='https://www.10100000.com/daikuancms/gerenchuangye/1425455750801.shtml' target='newblank'>个人创业贷款助力自主创业者圆梦</a></li> "
				+ "  </ul>" + "</div>";

		String html = "<p>An <a href='http://example.com/'><b>example</b></a> link.<b>dsds</b></p>";
		Document doc = Jsoup.parse(html1);// 解析HTML字符串返回一个Document实现
		Element link = doc.select("a").first();// 查找第一个a元素
		String text = doc.body().text(); // "An example link"//取得字符串中的文本
		String linkHref = link.attr("href"); // "http://example.com/"//取得链接地址
		String linkText = link.text(); // "example""//取得链接地址中的文本
		String linkOuterH = link.outerHtml();
		// "<a href="http://example.com"><b>example</b></a>"
		String linkInnerH = link.html();

		Elements els = doc.body().children();
		/*for (Element element : els) {
			System.out.println(element.text());
		}*/

		System.out.println(text);
	}
}

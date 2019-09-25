/**
 * @Project Name:spider 
 * @file:HtmlPaerser.java
 * @author:user
 * @date:2017年11月3日 下午5:59:12
 **/

package spider;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @description:(功能描述)
 * @class:HtmlPaerser
 * @company:平安科技(深圳)有限公司
 * @author:user
 * @daate:2017年11月3日 下午5:59:12
 */
public class HtmlPaerser
{
    private String currURL = null;
    
    private List<String> subURL = new ArrayList<String>();
    
    private String nextURL = null;
    
    public HtmlPaerser(String url)
    {
        this.currURL = url;
    }
    
    public Document getHtml()
    {
        try
        {
            return Jsoup.connect(currURL).get();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}


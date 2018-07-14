package models;

import org.junit.Test;
import org.mockito.Mockito;

public class RSSItemModelTest
{

    @Test
    public void RSSDateChecker()
    {
        RSSItemModel rssItemModel = new RSSItemModel("شنا در کانال\u200Cهای سیمانی - اهواز",
                "description",
                "https://www.isna.ir/photo/97042312224/",
                "test",
                "14 Jul 2018 16:09:10 +0430",
                new NewsWebPageModel("http://www.varzesh3.com/rss/all",
                        "news-page--news-text",
                        "dd MMM yyyy HH:mm:ss zzz",  null) );
    }

}
package database;

import models.NewsWebPageModel;
import models.RSSItemModel;
import org.junit.Test;

public class DataBaseServiceTest
{
    @Test
    public void mainTest()
    {
        DataBaseService.getInstance();
    }

    @Test
    public void addWebSiteTest(){
        DataBaseService.getInstance().addWebSite(new NewsWebPageModel("http://www.varzesh3.com/rss/all", "news-page--news-text", "",DataBaseService.getInstance()));
        DataBaseService.getInstance().addWebSite(new NewsWebPageModel("https://en.isna.ir/rss" , "item-body", "", DataBaseService.getInstance()));
    }

    @Test
    public void getWebsiteListTest(){

        for (NewsWebPageModel newsWebPageModel : DataBaseService.getInstance().getWebsites())
            System.out.println(newsWebPageModel);
    }

    @Test
    public void updateRSSItemsTest()
    {
        DataBaseService.getInstance().getWebsites().get(1).update();
    }

    @Test
    public void getWebsiteTest()
    {
        System.out.println(DataBaseService.getInstance().getWebsite("http://www.varzesh3.com/rss/all"));
    }

    @Test
    public void getAllRSSDataTest()
    {
        System.out.println(DataBaseService.getInstance().getAllRSSData());
    }

    @Test
    public void getRSSDataFromWebSiteTest()
    {
        System.out.println(DataBaseService.getInstance().getRSSDataFromWebSite("https://en.isna.ir/rss"));
    }

    @Test
    public void getArticleTest()
    {
        System.out.println(DataBaseService.getInstance().getArticle("http://www.varzesh3.com/news/1537867/ضیایی-اتاق-رییس-فدراسیون-خانه-بازیکنان-است"));
    }

}
package database;

import models.NewsWebPageModel;
import models.RSSItemModel;
import org.junit.Test;

public class DataBaseServiceTest
{
    @Test
    public void mainTest()
    {
        DataBaseService dataBaseService= new DataBaseService();
    }

    @Test
    public void addWebSiteTest(){
        DataBaseService dataBaseService= new DataBaseService();
        dataBaseService.addWebSite(new NewsWebPageModel("http://www.varzesh3.com/rss/all", "news-page--news-text", dataBaseService));
        dataBaseService.addWebSite(new NewsWebPageModel("https://en.isna.ir/rss" , "item-body", dataBaseService));
    }

    @Test
    public void getWebsiteListTest(){

        DataBaseService dataBaseService= new DataBaseService();
        for (NewsWebPageModel newsWebPageModel : dataBaseService.getWebsites())
            System.out.println(newsWebPageModel);
    }

    @Test
    public void updateRSSItemsTest()
    {
        DataBaseService dataBaseService = new DataBaseService();
        dataBaseService.getWebsites().get(1).update();
    }

    @Test
    public void getWebsiteTest()
    {
        DataBaseService dataBaseService = new DataBaseService();
        System.out.println(dataBaseService.getWebsite("http://www.varzesh3.com/rss/all"));
    }

    @Test
    public void getAllRSSDataTest()
    {
        DataBaseService dataBaseService = new DataBaseService();
        System.out.println(dataBaseService.getAllRSSData());
    }

    @Test
    public void getRSSDataFromWebSiteTest()
    {
        DataBaseService dataBaseService = new DataBaseService();
        System.out.println(dataBaseService.getRSSDataFromWebSite("https://en.isna.ir/rss"));
    }

    @Test
    public void getArticleTest()
    {
        DataBaseService dataBaseService = new DataBaseService();
        System.out.println(dataBaseService.getArticle("http://www.varzesh3.com/news/1537867/ضیایی-اتاق-رییس-فدراسیون-خانه-بازیکنان-است"));
    }

}
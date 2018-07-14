package database;

import database.implementation.DataBase;
import websiteTable.model.NewsWebPageModel;
import org.junit.Test;

public class DataBaseTest
{
    @Test
    public void mainTest()
    {
        DataBase.getInstance();
    }

    @Test
    public void addWebSiteTest(){
        DataBase.getInstance().addWebSite(new NewsWebPageModel("http://www.varzesh3.com/rss/all", "news-page--news-text", "",DataBase.getInstance()));
        DataBase.getInstance().addWebSite(new NewsWebPageModel("https://en.isna.ir/rss" , "item-body", "", DataBase.getInstance()));
    }

    @Test
    public void getWebsiteListTest(){

        for (NewsWebPageModel newsWebPageModel : DataBase.getInstance().getWebsites())
            System.out.println(newsWebPageModel);
    }

    @Test
    public void updateRSSItemsTest()
    {
        DataBase.getInstance().getWebsites().get(1).update();
    }

    @Test
    public void getWebsiteTest()
    {
        System.out.println(DataBase.getInstance().getWebsite("http://www.varzesh3.com/rss/all"));
    }

    @Test
    public void getAllRSSDataTest()
    {
        System.out.println(DataBase.getInstance().getAllRSSData());
    }

    @Test
    public void getRSSDataFromWebSiteTest()
    {
        System.out.println(DataBase.getInstance().getRSSDataFromWebSite("https://en.isna.ir/rss"));
    }

    @Test
    public void getArticleTest()
    {
        System.out.println(DataBase.getInstance().getArticle("http://www.varzesh3.com/news/1537867/ضیایی-اتاق-رییس-فدراسیون-خانه-بازیکنان-است"));
    }

}
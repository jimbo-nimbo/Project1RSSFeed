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
//        dataBaseService.addWebSite(new NewsWebPageModel("https://en.isna.ir/rss" , "item-body", dataBaseService));
    }

    @Test
    public void getWebsiteListTest(){

        DataBaseService dataBaseService= new DataBaseService();
        for (NewsWebPageModel newsWebPageModel : dataBaseService.getWebsites())
            System.out.println(newsWebPageModel);
    }

    @Test
    public void addRSSItemTest()
    {
        DataBaseService dataBaseService = new DataBaseService();
        dataBaseService.getWebsites().get(0).update();
    }
}
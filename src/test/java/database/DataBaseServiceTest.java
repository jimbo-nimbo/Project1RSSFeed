package database;

import models.NewsWebPageModel;
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
        dataBaseService.addWebSite(new NewsWebPageModel("faceook.com", "face1"));
    }

    @Test
    public void getWebsiteListTest(){
        DataBaseService dataBaseService= new DataBaseService();
        for (NewsWebPageModel newsWebPageModel : dataBaseService.getWebsites())
            System.out.println(newsWebPageModel);
    }
}
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
        dataBaseService.addWebSite(new NewsWebPageModel("google.com2", "new class"));
    }
}
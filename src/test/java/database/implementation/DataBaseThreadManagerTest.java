package database.implementation;

import database.DataBaseThreadManager;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class DataBaseThreadManagerTest
{
    private static DataBaseThreadManager dataBaseThreadManager;

    @Before
    @Test
    public void getInstance()
    {
        dataBaseThreadManager = DataBaseThreadManager.getInstance();
    }

    @Test
    public void addWebSite()
    {
        try
        {
            dataBaseThreadManager.addWebSite("https://www.isna.ir/rss",
                    "item-text", "E, dd MMM yyyy HH:mm:ss zzz");
            dataBaseThreadManager.addWebSite("https://www.yjc.ir/fa/rss/allnews",
                    "body", "dd MMM yyyy HH:mm:ss zzz");
            dataBaseThreadManager.addWebSite("http://www.tabnak.ir/fa/rss/allnews",
                    "item-text", "dd MMM yyyy HH:mm:ss zzz");
            dataBaseThreadManager.addWebSite("http://www.irna.ir/en/rss.aspx?kind=-1&area=0",
                    "bodytext", "EEE, dd MMM yyyy HH:mm");
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void getWebSites()
    {
        try
        {
            System.out.println(dataBaseThreadManager.getWebSites().get());
        } catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void updateDataBase()
    {
        dataBaseThreadManager.updateDataBase();
    }

    @Test
    public void updateDatabaseForWebsite()
    {
        dataBaseThreadManager.updateDatabaseForWebsite(
                "https://www.isna.ir/rss");
        dataBaseThreadManager.updateDatabaseForWebsite(
                "https://www.yjc.ir/fa/rss/allnews");
        dataBaseThreadManager.updateDatabaseForWebsite(
                "http://www.tabnak.ir/fa/rss/allnews");
    }

    @Test
    public void getWebSiteRssData() throws InterruptedException
    {
        try
        {
            System.out.println("hello");
            System.out.println(
                    dataBaseThreadManager.getWebSiteRssData("https://www.yjc.ir/fa/rss/allnews").get());
        } catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }

        dataBaseThreadManager.getExecutor().shutdown();
        while (!dataBaseThreadManager.getExecutor().awaitTermination(24L, TimeUnit.HOURS)) {
            System.out.println("Not yet. Still waiting for termination");
        }

    }

    @Test
    public void getAllRssData()
    {
        System.out.println("hello");
        try
        {
            System.out.println(
                    dataBaseThreadManager.getAllRssData().get().size());
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        }
    }
}
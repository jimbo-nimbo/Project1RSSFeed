package database.implementation;

import cli.RssService;
import core.Core;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class RssServiceTest {
  private static RssService rssService;

  @Before
  @Test
  public void getInstance() {
    rssService = Core.getInstance().getRssService();
  }

  @Test
  public void addWebSite() {
    try {
      rssService.addWebSite("https://www.isna.ir/rss", "item-text");
      rssService.addWebSite(
          "https://www.yjc.ir/fa/rss/allnews", "body");
      rssService.addWebSite(
          "http://www.tabnak.ir/fa/rss/allnews", "item-text");
      rssService.addWebSite(
          "http://www.irna.ir/en/rss.aspx?kind=-1&area=0", "bodytext");
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void getWebSites() {
    try {
      System.out.println(rssService.getWebSites().get());
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void updateDataBase() {
    rssService.updateDataBase();
  }

  @Test
  public void updateDatabaseForWebsite() {
    rssService.updateDatabaseForWebsite("https://www.isna.ir/rss");
    rssService.updateDatabaseForWebsite("https://www.yjc.ir/fa/rss/allnews");
    rssService.updateDatabaseForWebsite("http://www.tabnak.ir/fa/rss/allnews");
  }

  @Test
  public void getWebSiteRssData() throws InterruptedException {
    try {
      System.out.println("hello");
      System.out.println(rssService.getWebSiteRssData("https://www.yjc.ir/fa/rss/allnews").get());
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    rssService.getExecutor().shutdown();
    while (!rssService.getExecutor().awaitTermination(24L, TimeUnit.HOURS)) {
      System.out.println("Not yet. Still waiting for termination");
    }
  }

  @Test
  public void getAllRssData() {
    System.out.println("hello");
    try {
      System.out.println(rssService.getAllRssData().get().size());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }


}



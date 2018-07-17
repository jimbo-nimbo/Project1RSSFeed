package rssRepository;

import core.Core;
import database.DatabaseConnectionPool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import searchEngine.SearchEngine;
import webSiteRepository.NewsWebPageModel;
import webSiteRepository.WebSiteRepository;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RssItemRepositoryTest {

  DatabaseConnectionPool dataBase = Core.getInstance().getDatabaseConnectionPool();
  RssItemRepository rssItemRepository = Core.getInstance().getRssRepository();
  WebSiteRepository webSiteRepository = Core.getInstance().getWebSiteRepository();
  SearchEngine searchEngine = Core.getInstance().getSearchEngine();

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void addRSSItem() {}

  @Test
  public void getAllRSSDataTest() {
    System.out.println(rssItemRepository.getAllRSSData());
  }
  @Test
  public void getAllRSSData() {
    NewsWebPageModel newsWebPageModel =
            webSiteRepository.getWebsite("http://www.irinn.ir/fa/rss/allnews");
    newsWebPageModel.update();
    ArrayList<RSSItemModel> ans = (ArrayList<RSSItemModel>) rssItemRepository.getAllRSSData();
    for (RSSItemModel rssItemModel : ans) {
      System.err.println(rssItemModel.getLink());
    }
  }

  @Test
  public void updateRSSItemsTest() {
    webSiteRepository.getWebsites().get(1).update();
  }


  @Test
  public void getRSSItem() {}

  @Test
  public void takeRssItemFromResultSetWithHashCheck() {}
  @Test
  public void getRSSDataFromWebSiteTest() {
    System.out.println(rssItemRepository.getRSSDataFromWebSite("https://en.isna.ir/rss"));
  }


}
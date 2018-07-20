package rssRepository;

import core.Core;
import database.DatabaseConnectionPool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import searchEngine.SearchEngine;
import webSiteRepository.NewsWebPageModel;
import webSiteRepository.WebSiteRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class RssItemRepositoryTest {

  DatabaseConnectionPool dataBase = Core.getInstance().getDatabaseConnectionPool();
  RssItemRepository rssItemRepository = Core.getInstance().getRssRepository();
  WebSiteRepository webSiteRepository = Core.getInstance().getWebSiteRepository();
  SearchEngine searchEngine = Core.getInstance().getSearchEngine();

  @Before
  public void setUp() throws Exception {

  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void addRSSItem() throws IOException, ParseException, SQLException
  {
//    String link = "http://www.tabnak.ir/fa/news/817425/۵-کشته-و-مصدوم-در-تصادف-خونین-در-اردبیل";
//    String title = "testlink";
//    String description = "testlink";
//    String time = "17 Jul 2018 22:15:01 +0430";
//    String url = "http://www.tabnak.ir/fa/rss/allnews";
//    NewsWebPageModel newsWebPageModel = webSiteRepository.getWebsite(url);
//    RSSItemModel rssItemModel = new RSSItemModel(title, description, link, time, newsWebPageModel);
//    rssItemRepository.addRSSItem(rssItemModel);
  }

  @Test
  public void getAllRSSDataTest() throws SQLException
  {
//    ArrayList<RSSItemModel> rssItemModels = (ArrayList<RSSItemModel>) rssItemRepository.getAllRSSData();
//    for(RSSItemModel rssItemModel : rssItemModels){
//      System.out.println(rssItemModel.toString());
//    }
  }
  @Test
  public void getRSSData() throws SQLException, IOException, ParseException
  {
//    NewsWebPageModel newsWebPageModel =
//            webSiteRepository.getWebsite("https://www.isna.ir/rss");
//    newsWebPageModel.update();
//    ArrayList<RSSItemModel> ans = (ArrayList<RSSItemModel>) rssItemRepository.getAllRSSData();
//    for (RSSItemModel rssItemModel : ans) {
//      System.err.println(rssItemModel.getLink());
//    }
  }

  @Test
  public void updateRSSItemsTest() {
    //webSiteRepository.getWebsites().get(1).update();
  }


  @Test
  public void getRSSItemByID() throws SQLException
  {

//      System.out.println(Core.getInstance().getRssRepository().getRSSItem(1).toString());
  }

  @Test
  public void takeRssItemFromResultSetWithHashCheck() {}
  @Test
  public void getRSSDataFromWebSiteTest() throws SQLException
  {
//    System.out.println(rssItemRepository.rssForWebsite("https://en.isna.ir/rss"));
  }


}
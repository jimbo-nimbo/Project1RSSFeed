package searchEngine.implementions;

import core.Core;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rssRepository.RSSItemModel;
import searchEngine.SearchEngine;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchEngineTest {
  String INJECT_DATA_TO_RSS_ITEM =
      "INSERT  INTO RssItem "
          + "(title, description, link, pubDate, article, newsWebPage) VALUES "
          + "(?, ?, ?, ?, ?, (SELECT (url) FROM WebSite WHERE url LIKE ?));";
  String DELETE_DATA_FROM_RSS_ITEM = "DELETE FROM RssItem " + "WHERE link = ?;";
  private SearchEngine searchEngine = Core.getInstance().getSearchEngine();

  @Before
  public void setup() {
    injectDate();
  }

  @After
  public void clean() {
    try (Connection conn = Core.getInstance().getDatabaseConnectionPool().getConnection()) {
      PreparedStatement preparedStatement = conn.prepareStatement(DELETE_DATA_FROM_RSS_ITEM);
      preparedStatement.setString(1, "testLink");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void injectDate() {
    String title = "byeTestblahblah";
    String description = "describtion";
    String link = "testLink";
    String article = "helloTestblahblah";
    Date pubDate = new Date(System.currentTimeMillis());
    String newsWebPage = "http://www.irinn.ir/fa/rss/allnews";
    //        DataBase.getInstance().execute(rssRepository.SearchInjectQuery.INJECT_DATA_TO_RSS_ITEM.toString(),
    // title, description, link, pubDate, article, newsWebPage);

  }

  @Test
  public void searchTitle() {
    //        ArrayList<RSSItemModel> searchResult = (ArrayList<RSSItemModel>)
    // searchEngine.searchTitle("bye");
    //        System.err.println(searchResult.get(0).getTitle());
    ArrayList<RSSItemModel> searchResult = (ArrayList<RSSItemModel>) searchEngine.searchTitle("10");
    System.err.println(searchResult.get(0).getTitle());
    searchResult = (ArrayList<RSSItemModel>) searchEngine.searchTitle("برق");
    System.err.println(searchResult.get(0).getTitle());
  }

  @Test
  public void searchArticle() {
    ArrayList<RSSItemModel> searchResult =
        (ArrayList<RSSItemModel>) searchEngine.searchArticle("hello");
    System.err.println(searchResult.get(0).getTitle());
    searchResult = (ArrayList<RSSItemModel>) searchEngine.searchArticle("24");
    System.err.println(searchResult.get(0).getArticle());
    searchResult = (ArrayList<RSSItemModel>) searchEngine.searchArticle("اسپورت");
    System.err.println(searchResult.get(0).getArticle());
  }

  @Test
  public void searchAll() {
    ArrayList<RSSItemModel> searchResult =
        (ArrayList<RSSItemModel>) searchEngine.searchAll("hello");
    System.err.println(searchResult.get(0).getTitle());
    searchResult = (ArrayList<RSSItemModel>) searchEngine.searchAll("24");
    System.err.println(searchResult.get(0).getArticle());
    searchResult = (ArrayList<RSSItemModel>) searchEngine.searchAll("اسپورت");
    System.err.println(searchResult.get(0).getArticle());
  }
}

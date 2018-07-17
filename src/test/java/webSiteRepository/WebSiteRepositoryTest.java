package webSiteRepository;

import core.Core;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rssRepository.RSSItemTableQueries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WebSiteRepositoryTest {

  Core core = Core.getInstance();
  WebSiteRepository webSiteRepository = new WebSiteRepository(core);

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void addWebSite() {
    webSiteRepository.addWebSite(
        new NewsWebPageModel(
            "https://www.isna.ir/rss", "item-text", "E, dd MMM yyyy HH:mm:ss zzz"));
    webSiteRepository.addWebSite(
            new NewsWebPageModel(
                    "http://www.irna.ir/en/rss.aspx?kind=-1&area=0", "bodytext", "EEE, dd MMM yyyy HH:mm"));
  }

  @Test
  public void addWebSiteInjectionTest(){
    try (Connection conn = Core.getInstance().getDatabaseConnectionPool().getConnection()) {
      core.getDatabaseConnectionPool().execute(conn, RSSItemTableQueries.DROP_RSS_ITEM.toString());
      core.getDatabaseConnectionPool().execute(conn, WebsiteTableQueries.DROP_WEB_SITE_TABLE.toString());
      core.getDatabaseConnectionPool().execute(conn, WebsiteTableQueries.CREATE_WEBSITE_TABLE_IF_NOT_EXISTS.toString());
      core.getDatabaseConnectionPool().execute(conn, RSSItemTableQueries.CREATE_RSSITEM_TABLE_IF_NOT_EXISTS.toString());
      addWebSite();
      ResultSet resultSet =
              core.getDatabaseConnectionPool().executeQuery(conn, WebsiteTableQueries.SELECT_ALL_WEBSITES.toString());
      resultSet.beforeFirst();
      while (resultSet.next()) {
        System.out.println("added this webpagemodel : " + resultSet.getString("url"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void getWebsite() {
    System.out.println(webSiteRepository.getWebsite("https://www.yjc.ir/fa/rss/allnews").toString());
    System.out.println(webSiteRepository.getWebsite("http://www.varzesh3.com/rss/all").toString());
  }

  @Test
  public void getWebsites() {
    for (NewsWebPageModel newsWebPageModel : webSiteRepository.getWebsites()) {
      System.out.println(newsWebPageModel.toString());
    }
  }
}

package webSiteRepository;

import core.Core;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import rssRepository.RSSItemTableQueries;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import static junit.framework.TestCase.assertEquals;


public class WebSiteRepositoryTest
{

    Core core = Core.getInstance();
    WebSiteRepository webSiteRepository = new WebSiteRepository(core);


  public void addWebSiteInjectionTest() {
    try (Connection conn = Core.getInstance().getDatabaseConnectionPool().getConnection()) {
      conn.createStatement()
              .execute(WebsiteTableQueries.CREATE_WEBSITE_TABLE_IF_NOT_EXISTS.toString());
      conn.createStatement()
              .execute(RSSItemTableQueries.CREATE_RSSITEM_TABLE_IF_NOT_EXISTS.toString());
      conn.createStatement().execute(RSSItemTableQueries.CLEAR_TABLE_RSSITEM.toString());
      conn.createStatement().execute(WebsiteTableQueries.CLEAR_TABLE_WEB_SITE.toString());
      Thread.sleep(2000);
    } catch (SQLException | InterruptedException e) {
      //TODO
    }
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void addGetWebSite() throws ParseException, SQLException, IOException {
    addWebSiteInjectionTest();
    webSiteRepository.addWebSite("https://www.isna.ir/rss", "item-text");
    webSiteRepository.addWebSite("http://www.tabnak.ir/fa/rss/allnews", "item-text");
    webSiteRepository.addWebSite("http://www.irna.ir/en/rss.aspx?kind=-1&area=0", "bodytext");
    webSiteRepository.addWebSite("https://www.yjc.ir/fa/rss/allnews", "body");
    webSiteRepository.addWebSite("http://www.irinn.ir/fa/rss/allnews", "body");
    //webSiteRepository.addWebSite("http://www.varzesh3.com/rss/all", "news-page--news-text");
    NewsWebPageModel newsWebPageModel1 = webSiteRepository.getWebsite("https://www.yjc.ir/fa/rss/allnews");
    NewsWebPageModel newsWebPageModel2 = webSiteRepository.getWebsite("https://www.isna.ir/rss");
    assertEquals("body", newsWebPageModel1.getTargetClass());
    assertEquals("item-text", newsWebPageModel2.getTargetClass());
  }


  @Test
  public void getWebsiteByID() {
        //System.out.println(webSiteRepository.getWebsite().toString());
  }

  @Test
  public void getWebsites() throws SQLException {
    assertEquals(webSiteRepository.getWebsites().size(), 5);
  }
}

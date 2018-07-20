package webSiteRepository;

import core.Core;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rssRepository.RSSItemTableQueries;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class WebSiteRepositoryTest
{

    Core core = Core.getInstance();
    WebSiteRepository webSiteRepository = new WebSiteRepository(core);

    @Before
    public void setUp() throws Exception
    {
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void addWebSite()
    {
        try
        {
            webSiteRepository.addWebSite("https://www.isna.ir/rss", "item-text");
            webSiteRepository.addWebSite("http://www.tabnak.ir/fa/rss/allnews", "item-text");
            webSiteRepository.addWebSite("http://www.irna.ir/en/rss.aspx?kind=-1&area=0", "bodytext");
            webSiteRepository.addWebSite("https://www.yjc.ir/fa/rss/allnews", "body");

            webSiteRepository.addWebSite("http://www.irinn.ir/fa/rss/allnews", "body");
        } catch (SQLException | ParseException | IOException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void addWebSiteInjectionTest()
    {
        try (Connection conn = Core.getInstance().getDatabaseConnectionPool().getConnection())
        {
            conn.createStatement().execute(RSSItemTableQueries.DROP_RSS_ITEM.toString());
            conn.createStatement().execute(WebsiteTableQueries.DROP_WEB_SITE_TABLE.toString());
            conn.createStatement()
                    .execute(WebsiteTableQueries.CREATE_WEBSITE_TABLE_IF_NOT_EXISTS.toString());
            conn.createStatement()
                    .execute(RSSItemTableQueries.CREATE_RSSITEM_TABLE_IF_NOT_EXISTS.toString());
            Thread.sleep(5000);
            addWebSite();

            ResultSet resultSet =
                    conn.createStatement().executeQuery(WebsiteTableQueries.SELECT_ALL_WEBSITES.toString());
            resultSet.beforeFirst();
            while (resultSet.next())
            {
                System.out.println("added this webpagemodel : " + resultSet.getString("link"));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void getWebsite()
    {
        // webSiteRepository = Core.getInstance().getWebSiteRepository();
        // System.out.println(webSiteRepository.getWebsites());
        try
        {
            System.out.println(
                    webSiteRepository.getWebsite("https://www.yjc.ir/fa/rss/allnews").toString());
            System.out.println(webSiteRepository.getWebsite("http://www.varzesh3.com/rss/all").toString());
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void getWebsiteByID()
    {
        try
        {
            System.out.println(webSiteRepository.getWebsite(2).toString());
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void getWebsites()
    {
        try
        {
            for (NewsWebPageModel newsWebPageModel : webSiteRepository.getWebsites())
            {
                System.out.println(newsWebPageModel.toString());
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}

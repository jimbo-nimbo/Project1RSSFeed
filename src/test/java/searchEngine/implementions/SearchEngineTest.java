package searchEngine.implementions;

import core.Core;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rssRepository.RSSItemModel;
import rssRepository.SearchInjectQuery;
import searchEngine.SearchEngine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class SearchEngineTest
{
    String INJECT_DATA_TO_RSS_ITEM = (
            "INSERT  INTO RssItem "
                    + "(title, description, link, pubDate, article, WID) VALUES "
                    + "(?, ?, ?, ?, ?, (SELECT (WID) FROM WebSite WHERE link LIKE ?));");
    String DELETE_DATA_FROM_RSS_ITEM = ("DELETE FROM RssItem " + "WHERE link = ?;");

    Core core = Core.getInstance();
    private SearchEngine searchEngine = Core.getInstance().getSearchEngine();

    @Before
    public void setup()
    {
        injectDate();
    }

    @After
    public void clean() throws SQLException
    {
        try (Connection conn = Core.getInstance().getDatabaseConnectionPool().getConnection())
        {
            PreparedStatement preparedStatement = conn.prepareStatement(DELETE_DATA_FROM_RSS_ITEM);
            preparedStatement.setString(1, "testLink");
            preparedStatement.execute();
        } catch (SQLException e)
        {
            Core.getInstance().logToFile(e.getMessage());
            throw e;
        }
    }

    @Test
    public void injectDate()
    {
        String title = "byeTestblahblah";
        String description = "describtion";
        String link = "testLink";
        String article = "helloTestblahblah";
        Timestamp pubDate = new Timestamp(System.currentTimeMillis());
        String newsWebPage = "https://www.isna.ir/rss";
        try (Connection conn = Core.getInstance().getDatabaseConnectionPool().getConnection())
        {
            PreparedStatement stmnt = conn.prepareStatement(SearchInjectQuery.INJECT_DATA_TO_RSS_ITEM.toString());
            stmnt.setString(1, title);
            stmnt.setString(2, description);
            stmnt.setString(3, link);
            stmnt.setTimestamp(4, pubDate);
            stmnt.setString(5, article);
            stmnt.setString(6, newsWebPage);
            stmnt.execute();
        } catch (SQLException e)
        {
            Core.getInstance().logToFile(e.getMessage());
        }
    }

    @Test
    public void searchTitle() throws SQLException
    {
        //        ArrayList<RSSItemModel> searchResult = (ArrayList<RSSItemModel>)
        // searchEngine.searchTitle("bye");
        //        System.err.println(searchResult.get(0).getTitle());
        ArrayList<RSSItemModel> searchResult = (ArrayList<RSSItemModel>) searchEngine.searchTitle("10");
        System.err.println(searchResult.get(0).getTitle());
        searchResult = (ArrayList<RSSItemModel>) searchEngine.searchTitle("برق");
        System.err.println(searchResult.get(0).getTitle());
    }

    @Test
    public void searchArticle() throws SQLException
    {
        ArrayList<RSSItemModel> searchResult =
                (ArrayList<RSSItemModel>) searchEngine.searchArticle("hello");
        System.err.println(searchResult.get(0).getTitle());
        searchResult = (ArrayList<RSSItemModel>) searchEngine.searchArticle("24");
        System.err.println(searchResult.get(0).getArticle());
        searchResult = (ArrayList<RSSItemModel>) searchEngine.searchArticle("اسپورت");
        System.err.println(searchResult.get(0).getArticle());
    }

    @Test
    public void searchAll() throws SQLException
    {
        ArrayList<RSSItemModel> searchResult =
                (ArrayList<RSSItemModel>) searchEngine.searchAll("hello");
        System.err.println(searchResult.get(0).getTitle());
        searchResult = (ArrayList<RSSItemModel>) searchEngine.searchAll("24");
        System.err.println(searchResult.get(0).getArticle());
        searchResult = (ArrayList<RSSItemModel>) searchEngine.searchAll("اسپورت");
        System.err.println(searchResult.get(0).getArticle());
    }
}

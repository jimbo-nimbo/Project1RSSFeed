package rssRepository;

import core.Core;
import core.Service;
import database.DatabaseConnectionPool;
import webSiteRepository.NewsWebPageModel;
import webSiteRepository.WebSiteRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RssItemRepository extends Service
{
    private static DatabaseConnectionPool databaseConnectionPool;
    private HashMap<Integer, RSSItemModel> linkRssModelMap;
    private static WebSiteRepository webSiteRepository;

    public RssItemRepository(Core core)
    {
        super(core);
        linkRssModelMap = new HashMap<>();
        databaseConnectionPool = core.getDatabaseConnectionPool();
        webSiteRepository = core.getWebSiteRepository();
        core.setRssRepository(this);
    }

    public RSSItemModel getRSSItem(String link)
    {
        try (Connection conn = core.getDatabaseConnectionPool().getConnection())
        {
            PreparedStatement preparedStatement =
                    conn.prepareStatement(RSSItemTableQueries.SELECT_RSS_ITEM_BY_LINK.toString());
            preparedStatement.setString(1, link);
            ResultSet resultSet = preparedStatement.executeQuery();

            return getRSSItem(resultSet.getInt("RID"));
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public RSSItemModel getRSSItem(int id)
    {
        if (linkRssModelMap.containsKey(id))
            return linkRssModelMap.get(id);
        else
        {
            try ( Connection conn  = core.getDatabaseConnectionPool().getConnection())
            {
                PreparedStatement preparedStatement =
                        conn.prepareStatement(RSSItemTableQueries.SELECT_RSS_ITEM_BY_ID.toString());
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.first();
                int wid = resultSet.getInt("WID");
                NewsWebPageModel newsWebPageModel = webSiteRepository.getWebsite(wid);
                RSSItemModel rssItemModel = new RSSItemModel(resultSet, newsWebPageModel);
                linkRssModelMap.put(id, rssItemModel);
                return rssItemModel;
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * only called from update method of NewsWebPageModel
     * @param rssItemModel generated object
     */
    public void addRSSItem(RSSItemModel rssItemModel)
    {
        if (rssItemModel.getId() != -1 && linkRssModelMap.containsKey(rssItemModel.getId()))
            return;

        linkRssModelMap.put(rssItemModel.getId(), rssItemModel);

        try (Connection conn = databaseConnectionPool.getConnection())
        {
            PreparedStatement preparedStatement =
                    conn.prepareStatement(RSSItemTableQueries.SELECT_RSS_ITEM_BY_LINK.toString());
            preparedStatement.setString(1, rssItemModel.getLink());
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.beforeFirst();
            if (!resultSet.next())
            {
                PreparedStatement addRSS =
                        conn.prepareStatement(RSSItemTableQueries.INSERT_INTO_RSS_ITEM_TABLE.toString());

                java.sql.Timestamp timestamp = new java.sql.Timestamp(rssItemModel.getDate().getTime());
                addRSS.setString(1, rssItemModel.getTitle());
                addRSS.setString(2, rssItemModel.getDescription());
                addRSS.setString(3, rssItemModel.getLink());
                addRSS.setTimestamp(4, timestamp);
                addRSS.setString(5, rssItemModel.getArticle());
                addRSS.setInt(6, rssItemModel.getNewsWebPageModel().getId());
                addRSS.execute();
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public List<RSSItemModel> getAllRSSData()
    {
        try (Connection conn = core.getDatabaseConnectionPool().getConnection())
        {
            PreparedStatement preparedStatement =
                    conn.prepareStatement(RSSItemTableQueries.SELECT_ALL_RSS_ITEMS.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.beforeFirst();
            ArrayList<RSSItemModel> ans = new ArrayList<>();
            while(resultSet.next())
            {
                ans.add(getRSSItem(resultSet.getInt("RID")));
            }
            return ans;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * return all rss for specified website
     * @param webPageLink
     * @return
     */
    public List<RSSItemModel> rssForWebsite(String webPageLink)
    {
        try (Connection conn = core.getDatabaseConnectionPool().getConnection())
        {
            NewsWebPageModel newsWebPageModel = webSiteRepository.getWebsite(webPageLink);
            PreparedStatement preparedStatement =
                    conn.prepareStatement(RSSItemTableQueries.SELECT_ALL_RSS_FROM_WEBSITE_BY_WID.toString());
            preparedStatement.setInt(1, newsWebPageModel.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.first();

            ArrayList<RSSItemModel> ans = new ArrayList<>();
            resultSet.beforeFirst();
            while (resultSet.next())
            {
                ans.add(getRSSItem(resultSet.getInt("RID")));
            }
            return ans;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }


    public List<RSSItemModel> convertResultSetToListOfRssModel(ResultSet resultSet)
    {
        try
        {
            resultSet.beforeFirst();
            List<RSSItemModel> ans = new ArrayList<>();
            while (resultSet.next())
            {
                ans.add(getRSSItem(resultSet.getInt("RID")));
            }
            return ans;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}

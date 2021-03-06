package dateEngine;

import core.Core;
import core.Service;
import rssRepository.RSSItemModel;
import rssRepository.RssItemRepository;
import webSiteRepository.NewsWebPageModel;
import webSiteRepository.WebSiteRepository;

import java.sql.*;
import java.util.List;

public class DateEngine extends Service
{
    private static final Long DAY_IN_MIL_SECOND = 3600 * 24 * 1000L;

    private WebSiteRepository webSiteRepository;
    private RssItemRepository rssItemRepository;

    public DateEngine(Core core)
    {
        super(core);
        core.setDateEngine(this);
        webSiteRepository = core.getWebSiteRepository();
        rssItemRepository = core.getRssRepository();
    }

    public List<RSSItemModel> getSomeLastRssForWebsite(String newsWebPage, int num) throws SQLException
    {
        try (Connection conn = core.getDatabaseConnectionPool().getConnection())
        {
            NewsWebPageModel newsWebPageModel = webSiteRepository.getWebsite(newsWebPage);
            PreparedStatement preparedStatement =
                    conn.prepareStatement(DateQueries.SELECT_SOME_LAST_RSS_ITEM_BY_WID.toString());
            preparedStatement.setInt(1, newsWebPageModel.getId());
            preparedStatement.setInt(2, num);
            ResultSet resultSet = preparedStatement.executeQuery();

            return rssItemRepository.convertResultSetToListOfRssModel(resultSet);
        } catch (SQLException e)
        {
            core.logToFile(e.getMessage());
            throw e;
        }
    }

    public List<RSSItemModel> getNewsForWebsiteByDate(String newsWebPage, Date date) throws SQLException
    {
        try (Connection conn = core.getDatabaseConnectionPool().getConnection())
        {
            NewsWebPageModel newsWebPageModel = webSiteRepository.getWebsite(newsWebPage);
            PreparedStatement preparedStatement =
                    conn.prepareStatement(DateQueries.SELECT_RSS_FROM_WEBSITE_BY_DATE.toString());
            preparedStatement.setInt(1, newsWebPageModel.getId());
            preparedStatement.setDate(2, date);
            ResultSet resultSet = preparedStatement.executeQuery();

            return rssItemRepository.convertResultSetToListOfRssModel(resultSet);
        } catch (SQLException e)
        {
            core.logToFile(e.getMessage());
            throw e;
        }
    }

    public int getNewsCountForDay(String webLink, int dayPast) throws SQLException
    {
        return getNewsForWebsiteByDate(webLink,
                new Date( System.currentTimeMillis() - DAY_IN_MIL_SECOND* dayPast)).size();
    }
}

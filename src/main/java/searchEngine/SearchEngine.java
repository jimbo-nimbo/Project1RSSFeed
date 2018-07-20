package searchEngine;

import core.Core;
import core.Service;
import rssRepository.RSSItemModel;
import rssRepository.RssItemRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SearchEngine extends Service
{
    private static RssItemRepository rssItemRepository;

    public SearchEngine(Core core)
    {
        super(core);
        rssItemRepository = core.getRssRepository();
        core.setSearchEngine(this);
    }

    public List<RSSItemModel> searchTitle(String context) throws SQLException
    {
        context = "%" + context + "%";
        try (Connection conn = core.getDatabaseConnectionPool().getConnection())
        {
            PreparedStatement preparedStatement =
                    conn.prepareStatement(SearchSqlQuery.SEARCH_FOR_TITLE_IN_RSSITEM.toString());
            preparedStatement.setString(1,context);
            ResultSet resultSet = preparedStatement.executeQuery();
            return rssItemRepository.convertResultSetToListOfRssModel(resultSet);
        } catch (SQLException e)
        {
            Core.getInstance().logToFile(e.getMessage());
            throw e;
        }
    }

    public List<RSSItemModel> searchArticle(String context) throws SQLException
    {
        context = "%" + context + "%";
        try (Connection conn = core.getDatabaseConnectionPool().getConnection())
        {
            PreparedStatement preparedStatement =
                    conn.prepareStatement(SearchSqlQuery.SEARCH_FOR_ARTICLE_IN_RSSITEM.toString());
            preparedStatement.setString(1, context);
            ResultSet resultSet = preparedStatement.executeQuery();
            return rssItemRepository.convertResultSetToListOfRssModel(resultSet);
        } catch (SQLException e)
        {
            Core.getInstance().logToFile(e.getMessage());
            throw e;
        }
    }

    public List<RSSItemModel> searchAll(String context) throws SQLException
    {
        context = "%" + context + "%";
        try (Connection conn = core.getDatabaseConnectionPool().getConnection())
        {
            PreparedStatement preparedStatement =
                    conn.prepareStatement(SearchSqlQuery.SEARCH_FOR_TITLE_OR_ARTICLE_IN_RSSITEM.toString());
            preparedStatement.setString(1, context);
            preparedStatement.setString(2, context);
            ResultSet resultSet = preparedStatement.executeQuery();
            return rssItemRepository.convertResultSetToListOfRssModel(resultSet);
        } catch (SQLException e)
        {
            Core.getInstance().logToFile(e.getMessage());
            throw e;
        }
    }
}

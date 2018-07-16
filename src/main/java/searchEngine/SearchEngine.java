package searchEngine;

import core.Core;
import database.DatabaseConnectionPool;
import rssRepository.RSSItemModel;
import rssRepository.RssItemRepository;

import java.sql.ResultSet;
import java.util.List;

public class SearchEngine {
    private static SearchEngine ourInstance = new SearchEngine();
    private static Core core = Core.getInstance();
    private static DatabaseConnectionPool databaseConnectionPool = core.getDatabaseConnectionPool();
    private static RssItemRepository rssItemRepository = core.getRssRepository();

    synchronized public static SearchEngine getInstance() {
        return ourInstance;
    }

    public List<RSSItemModel> searchTitle(String context) {
        context = "%" + context + "%";
        ResultSet resultSet =
                databaseConnectionPool.executeQuery(SearchSqlQuery.SEARCH_FOR_TITLE_IN_RSSITEM.toString(), context);
        return rssItemRepository.takeRssItemFromResultSetWithHashCheck(resultSet);
    }

    public List<RSSItemModel> searchArticle(String context) {
        context = "%" + context + "%";
        ResultSet resultSet =
                databaseConnectionPool.executeQuery(SearchSqlQuery.SEARCH_FOR_ARTICLE_IN_RSSITEM.toString(), context);
        return rssItemRepository.takeRssItemFromResultSetWithHashCheck(resultSet);
    }

    public List<RSSItemModel> searchAll(String context) {
        context = "%" + context + "%";
        ResultSet resultSet =
                databaseConnectionPool.executeQuery(
                        SearchSqlQuery.SEARCH_FOR_TITLE_OR_ARTICLE_IN_RSSITEM.toString(), context, context);
        return rssItemRepository.takeRssItemFromResultSetWithHashCheck(resultSet);
    }
}

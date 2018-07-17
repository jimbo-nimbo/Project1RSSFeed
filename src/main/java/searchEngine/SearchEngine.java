package searchEngine;

import core.Core;
import core.Service;
import database.DatabaseConnectionPool;
import rssRepository.RSSItemModel;
import rssRepository.RssItemRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SearchEngine extends Service {
  private static DatabaseConnectionPool databaseConnectionPool;
  private static RssItemRepository rssItemRepository;

  public SearchEngine(Core core) {
    super(core);
    databaseConnectionPool = core.getDatabaseConnectionPool();
    rssItemRepository = core.getRssRepository();
    core.setSearchEngine(this);
  }

  public List<RSSItemModel> searchTitle(String context) {
    context = "%" + context + "%";
    try (Connection conn = core.getDatabaseConnectionPool().getConnection()) {
      ResultSet resultSet =
          databaseConnectionPool.executeQuery(
              conn, SearchSqlQuery.SEARCH_FOR_TITLE_IN_RSSITEM.toString(), context);
      return rssItemRepository.takeRssItemFromResultSetWithHashCheck(resultSet);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<RSSItemModel> searchArticle(String context) {
    context = "%" + context + "%";
    try (Connection conn = core.getDatabaseConnectionPool().getConnection()) {
      ResultSet resultSet =
          databaseConnectionPool.executeQuery(
              conn, SearchSqlQuery.SEARCH_FOR_ARTICLE_IN_RSSITEM.toString(), context);
      return rssItemRepository.takeRssItemFromResultSetWithHashCheck(resultSet);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<RSSItemModel> searchAll(String context) {
    context = "%" + context + "%";
    try (Connection conn = core.getDatabaseConnectionPool().getConnection()) {
      ResultSet resultSet =
          databaseConnectionPool.executeQuery(
              conn,
              SearchSqlQuery.SEARCH_FOR_TITLE_OR_ARTICLE_IN_RSSITEM.toString(),
              context,
              context);
      return rssItemRepository.takeRssItemFromResultSetWithHashCheck(resultSet);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}

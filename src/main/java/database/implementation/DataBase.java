package database.implementation;

import RSSTable.enumaration.RSSItemTableQueries;
import RSSTable.interfaces.RSSItemRepository;
import RSSTable.model.RSSItemModel;
import database.enumaration.DataBaseCreationQueries;
import database.model.DataBaseConfig;
import dateEngine.enumaration.DateQueries;
import dateEngine.interfaces.DateQuery;
import searchEngine.enumarations.SearchSqlQuery;
import searchEngine.interfaces.SearchEngine;
import websiteTable.enumaration.WebsiteTableQueries;
import websiteTable.interfaces.WebSiteRepository;
import websiteTable.model.NewsWebPageInformation;
import websiteTable.model.NewsWebPageModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBase implements WebSiteRepository, RSSItemRepository, DateQuery, SearchEngine {
  private DataBaseThreadManager dataBaseThreadManager;
  /** Map for caching RSSItems and newsAgencies */
  private HashMap<String, NewsWebPageModel> webPageInformationHashMap;

  private HashMap<String, RSSItemModel> rssItemModelHashMap;

  /** create connection, so we can connect to database */
  private Connection conn;

  /** singleton pattern for database */
  private Object webSiteTableLock;

  private Object rssItemTableLock;

  private static DataBase dataBase;

  public static synchronized DataBase getInstance() {
    if (dataBase == null) {
      dataBase = new DataBase();
    }
    return dataBase;
  }

  private DataBase() {
    dataBaseThreadManager = new DataBaseThreadManager(this);
    webPageInformationHashMap = new HashMap<>();
    rssItemModelHashMap = new HashMap<>();
    webSiteTableLock = new Object();
    rssItemTableLock = new Object();
    DataBaseConfig configModel =
        new DataBaseConfig(DataBaseCreationQueries.DATABASE_CONFIG_PATH.toString());
    try {
      Class.forName(DataBaseCreationQueries.DATABASE_DRIVER_NAME.toString());
      conn =
          DriverManager.getConnection(
              DataBaseCreationQueries.DATABASE_TYPE.toString()
                  + configModel.getHostIP()
                  + ":"
                  + configModel.getHostPort()
                  + "?"
                  + DataBaseCreationQueries.DATABASE_CONFIG.toString(),
              configModel.getUsername(),
              configModel.getPassword());

      Statement statement = conn.createStatement();
      statement.execute(
          DataBaseCreationQueries.CREATE_DATABASE_IF_NOT_EXISTS.toString()
              + configModel.getDbName()
              + ";");
      statement.execute("USE " + configModel.getDbName() + ";");
      statement.execute(WebsiteTableQueries.CREATE_WEBSITE_TABLE_IF_NOT_EXISTS.toString());
      statement.execute(RSSItemTableQueries.CREATE_RSSITEM_TABLE_IF_NOT_EXISTS.toString());
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  public ResultSet executeQuery(String query, String... param) {
    ResultSet resultSet = null;
    try {
      PreparedStatement statement = conn.prepareStatement(query);
      for (int i = 0; i < param.length; i++) {
        statement.setString(i + 1, param[i]);
      }
      resultSet = statement.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return resultSet;
  }

  public void execute(String query, String... param) {
    ResultSet resultSet = null;
    try {
      PreparedStatement statement = conn.prepareStatement(query);
      for (int i = 0; i < param.length; i++) {
        statement.setString(i + 1, param[i]);
      }
      statement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void addWebSite(NewsWebPageModel newsWebPageModel) {
    try {
      synchronized (webSiteTableLock) {
        ResultSet resultSet =
            executeQuery(
                WebsiteTableQueries.SELECT_WEBSITE_BY_LINK.toString(), newsWebPageModel.getLink());

        resultSet.beforeFirst();
        if (!resultSet.next()) {
          execute(
              WebsiteTableQueries.INSERT_INTO_TABLE.toString(),
              newsWebPageModel.getLink(),
              newsWebPageModel.getTargetClass(),
              newsWebPageModel.getDatePattern());
        } else {
          execute(
              WebsiteTableQueries.UPDATE_TARGET_CLASS_BY_LINK.toString(),
              newsWebPageModel.getTargetClass(),
              newsWebPageModel.getDatePattern(),
              newsWebPageModel.getLink());
        }

        webPageInformationHashMap.put(newsWebPageModel.getLink(), newsWebPageModel);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public NewsWebPageModel getWebsite(String websiteLink) {
    if (webPageInformationHashMap.containsKey(websiteLink)) {
      return webPageInformationHashMap.get(websiteLink);
    } else {
      NewsWebPageModel newsWebPageModel = null;
      try {
        ResultSet resultSet =
            executeQuery(WebsiteTableQueries.SELECT_WEBSITE_BY_LINK.toString(), websiteLink);
        resultSet.first();
        newsWebPageModel = new NewsWebPageModel(resultSet, this);
        webPageInformationHashMap.put(websiteLink, newsWebPageModel);

      } catch (SQLException e) {
        e.printStackTrace();
      }
      return newsWebPageModel;
    }
  }

  @Override
  public List<NewsWebPageModel> getWebsites() {
    ArrayList<NewsWebPageModel> ans = null;
    try {
      ResultSet resultSet = executeQuery(WebsiteTableQueries.SELECT_ALL_WEBSITES.toString());
      ans = new ArrayList<>();
      resultSet.beforeFirst();
      while (resultSet.next()) {
        ans.add(getWebsite(resultSet.getString("url")));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ans;
  }

  @Override
  public void addRSSItem(RSSItemModel rssItemModel) {
    if (rssItemModelHashMap.containsKey(rssItemModel.getLink())) return;
    rssItemModelHashMap.put(rssItemModel.getLink(), rssItemModel);
    try {
      ResultSet resultSet =
          executeQuery(
              RSSItemTableQueries.SELECT_RSS_ITEM_BY_LINK.toString(), rssItemModel.getLink());

      resultSet.beforeFirst();
      if (!resultSet.next()) {
        PreparedStatement addRSS =
            conn.prepareStatement(RSSItemTableQueries.INSERT_INTO_RSS_ITEM_TABLE.toString());
        java.sql.Date date = new java.sql.Date(rssItemModel.getDate().getTime());
        addRSS.setString(1, rssItemModel.getTitle());
        addRSS.setString(2, rssItemModel.getDescription());
        addRSS.setString(3, rssItemModel.getLink());
        addRSS.setDate(4, date);
        addRSS.setString(5, rssItemModel.getArticle());
        addRSS.setString(6, rssItemModel.getNewsWebPage());
        addRSS.execute();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<RSSItemModel> getAllRSSData() {
    ArrayList<RSSItemModel> ans = new ArrayList<>();

    try {
      ResultSet resultSet = executeQuery(RSSItemTableQueries.SELECT_ALL_RSS_ITEMS.toString());

      resultSet.beforeFirst();
      while (resultSet.next()) {
        if (rssItemModelHashMap.containsKey(resultSet.getString("link"))) {
          ans.add(rssItemModelHashMap.get(resultSet.getString("link")));
        } else {
          NewsWebPageInformation webPageModel = getWebsite(resultSet.getString("newsWebPage"));

          RSSItemModel rssItemModel = new RSSItemModel(resultSet, webPageModel);
          ans.add(rssItemModel);
          rssItemModelHashMap.put(rssItemModel.getLink(), rssItemModel);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return ans;
  }

  @Override
  public List<RSSItemModel> getRSSDataFromWebSite(String webPageLink) {
    ArrayList<RSSItemModel> ans = new ArrayList<>();
    try {
      ResultSet resultSet =
          executeQuery(
              RSSItemTableQueries.SELECT_FROM_RSSITEM_BY_NEWSPAGE_LINK.toString(), webPageLink);

      NewsWebPageInformation webPageModel = getWebsite(resultSet.getString("newsWebPage"));
      resultSet.beforeFirst();
      while (resultSet.next()) {
        if (rssItemModelHashMap.containsKey(resultSet.getString("link"))) {
          ans.add(rssItemModelHashMap.get(resultSet.getString("link")));
        } else {
          RSSItemModel rssItemModel = new RSSItemModel(resultSet, webPageModel);
          ans.add(rssItemModel);
          rssItemModelHashMap.put(rssItemModel.getLink(), rssItemModel);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ans;
  }

  @Override
  public String getArticle(String link) {
    try {
      ResultSet resultSet =
          executeQuery(RSSItemTableQueries.SELECT_RSS_ITEM_BY_LINK.toString(), link);

      resultSet.beforeFirst();
      if (resultSet.next()) {
        return resultSet.getString("article");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public RSSItemModel getRSSItem(String link) {
    if (rssItemModelHashMap.containsKey(link)) {
      return rssItemModelHashMap.get(link);
    } else {
      RSSItemModel rssItemModel = null;
      try {
        ResultSet resultSet =
            executeQuery(RSSItemTableQueries.SELECT_RSS_ITEM_BY_LINK.toString(), link);
        rssItemModel = new RSSItemModel(resultSet, getWebsite(resultSet.getString("newsWebPage")));
        rssItemModelHashMap.put(link, rssItemModel);
      } catch (SQLException e) {
        e.printStackTrace();
      }
      return rssItemModel;
    }
  }

  @Override
  public List<RSSItemModel> getTenLastNewsForWebsite(String newsWebPage) {
    List<RSSItemModel> ans = new ArrayList<>();
    ResultSet resultSet =
        executeQuery(DateQueries.SELECT_TEN_LAST_RSS_ITEM_BY_WEBSITE.toString(), newsWebPage);
    return takeRssItemFromResultSetWithHashCheck(resultSet);
  }

  @Override
  public List<RSSItemModel> getNewsForWebsiteByDate(String newsWebPage, Date date) {
    List<RSSItemModel> ans = new ArrayList<>();
    ResultSet resultSet = null;
    try {
      PreparedStatement statement =
          conn.prepareStatement(DateQueries.SELECT_BY_DATE_NEWS_BY_WEBSITE.toString());
      statement.setString(1, newsWebPage);
      statement.setDate(2, date);
      resultSet = statement.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return takeRssItemFromResultSetWithHashCheck(resultSet);
  }

  @Override
  public List<RSSItemModel> searchTitle(String context) {
    context = "%" + context + "%";
    ResultSet resultSet =
        executeQuery(SearchSqlQuery.SEARCH_FOR_TITLE_IN_RSSITEM.toString(), context);
    return takeRssItemFromResultSetWithHashCheck(resultSet);
  }

  @Override
  public List<RSSItemModel> searchArticle(String context) {
    context = "%" + context + "%";
    ResultSet resultSet =
        executeQuery(SearchSqlQuery.SEARCH_FOR_ARTICLE_IN_RSSITEM.toString(), context);
    return takeRssItemFromResultSetWithHashCheck(resultSet);
  }

  @Override
  public List<RSSItemModel> searchAll(String context) {
    context = "%" + context + "%";
    ResultSet resultSet =
        executeQuery(
            SearchSqlQuery.SEARCH_FOR_TITLE_OR_ARTICLE_IN_RSSITEM.toString(), context, context);
    return takeRssItemFromResultSetWithHashCheck(resultSet);
  }

  private List<RSSItemModel> takeRssItemFromResultSetWithHashCheck(ResultSet resultSet) {
    List<RSSItemModel> ans = new ArrayList<>();
    try {
      resultSet.beforeFirst();
      while (resultSet.next()) {
        if (rssItemModelHashMap.containsKey(resultSet.getString("link"))) {
          ans.add(rssItemModelHashMap.get(resultSet.getString("link")));
        } else {
          RSSItemModel rssItemModel =
              new RSSItemModel(resultSet, getWebsite(resultSet.getString("newsWebPage")));
          rssItemModelHashMap.put(rssItemModel.getLink(), rssItemModel);
          ans.add(rssItemModel);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ans;
  }
}

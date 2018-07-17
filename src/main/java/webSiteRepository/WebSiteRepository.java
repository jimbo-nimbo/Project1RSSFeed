package webSiteRepository;

import core.Core;
import core.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WebSiteRepository extends Service {
  private HashMap<String, NewsWebPageModel> webPageInformationHashMap = new HashMap<>();

  public WebSiteRepository(Core core) {
    super(core);
    core.setWebSiteRepository(this);
  }

  public void addWebSite(NewsWebPageModel newsWebPageModel) {
    try (Connection conn = core.getDatabaseConnectionPool().getConnection()) {
      ResultSet resultSet =
          core.getDatabaseConnectionPool()
              .executeQuery(
                  conn,
                  WebsiteTableQueries.SELECT_WEBSITE_BY_LINK.toString(),
                  newsWebPageModel.getLink());
      resultSet.beforeFirst();
      if (!resultSet.next()) {
        core.getDatabaseConnectionPool()
            .execute(
                conn,
                WebsiteTableQueries.INSERT_INTO_TABLE.toString(),
                newsWebPageModel.getLink(),
                newsWebPageModel.getTargetClass(),
                newsWebPageModel.getDatePattern());
      } else {
        core.getDatabaseConnectionPool()
            .execute(
                conn,
                WebsiteTableQueries.UPDATE_TARGET_CLASS_BY_LINK.toString(),
                newsWebPageModel.getTargetClass(),
                newsWebPageModel.getDatePattern(),
                newsWebPageModel.getLink());
      }
      webPageInformationHashMap.put(newsWebPageModel.getLink(), newsWebPageModel);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public NewsWebPageModel getWebsite(String websiteLink) {
    if (webPageInformationHashMap.containsKey(websiteLink)) {
      return webPageInformationHashMap.get(websiteLink);
    } else {
      NewsWebPageModel newsWebPageModel = null;
      try (Connection conn = core.getDatabaseConnectionPool().getConnection()) {
        ResultSet resultSet =
            core.getDatabaseConnectionPool()
                .executeQuery(
                    conn, WebsiteTableQueries.SELECT_WEBSITE_BY_LINK.toString(), websiteLink);
        resultSet.first();
        newsWebPageModel = new NewsWebPageModel(resultSet);
        webPageInformationHashMap.put(websiteLink, newsWebPageModel);

      } catch (SQLException e) {
        e.printStackTrace();
      }
      return newsWebPageModel;
    }
  }

  public List<NewsWebPageModel> getWebsites() {
    ArrayList<NewsWebPageModel> ans = null;
    try (Connection conn = core.getDatabaseConnectionPool().getConnection()) {
      ResultSet resultSet =
          core.getDatabaseConnectionPool()
              .executeQuery(conn, WebsiteTableQueries.SELECT_ALL_WEBSITES.toString());
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
}

package database;

import models.NewsWebPageModel;
import models.RSSItemModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBaseService implements WebSiteRepository, RSSItemRepository, SearchEngineRepository
{
    private Connection connect;

    HashMap<String, NewsWebPageModel> webPageInformationHashMap;

    private static DataBaseService dataBaseService;
    public static DataBaseService getInstance()
    {
        if (dataBaseService == null)
        {
            dataBaseService = new DataBaseService();
        }
        return dataBaseService;
    }

    private DataBaseService()
    {
        ConfigModel configModel = new ConfigModel(DataBaseCreationQueries.DATABASE_CONFIG_PATH.toString());
        webPageInformationHashMap = new HashMap<>();
        try
        {
            Class.forName(DataBaseCreationQueries.DATABASE_DRIVER_NAME.toString());
            connect = DriverManager.getConnection(DataBaseCreationQueries.DATABASE_TYPE.toString() + configModel.getHostIP() + ":" +
                    configModel.getHostPort() +
                    "?" + DataBaseCreationQueries.DATABASE_CONFIG.toString(), configModel.getUsername(), configModel.getPassword());

            Statement statement = connect.createStatement();
            statement.execute( DataBaseCreationQueries.CREATE_DATABASE_IF_NOT_EXISTS.toString() + configModel.getDbName() + ";");
            statement.execute("USE " + configModel.getDbName() + ";");
            statement.execute(WebsiteTableQueries.CREATE_WEBSITE_TABLE_IF_NOT_EXISTS.toString() );
            statement.execute(RSSItemTableQueries.CREATE_RSSITEM_TABLE_IF_NOT_EXISTS.toString());
        } catch (ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public void addWebSite(NewsWebPageModel newsWebPageModel)
    {
        try
        {
            PreparedStatement statement = connect.prepareStatement(WebsiteTableQueries.SELECT_WEBSITE_BY_LINK.toString());
            statement.setString(1, newsWebPageModel.getLink());
            ResultSet resultSet = statement.executeQuery();

            resultSet.beforeFirst();
            if (!resultSet.next())
            {
                PreparedStatement statement1 = connect.prepareStatement(WebsiteTableQueries.INSERT_INTO_TABLE.toString());
                statement1.setString(1, newsWebPageModel.getLink());
                statement1.setString(2, newsWebPageModel.getTargetClass());
                statement1.execute();
            } else
            {
                PreparedStatement statement2 = connect.prepareStatement(WebsiteTableQueries.UPDATE_TARGET_CLASS_BY_LINK.toString());
                statement2.setString(1, newsWebPageModel.getTargetClass());
                statement2.setString(2, newsWebPageModel.getLink());
                statement2.execute();
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public List<NewsWebPageModel> getWebsites()
    {
        ArrayList<NewsWebPageModel> urls = null;
        try
        {
            PreparedStatement preparedStatement = connect.prepareStatement(WebsiteTableQueries.SELECT_ALL_WEBSITES.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            urls = new ArrayList<>();

            resultSet.beforeFirst();
            while (resultSet.next())
            {
                urls.add(new NewsWebPageModel(resultSet.getString("url"),
                        resultSet.getString("class"), this));
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return urls;
    }

    @Override
    public List<RSSItemModel> getAllRSSData()
    {
        ArrayList<RSSItemModel> ans = new ArrayList<>();

        try
        {
            PreparedStatement preparedStatement = connect.prepareStatement(RSSItemTableQueries.SELECT_ALL_RSS_ITEMS.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                String webPage = resultSet.getString("newsWebPage");
                NewsWebPageModel webPageModel = getWebsite(webPage);

                ans.add(new RSSItemModel(
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("link"),
                        resultSet.getString("pubDate"),
                        resultSet.getString("article"),
                        webPageModel));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return ans;
    }

    @Override
    public List<RSSItemModel> getRSSDataFromWebSite(String webPageLink)
    {
        ArrayList<RSSItemModel> ans = new ArrayList<>();

        try
        {
            PreparedStatement preparedStatement = connect.prepareStatement( RSSItemTableQueries.SELECT_FROM_RSSITEM_BY_NEWSPAGE_LINK.toString());
            preparedStatement.setString(1, webPageLink);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.first();
            String webPage = resultSet.getString("newsWebPage");
            NewsWebPageModel webPageModel = getWebsite(webPage);

            do
            {
                ans.add(new RSSItemModel(
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("link"),
                        resultSet.getString("pubDate"),
                        resultSet.getString("article"),
                        webPageModel));
            }while (resultSet.next());
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return ans;
    }

    @Override
    public NewsWebPageModel getWebsite(String websiteLink)
    {
        if (webPageInformationHashMap.containsKey(websiteLink))
        {
            return webPageInformationHashMap.get(websiteLink);
        } else
        {
            NewsWebPageModel newsWebPageModel = null;
            try
            {
                PreparedStatement selectWebsite = connect.prepareStatement(WebsiteTableQueries.SELECT_WEBSITE_BY_LINK.toString() );
                selectWebsite.setString(1, websiteLink);
                ResultSet resultSet = selectWebsite.executeQuery();
                resultSet.first();
                newsWebPageModel = new NewsWebPageModel(
                                resultSet.getString("url"),
                                resultSet.getString("class"),
                                this);
                webPageInformationHashMap.put(websiteLink, newsWebPageModel);

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            return newsWebPageModel;
        }

    }

    @Override
    public void addRSSItem(RSSItemModel rssItemModel)
    {
        try
        {
            PreparedStatement selectRSS = connect.prepareStatement( RSSItemTableQueries.SELECT_FROM_RSSITEM_BY_ARTICLE_LINK.toString());

            selectRSS.setString(1, rssItemModel.getLink());
            ResultSet resultSet = selectRSS.executeQuery();
            resultSet.beforeFirst();
            if (!resultSet.next())
            {
                PreparedStatement addRSS = connect.prepareStatement(RSSItemTableQueries.INSERT_INTO_RSSITEM_TABLE.toString());

                addRSS.setString(1, rssItemModel.getTitle());
                addRSS.setString(2, rssItemModel.getDescription());
                addRSS.setString(3, rssItemModel.getLink());
                addRSS.setString(4, rssItemModel.getPubDate());
                addRSS.setString(5, rssItemModel.getArticle());
                addRSS.setString(6, rssItemModel.getNewsWebPage());

                addRSS.execute();
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String getArticle(String link)
    {
        RSSItemModel rssItemModel;

        try
        {
            PreparedStatement preparedStatement = connect.prepareStatement(RSSItemTableQueries.SELECT_FROM_RSSITEM_BY_ARTICLE_LINK.toString());
            preparedStatement.setString(1, link);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.beforeFirst();
            if (resultSet.next())
            {
                return resultSet.getString("article");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet runQuery(String query, String... param) {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connect.prepareStatement(query);
            int paramIndex = 1;
            for(String parameter : param) {
                statement.setString(paramIndex, parameter);
                paramIndex++;
            }
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    @Override
    public void runExecute(String query, String... param) {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connect.prepareStatement(query);
            int paramIndex = 1;
            for(String parameter : param) {
                statement.setString(paramIndex, parameter);
                paramIndex++;
            }
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
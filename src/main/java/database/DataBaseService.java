package database;

import models.NewsWebPageModel;
import models.RSSItemModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBaseService implements WebSiteRepository, RSSItemRepository
{
    private static final String dataBaseConfig = "useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";
    private static final String checkDataBaseExist = "CREATE DATABASE IF NOT EXISTS ";
    private static final String checkTableWebSiteExist = "CREATE TABLE IF NOT EXISTS WebSite" +
            "(url varchar (300) PRIMARY KEY ," +
            " class varchar (200)) CHARACTER SET utf8mb4 COLLATE utf8mb4_persian_ci";
    private static final String checkTableRssItemExist = "CREATE TABLE IF NOT EXISTS RssItem" +
            "(title mediumtext," +
            " description mediumtext," +
            " link varchar(300) PRIMARY KEY ," +
            " pubDate mediumtext," +
            " article longtext," +
            " newsWebPage varchar(300)," +
            " FOREIGN KEY (newsWebPage) REFERENCES WebSite(url)) CHARACTER SET utf8mb4 COLLATE utf8mb4_persian_ci";
    private static final String checkUrlExistInWebSiteTable = "SELECT * FROM WebSite WHERE url LIKE ?";
    private static final String addUrlToWebSiteTable = "INSERT INTO WebSite (url, class) VALUES (?,?)";
    private static final String updateUrlClassTagWebSiteTable = "UPDATE WebSite SET class=? WHERE url=?";
    private static final String getAllWebSitesFromWebSiteTable = "SELECT * FROM WebSite";
    private static final String selectWebSitesFromWebSiteTable = "SELECT * FROM WebSite WHERE url LIKE ?";
    private static final String addRSSItemToRSSItemsTable = "INSERT  INTO RssItem " +
            "(title, description, link, pubDate, article, newsWebPage) VALUES " +
            "(?, ?, ?, ?, ?, (SELECT (url) FROM WebSite WHERE url LIKE ?))";
    private static final String selectRssItemFromRssTableByWebPage = "SELECT * FROM RssItem WHERE newsWebPage LIKE ?";
    private static final String selectRssByLink = "SELECT * FROM RssItem WHERE link LIKE ?";
    private static final String selectAllRss = "SELECT * FROM RssItem";

    private Connection connect;

    HashMap<String, NewsWebPageModel> webPageInformationHashMap;

    DataBaseService()
    {
        ConfigModel configModel = new ConfigModel("src/main/resources/config.properties");
        webPageInformationHashMap = new HashMap<>();
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://" + configModel.getHostIP() + ":" +
                    configModel.getHostPort() +
                    "?" + dataBaseConfig, configModel.getUsername(), configModel.getPassword());

            Statement statement = connect.createStatement();
            statement.execute(checkDataBaseExist + configModel.getDbName() + ";");
            statement.execute("USE " + configModel.getDbName() + ";");
            statement.execute(checkTableWebSiteExist + ";");
            statement.execute(checkTableRssItemExist + ";");
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
            PreparedStatement statement = connect.prepareStatement(checkUrlExistInWebSiteTable + ";");
            statement.setString(1, newsWebPageModel.getLink());
            ResultSet resultSet = statement.executeQuery();

            resultSet.beforeFirst();
            if (!resultSet.next())
            {
                PreparedStatement statement1 = connect.prepareStatement(addUrlToWebSiteTable + ";");
                statement1.setString(1, newsWebPageModel.getLink());
                statement1.setString(2, newsWebPageModel.getTargetClass());
                statement1.execute();
            } else
            {
                PreparedStatement statement2 = connect.prepareStatement(updateUrlClassTagWebSiteTable + ";");
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
            PreparedStatement preparedStatement = connect.prepareStatement(getAllWebSitesFromWebSiteTable + ";");
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
            PreparedStatement preparedStatement = connect.prepareStatement(selectAllRss + ";");
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
            PreparedStatement preparedStatement = connect.prepareStatement( selectRssItemFromRssTableByWebPage + ";");
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
                PreparedStatement selectWebsite = connect.prepareStatement(selectWebSitesFromWebSiteTable + ";");
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
            PreparedStatement selectRSS = connect.prepareStatement(selectRssItemFromRssTableByWebPage + ";");

            selectRSS.setString(1, rssItemModel.getLink());
            ResultSet resultSet = selectRSS.executeQuery();
            resultSet.beforeFirst();
            if (!resultSet.next())
            {
                PreparedStatement addRSS = connect.prepareStatement(addRSSItemToRSSItemsTable + ";");

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
            PreparedStatement preparedStatement = connect.prepareStatement(selectRssByLink + ";");
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
}
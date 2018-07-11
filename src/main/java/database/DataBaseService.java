package database;

import models.NewsWebPageModel;
import models.RSSItemModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseService implements WebSiteRepository, RSSItemRepository
{
    private static final String dataBaseConfig = "useSSL=false&serverTimezone=UTC";
    private static final String checkDataBaseExist = "CREATE DATABASE IF NOT EXISTS ";
    private static final String checkTableWebSiteExist = "CREATE TABLE IF NOT EXISTS WebSite" +
            "(url varchar (200) PRIMARY KEY ," +
            " class varchar (200))";
    private static final String checkTableRssItemExist = "CREATE TABLE IF NOT EXISTS RssItem" +
            "(title mediumtext," +
            " description mediumtext," +
            " link varchar(1000) PRIMARY KEY ," +
            " pubDate mediumtext," +
            " newsWebPage varchar(200)," +
            " FOREIGN KEY (newsWebPage) REFERENCES WebSite(url))";
    private static final String checkUrlExistInWebSiteTable = "SELECT * FROM WebSite WHERE url LIKE ?";
    private static final String addUrlToWebSiteTable = "INSERT INTO WebSite (url, class) VALUES (?,?)";
    private static final String updateUrlClassTagWebSiteTable = "UPDATE WebSite SET class=? WHERE url=?";
    private static final String getWebSitesFromWebSiteTable = "SELECT * FROM WebSite";
    private static final String addRSSItemToRSSItemsTable = "INSERT  INTO RssItem " +
            "(title, description, link, pubDate, newsWebPage) VALUES " +
            "(?, ?, ?, ?, ?)";



    private Connection connect;


    DataBaseService()
    {
        ConfigModel configModel = new ConfigModel("src/main/resources/config.properties");
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://" + configModel.getHostIP() + ":" +
                    configModel.getHostPort() +
                    "?" + dataBaseConfig ,configModel.getUsername(), configModel.getPassword());

            Statement statement = connect.createStatement();
            statement.execute(checkDataBaseExist + configModel.getDbName() + ";");
            statement.execute("USE " + configModel.getDbName() + ";");
            statement.execute(checkTableWebSiteExist + ";");
            statement.execute(checkTableRssItemExist + ";");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addWebSite(NewsWebPageModel newsWebPageModel) {
        try {
            PreparedStatement statement = connect.prepareStatement(checkUrlExistInWebSiteTable + ";");
            statement.setString(1,newsWebPageModel.getLink());
            ResultSet resultSet = statement.executeQuery();

            resultSet.beforeFirst();
            if (!resultSet.next())
            {
                PreparedStatement statement1 = connect.prepareStatement(addUrlToWebSiteTable + ";");
                statement1.setString(1, newsWebPageModel.getLink());
                statement1.setString(2,newsWebPageModel.getTargetClass());
                statement1.execute();
            } else {
                PreparedStatement statement2 = connect.prepareStatement( updateUrlClassTagWebSiteTable + ";");
                statement2.setString(1, newsWebPageModel.getTargetClass());
                statement2.setString(2, newsWebPageModel.getLink());
                statement2.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<NewsWebPageModel> getWebsites()
    {
        ArrayList<NewsWebPageModel> urls = null;
        try
        {
            PreparedStatement preparedStatement = connect.prepareStatement(getWebSitesFromWebSiteTable + ";");
            ResultSet resultSet = preparedStatement.executeQuery();

            urls = new ArrayList<>();

            resultSet.beforeFirst();
            while(resultSet.next())
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
    public void addRSSItem(RSSItemModel rssItemModel)
    {
        try
        {
            PreparedStatement preparedStatement = connect.prepareStatement(addRSSItemToRSSItemsTable + ";");

            preparedStatement.setString(1, rssItemModel.getTitle());
            preparedStatement.setString(2, rssItemModel.getDescription());
            preparedStatement.setString(3, rssItemModel.getLink());
            preparedStatement.setString(4, rssItemModel.getPubDate());
            preparedStatement.setString(5, rssItemModel.getLink());

            preparedStatement.execute();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
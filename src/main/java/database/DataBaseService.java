package database;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import models.NewsWebPageModel;

import java.sql.*;

public class DataBaseService implements WebSiteRepository
{
    private static final String dataBaseConfig = "useSSL=false&serverTimezone=UTC";
    private static final String checkDataBaseExist = "CREATE DATABASE IF NOT EXISTS ";
    private static final String checkTableWebSiteExist = "CREATE TABLE IF NOT EXISTS WebSite(url varchar(120), class varchar(120))";
    private static final String checkUrlExistInWebSiteTable = "SELECT * FROM WebSite WHERE url LIKE ?";
    private static final String addUrlToWebSiteTable = "INSERT INTO WebSite (url, class) VALUES (?,?)";
    private static final String updateUrlClassTagWebSiteTable = "UPDATE WebSite SET class=? WHERE url=?";
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
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addWebSite(NewsWebPageModel newsWebPageModel) {
        try {
            PreparedStatement statement = connect.prepareStatement(checkUrlExistInWebSiteTable + ";");
            statement.setString(1,newsWebPageModel.getUrl());
            ResultSet resultSet = statement.executeQuery();

            resultSet.beforeFirst();
            if (!resultSet.next())
            {
                PreparedStatement statement1 = connect.prepareStatement(addUrlToWebSiteTable + ";");
                statement1.setString(1, newsWebPageModel.getUrl());
                statement1.setString(2,newsWebPageModel.getTargetClass());
                statement1.execute();
            } else {
                PreparedStatement statement2 = connect.prepareStatement( updateUrlClassTagWebSiteTable + ";");
                statement2.setString(1, newsWebPageModel.getTargetClass());
                statement2.setString(2, newsWebPageModel.getUrl());
                statement2.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
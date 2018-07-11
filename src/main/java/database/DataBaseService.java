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
            resultSet.first();
            if(resultSet == null)
                System.err.println("hi");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
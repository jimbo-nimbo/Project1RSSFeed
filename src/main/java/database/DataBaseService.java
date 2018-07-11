package database;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import models.NewsWebPageModel;

import java.sql.*;

public class DataBaseService implements WebSiteRepository
{
    private static final String dataBaseConfig = "useSSL=false&serverTimezone=UTC";
    private static final String checkDataBaseExist = "CREATE DATABASE IF NOT EXISTS ";
    DataBaseService()
    {
        ConfigModel configModel = new ConfigModel("src/main/resources/config.properties");
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connect = DriverManager.getConnection("jdbc:mysql://" + configModel.getHostIP() + ":" +
                    configModel.getHostPort() +
                    "?" + dataBaseConfig ,configModel.getUsername(), configModel.getPassword());

            Statement statement = connect.createStatement();
            statement.execute(checkDataBaseExist + configModel.getDbName() + ";");
            ResultSet resultset = statement.executeQuery("show databases;");

            while (resultset.next()) {
                System.out.println(resultset.getString("Database"));
            }



        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addWebSite(NewsWebPageModel newsWebPageModel) {

    }
}
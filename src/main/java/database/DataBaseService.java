package database;

import java.sql.*;

public class DataBaseService implements WebSiteRepository
{

    DataBaseService()
    {
        ConfigModel configModel = new ConfigModel("src/main/resources/config.properties");
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connect = DriverManager.getConnection("jdbc:mysql://" + configModel.getHostIP() + ":" +
                    configModel.getHostPort() + "/" + configModel.getDbName() +
                    "?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&"
                            + "user="+ configModel.getUsername()+ "&" + "password=" + configModel.getPassword());

            Statement statement = connect.createStatement();
            ResultSet resultset = statement.executeQuery("SHOW databases;");

            if (statement.execute("SHOW databases;")) {
                resultset = statement.getResultSet();
            }

            while (resultset.next()) {
                System.out.println(resultset.getString("Database"));
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addWebSite(String website, String keyword)
    {
        // TODO: 7/10/18
    }
}
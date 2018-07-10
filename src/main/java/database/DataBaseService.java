package database;

import java.sql.*;

public class DataBaseService {
    private ConfigModel configModel;
    private static DataBaseService dataBaseService;

    private DataBaseService() {
        //configModel = new ConfigReader().readConfig();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            // Setup the connection with the DB
            Connection connect = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/nimac?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&"
                            + "user=nimbo&password=nimbo");
            Statement statement = connect.createStatement();
            // Result set get the result of the SQL query

            ResultSet resultset = statement.executeQuery("SHOW tables;");

            if (statement.execute("SHOW tables;")) {
                resultset = statement.getResultSet();
            }

            while (resultset.next()) {
                System.out.println(resultset.getString(1));
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        database.DataBaseService.getInstance();
    }

    public static database.DataBaseService getInstance() {
        if (dataBaseService == null) {
            dataBaseService = new database.DataBaseService();
        }
        return dataBaseService;
    }
}
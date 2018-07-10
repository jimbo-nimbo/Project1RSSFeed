package database;

import java.sql.*;

public class DataBaseService {

    private static final String HOST_IP = "127.0.0.1";
    private static DataBaseService dataBaseService;
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;


    private DataBaseService(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/feedback?"
                            + "user=sqluser&password=sqluserpw");
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("select * from feedback.comments");
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static DataBaseService getInstance (){
        if (dataBaseService == null){
            dataBaseService = new DataBaseService();
        }
        return dataBaseService;
    }






}

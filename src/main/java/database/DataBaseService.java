package database;
import java.sql.*;

public class DataBaseService
{
    private ConfigModel configModel;
    private static DataBaseService dataBaseService;

    private DataBaseService()
    {
        configModel = new ConfigReader().readConfig();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            Connection connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/feedback?"
                            + "user=sqluser&password=sqluserpw");
            Statement statement = connect.createStatement();
            // Result set get the result of the SQL query
            ResultSet resultSet = statement.executeQuery("select * from feedback.comments");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args)
    {
        DataBaseService.getInstance();
    }

    public static DataBaseService getInstance (){
        if (dataBaseService == null){
            dataBaseService = new DataBaseService();
        }
        return dataBaseService;
    }

}

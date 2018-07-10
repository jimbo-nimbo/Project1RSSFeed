package database;
import java.sql.*;

public class DataBaseService
{
    private ConfigModel configModel;
    private static DataBaseService dataBaseService;

//    private DataBaseService()
//    {
//        configModel = new ConfigReader().readConfig();
//        try {
//            Class.forName(className);
//        } catch (ClassNotFoundException ex) {
//            System.out.println("Unable to load the class. Terminating the program");
//            System.exit(-1);
//        }
//        //get the connection
//        try {
//            connection = DriverManager.getConnection(URL, user, password);
//        } catch (SQLException ex) {
//            System.out.println("Error getting connection: " + ex.getMessage());
//            System.exit(-1);
//        } catch (Exception ex) {
//            System.out.println("Error: " + ex.getMessage());
//            System.exit(-1);
//        }
//
//    }

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

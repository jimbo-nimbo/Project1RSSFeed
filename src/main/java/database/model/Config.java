package database.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config
{
    private String hostIP;
    private String hostPort;
    private String dbName;
    private String username;
    private String password;

    public Config(String url)
    {
        Properties prop = new Properties();
        InputStream input = null;
        try
        {
            input = new FileInputStream(url);
            prop.load(input);

            hostIP = prop.getProperty("HOST_IP").trim();
            hostPort = prop.getProperty("HOST_PORT").trim();
            dbName = prop.getProperty("DB_NAME").trim();
            username = prop.getProperty("DB_USERNAME").trim();
            password = prop.getProperty("DB_PASSWORD").trim();

        } catch (IOException ex)
        {
            ex.printStackTrace();
        } finally
        {
            if (input != null)
            {
                try
                {
                    input.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

    }

     public String getHostIP()
    {
        return hostIP;
    }

     public String getHostPort()
    {
        return hostPort;
    }

     public String getDbName()
    {
        return dbName;
    }

     public String getUsername()
    {
        return username;
    }

     public String getPassword()
    {
        return password;
    }

}

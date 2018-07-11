package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigModel
{
    private String hostIP;
    private String hostPort;
    private String dbName;
    private String username;
    private String password;

    ConfigModel(String url)
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

    public ConfigModel(String hostIP, String hostPort, String dbName, String username, String password)
    {
        this.hostIP = hostIP;
        this.hostPort = hostPort;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
    }

     String getHostIP()
    {
        return hostIP;
    }

     String getHostPort()
    {
        return hostPort;
    }

     String getDbName()
    {
        return dbName;
    }

     String getUsername()
    {
        return username;
    }

     String getPassword()
    {
        return password;
    }

    public void setHostIP(String hostIP)
    {
        this.hostIP = hostIP;
    }

    public void setHostPort(String hostPort)
    {
        this.hostPort = hostPort;
    }

    public void setDbName(String dbName)
    {
        this.dbName = dbName;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}

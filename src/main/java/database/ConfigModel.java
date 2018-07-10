package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigModel
{
    private String hostIP;
    private String hostPort;
    private String dbName;
    private String username;
    private String password;

    public ConfigModel(String url)
    {
        Properties prop = new Properties();
        InputStream input = null;
        try
        {
            input = new FileInputStream(url);
            prop.load(input);

            hostIP = prop.getProperty("HOST_IP");
            hostPort = prop.getProperty("HOST_PORT");
            dbName = prop.getProperty("DB_NAME");
            username = prop.getProperty("DB_USERNAME");
            password = prop.getProperty("DB_PASSWORD");

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

    public static void main(String[] args)
    {
        new ConfigModel("src/main/resources/config.properties");
    }

    public ConfigModel(String hostIP, String hostPort, String dbName, String username, String password)
    {
        this.hostIP = hostIP;
        this.hostPort = hostPort;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
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

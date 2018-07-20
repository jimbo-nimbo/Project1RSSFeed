package database;

import core.Core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataBaseConfig
{
    private String hostIP;
    private String hostPort;
    private String dbName;
    private String username;
    private String password;

    public DataBaseConfig(String url) throws IOException
    {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(url))
        {
            prop.load(input);

            hostIP = prop.getProperty("HOST_IP").trim();
            hostPort = prop.getProperty("HOST_PORT").trim();
            dbName = prop.getProperty("DB_NAME").trim();
            username = prop.getProperty("DB_USERNAME").trim();
            password = prop.getProperty("DB_PASSWORD").trim();

        } catch (IOException ex)
        {
            Core.getInstance().logToFile(ex.getMessage());
            throw new IOException("cannot read the config file of database (config.properties)");
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

package database;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ConfigModel
{
    private String hostIP;
    private String hostPort;
    private String dbName;
    private String username;
    private String password;

    public ConfigModel(String hostIP, String hostPort, String dbName, String username, String password)
    {
        this.hostIP = hostIP;
        this.hostPort = hostPort;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
    }

    public ConfigModel(LinkedHashMap<String , String> map)
    {

        for (HashMap.Entry<String, String> entry: map.entrySet())
        {
            String conf = entry.getKey();
            String val = entry.getValue();
            System.out.println(conf);
            switch (conf)
            {
                case "HOST_IP":
                    hostIP = val;
                    break;
                case "HOST_PORT":
                    hostPort = val;
                    break;
                case "DB_NAME":
                    dbName = val;
                    break;
                case "DB_USERNAME":
                    username = val;
                    break;
                case "DB_PASSWORD":
                    password = val;
                    break;
                    default:
                        System.err.println("Invalid property in config.properties:" + conf + "!");
                        break;
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

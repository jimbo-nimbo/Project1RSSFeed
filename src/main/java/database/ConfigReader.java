package database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.NavigableMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigReader
{
    private static final String CONFIG_ADD = "/main/resources/config.properties";

    public ConfigModel readConfig()
    {
        final LinkedHashMap<String, String > configMap = new LinkedHashMap<>();
        Path path = Paths.get("src/main/resources/config.properties");
        try
        {
            Files.lines(path).forEach(line -> {
                String configPattern = "(\\S*) *: *(\\S*);";
                Matcher matcher = Pattern.compile(configPattern).matcher(line);
                if (matcher.find()) {
                    String conf = matcher.group(1);
                    String val = matcher.group(2);
                    configMap.put(conf, val);
                }
            });

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return new ConfigModel(configMap);
    }

    public static void main(String[] args)
    {
        ConfigModel configModel = new ConfigReader().readConfig();
        System.out.println(configModel.getDbName());
        System.out.println(configModel.getHostIP());
        System.out.println(configModel.getHostPort());
        System.out.println(configModel.getUsername());
        System.out.println(configModel.getPassword());
    }
}

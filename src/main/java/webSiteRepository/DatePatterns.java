package webSiteRepository;

import core.Core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

class DatePatterns
{
	//private static final String DATABASE_CONFIG_PATH = "src/main/resources/date_patterns.properties";
	private static final String DATABASE_CONFIG_NAME = "date_patterns.properties";
	private List<String> patterns;

	DatePatterns() throws IOException
	{
		Properties properties = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try(InputStream inputStream = loader.getResourceAsStream(DATABASE_CONFIG_NAME))
		{
			properties.load(inputStream);
			String[] patterns = properties.getProperty("patterns").split("#");
			this.patterns = new ArrayList<>(Arrays.asList(patterns));
		} catch (IOException e)
		{
			Core.getInstance().logToFile(e.getMessage());
			throw new IOException("cannot read the config file of date patterns (date_patterns.properties");
		}
	}

	List<String > getPatterns()
	{
		return patterns;
	}
}

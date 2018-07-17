package webSiteRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class DatePatterns
{
	private static final String DATABASE_CONFIG_PATH = "src/main/resources/date_patterns.properties";
	private List<String> patterns;

	public DatePatterns()
	{
		Properties properties = new Properties();
		try(InputStream inputStream = new FileInputStream(DATABASE_CONFIG_PATH))
		{
			properties.load(inputStream);
			String[] patterns = properties.getProperty("patterns").split("#");
			this.patterns = new ArrayList<>(Arrays.asList(patterns));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public List<String > getPatterns()
	{
		return patterns;
	}
}
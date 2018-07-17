package core;

import cli.RssService;
import database.DatabaseConnectionPool;
import dateEngine.DateEngine;
import rssRepository.RssItemRepository;
import searchEngine.SearchEngine;
import webSiteRepository.WebSiteRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Core
{
	private static Core core;
	private DatabaseConnectionPool databaseConnectionPool;
	private RssItemRepository rssRepository;
	private WebSiteRepository webSiteRepository;
	private SearchEngine searchEngine;
	private DateEngine dateEngine;
	private RssService rssService;
	Logger logger = Logger.getLogger("MyLog");
	FileHandler fileHandler;
	Path path = Paths.get("logFile.log");


	private Core()
	{
		try {
			fileHandler = new FileHandler("LogFile.log");
			logger.addHandler(fileHandler);
			SimpleFormatter formatter = new SimpleFormatter();
			fileHandler.setFormatter(formatter);
			logger.setUseParentHandlers(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		databaseConnectionPool = new DatabaseConnectionPool(this);
		rssRepository = new RssItemRepository(this);
		webSiteRepository = new WebSiteRepository(this);
		searchEngine = new SearchEngine(this);
		dateEngine = new DateEngine(this);
		rssService = new RssService(this);
	}

	public synchronized static Core getInstance()
	{
		if (core == null)
			core = new Core();
		return core;
	}

	public RssItemRepository getRssRepository() {
		return rssRepository;
	}

	public WebSiteRepository getWebSiteRepository() {
		return webSiteRepository;
	}

	public SearchEngine getSearchEngine() {
		return searchEngine;
	}

	public DatabaseConnectionPool getDatabaseConnectionPool()
	{
		return databaseConnectionPool;
	}

	public void setDatabaseConnectionPool(DatabaseConnectionPool databaseConnectionPool)
	{
		this.databaseConnectionPool = databaseConnectionPool;
	}

	public void setRssRepository(RssItemRepository rssRepository)
	{
		this.rssRepository = rssRepository;
	}

	public void setWebSiteRepository(WebSiteRepository webSiteRepository)
	{
		this.webSiteRepository = webSiteRepository;
	}

	public void setSearchEngine(SearchEngine searchEngine)
	{
		this.searchEngine = searchEngine;
	}

	public void setDateEngine(DateEngine dateEngine)
	{
		this.dateEngine = dateEngine;
	}

	public RssService getRssService()
	{
		return rssService;
	}

	public void setRssService(RssService rssService)
	{
		this.rssService = rssService;
	}

	public DateEngine getDateEngine()
	{
		return dateEngine;
	}

	synchronized public void logToFile(String s){
		try{
			logger.info(s);
		} catch (Exception e){
			//
		}
	}
}

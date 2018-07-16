package core;

import database.DatabaseConnectionPool;
import dateEngine.DateQueries;
import rssRepository.RssItemRepository;
import searchEngine.SearchEngine;
import webSiteRepository.WebSiteRepository;

public class Core
{
	private static Core core;
	private DatabaseConnectionPool databaseConnectionPool;
	private RssItemRepository rssRepository;
	private WebSiteRepository webSiteRepository;
	private SearchEngine searchEngine;
	private DateQueries dateQueries;

	private Core()
	{
		databaseConnectionPool = DatabaseConnectionPool.getInstance();
		rssRepository = RssItemRepository.getInstance();
		webSiteRepository = WebSiteRepository.getInstance();
		searchEngine = SearchEngine.getInstance();
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
}

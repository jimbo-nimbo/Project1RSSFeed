package core;

import cli.RssService;
import database.DatabaseConnectionPool;
import dateEngine.DateEngine;
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
	private DateEngine dateEngine;
	private RssService rssService;

	private Core()
	{
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
}

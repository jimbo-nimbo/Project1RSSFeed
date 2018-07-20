package core;

import cli.Cli;
import cli.RssService;
import database.DatabaseConnectionPool;
import dateEngine.DateEngine;
import org.slf4j.LoggerFactory;
import rssRepository.RssItemRepository;
import searchEngine.SearchEngine;
import webSiteRepository.WebSiteRepository;

import java.io.IOException;
import java.sql.SQLException;

public class Core
{
    private static Core core;
    private DatabaseConnectionPool databaseConnectionPool;
    private RssItemRepository rssRepository;
    private WebSiteRepository webSiteRepository;
    private SearchEngine searchEngine;
    private DateEngine dateEngine;
    private RssService rssService;
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(Cli.class);

    private Core()
    {

        try
        {
            databaseConnectionPool = new DatabaseConnectionPool(this);
        } catch (SQLException e)
        {
            logToFile(e.getMessage());
            System.out.println("cannot connect to database!\n" + e.getMessage() + "\n");
        } catch (IOException e)
        {
            logToFile(e.getMessage());
            System.out.println("cannot read the config file!\n" + e.getMessage() + "\n");
        }

        webSiteRepository = new WebSiteRepository(this);
        rssRepository = new RssItemRepository(this);
        searchEngine = new SearchEngine(this);
        dateEngine = new DateEngine(this);
        rssService = new RssService(this);
    }

    public static synchronized Core getInstance()
    {
        if (core == null) core = new Core();
        return core;
    }

    public RssItemRepository getRssRepository()
    {
        return rssRepository;
    }

    public WebSiteRepository getWebSiteRepository()
    {
        return webSiteRepository;
    }

    public SearchEngine getSearchEngine()
    {
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

    public synchronized void logToFile(String s)
    {
        try
        {
            logger.debug(s);
        } catch (Exception e)
        {
            //
        }
    }
}

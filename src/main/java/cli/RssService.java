package cli;

import core.Core;
import core.Service;
import dateEngine.DateEngine;
import rssRepository.RSSItemModel;
import rssRepository.RssItemRepository;
import searchEngine.SearchEngine;
import webSiteRepository.NewsWebPageModel;
import webSiteRepository.WebSiteRepository;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class RssService extends Service
{

    private static final int THREAD_NUM = 20;
    private ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUM);

    private WebSiteRepository webSiteRepository;
    private RssItemRepository rssItemRepository;
    private DateEngine dateEngine;
    private SearchEngine searchEngine;

    public RssService(Core core)
    {
        super(core);
        webSiteRepository = core.getWebSiteRepository();
        rssItemRepository = core.getRssRepository();
        dateEngine = core.getDateEngine();
        searchEngine = core.getSearchEngine();

        ScheduledExecutorService scheduledExecutorService =
                Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() ->{
            try
            {
                for (NewsWebPageModel newsWebPageModel: webSiteRepository.getWebsites())
                    newsWebPageModel.update();
            } catch (SQLException | ParseException | IOException e)
            {
                Core.getInstance().logToFile(e.getMessage());
            }
        }, 0, 100, TimeUnit.SECONDS);
    }

    public CompletableFuture<Boolean> addWebSite(String websiteLink, String targetClass)
    {
        return CompletableFuture.supplyAsync(() -> {
            try
            {
                webSiteRepository.addWebSite(websiteLink, targetClass);
                return true;
            } catch (SQLException | IOException | ParseException e)
            {
                System.out.println(e.getMessage());
                return false;
            }
        }, executor);
    }

    public CompletableFuture<List<NewsWebPageModel>> getWebSites()
    {
        return CompletableFuture.supplyAsync(() -> {
                    try
                    {
                        return webSiteRepository.getWebsites();
                    } catch (SQLException e)
                    {
                        System.out.println(e.getMessage());
                        return new ArrayList<>();
                    }
                },
                executor);
    }

    public CompletableFuture<Void> updateWebsite(String webSiteLink) throws SQLException
    {
    return CompletableFuture.runAsync(
        () -> {
          try {
            if (webSiteRepository.getWebsite(webSiteLink) != null) {
              webSiteRepository.getWebsite(webSiteLink).update();
            }else {
                throw new SQLException("this site isnt in dataBase");
            }

          } catch (SQLException | IOException | ParseException e) {
            System.out.println(e.getMessage());
          }
        },
        executor);
    }

    public List<CompletableFuture<Void>> updateAllWebsites()
    {
        List<CompletableFuture<Void>> list = new ArrayList<>();
        try
        {
            for (NewsWebPageModel newsWebPageModel : webSiteRepository.getWebsites())
                list.add(updateWebsite(newsWebPageModel.getLink()));
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public CompletableFuture<List<RSSItemModel>> getWebSiteRssData(String webPageLink)
    {
        return CompletableFuture.supplyAsync(() -> {
            try
            {
                return rssItemRepository.rssForWebsite(webPageLink);
            } catch (SQLException e)
            {
                System.out.println(e.getMessage());
                return new ArrayList<>();
            }
        }, executor);
    }

    public CompletableFuture<List<RSSItemModel>> getAllRssData()
    {
        return CompletableFuture.supplyAsync(() -> {
            try
            {
                return rssItemRepository.getAllRSSData();
            } catch (SQLException e)
            {
                System.out.println(e.getMessage());
                return new ArrayList<>();
            }
        }, executor);
    }

    public CompletableFuture<List<RSSItemModel>> getTodayNewsForWebsite(String link)
    {
        return CompletableFuture.supplyAsync(() ->
        {
            try
            {
                return dateEngine.getNewsForWebsiteByDate(link, new Date(System.currentTimeMillis()));
            } catch (SQLException e)
            {
                System.out.println(e.getMessage());
                return new ArrayList<>();
            }
        },executor);
    }

    public CompletableFuture<List<RSSItemModel>> getSomeLastNewsForWebsite(String link, int num)
    {
        return CompletableFuture.supplyAsync(() ->
        {
            try
            {
                return dateEngine.getSomeLastRssForWebsite(link, num);
            } catch (SQLException e)
            {
                System.out.println(e.getMessage());
                return new ArrayList<>();
            }
        }, executor);
    }

    public CompletableFuture<Integer> getNewsCountForDate(String link, int dayPast)
    {
        return CompletableFuture.supplyAsync(() ->
                {
                    try
                    {
                        return dateEngine.getNewsCountForDay(link, dayPast);
                    } catch (SQLException e)
                    {
                        System.out.println(e.getMessage());
                        return 0;
                    }
                },
                executor);
    }

    public CompletableFuture<List<RSSItemModel>> searchInArticles(String text)
    {
        return CompletableFuture.supplyAsync(() ->
                {
                    try
                    {
                        return searchEngine.searchArticle(text);
                    } catch (SQLException e)
                    {
                        System.out.println(e.getMessage());
                        return new ArrayList<>();
                    }
                },
                executor);
    }

    public CompletableFuture<List<RSSItemModel>> searchInTitles(String text)
    {
        return CompletableFuture.supplyAsync(() ->
                {
                    try
                    {
                        return searchEngine.searchTitle(text);
                    } catch (SQLException e)
                    {
                        System.out.println(e.getMessage());
                        return new ArrayList<>();
                    }
                },
                executor);
    }

    public CompletableFuture<List<RSSItemModel>> searchBoth(String text)
    {
        return CompletableFuture.supplyAsync(() ->
                {
                    try
                    {
                        return searchEngine.searchAll(text);
                    } catch (SQLException e)
                    {
                        System.out.println(e.getMessage());
                        return new ArrayList<>();
                    }
                },
                executor);
    }
}

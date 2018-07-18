package cli;

import core.Core;
import core.Service;
import dateEngine.DateEngine;
import rssRepository.RSSItemModel;
import rssRepository.RssItemRepository;
import searchEngine.SearchEngine;
import webSiteRepository.NewsWebPageModel;
import webSiteRepository.WebSiteRepository;

import java.sql.Date;
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
        scheduledExecutorService.scheduleAtFixedRate(this::updateAllWebsites, 0, 100, TimeUnit.SECONDS);
    }

    public CompletableFuture<Void> addWebSite(String websiteLink, String targetClass)
    {
        return CompletableFuture.runAsync(() -> webSiteRepository.addWebSite(websiteLink, targetClass), executor);
    }

    public CompletableFuture<List<NewsWebPageModel>> getWebSites()
    {
        return CompletableFuture.supplyAsync(() -> webSiteRepository.getWebsites(),
                executor);
    }

    public CompletableFuture<Void> updateWebsite(String webSiteLink)
    {
        return CompletableFuture.runAsync(() -> webSiteRepository.getWebsite(webSiteLink).update(), executor);
    }

    public List<CompletableFuture<Void>> updateAllWebsites()
    {
        List<CompletableFuture<Void>> list = new ArrayList<>();
        for (NewsWebPageModel newsWebPageModel : webSiteRepository.getWebsites())
            list.add(updateWebsite(newsWebPageModel.getLink()));
        return list;
    }

    public CompletableFuture<List<RSSItemModel>> getWebSiteRssData(String webPageLink)
    {
        return CompletableFuture.supplyAsync(() ->rssItemRepository.rssForWebsite(webPageLink), executor);
    }

    public CompletableFuture<List<RSSItemModel>> getAllRssData()
    {
        return CompletableFuture.supplyAsync(() -> rssItemRepository.getAllRSSData(), executor);
    }

    public CompletableFuture<List<RSSItemModel>> getTodayNewsForWebsite(String link)
    {
        return CompletableFuture.supplyAsync(() ->
                dateEngine.getNewsForWebsiteByDate(link, new Date(System.currentTimeMillis())),executor);
    }

    public CompletableFuture<List<RSSItemModel>> getSomeLastNewsForWebsite(String link, int num)
    {
        return CompletableFuture.supplyAsync(() ->
        dateEngine.getSomeLastRssForWebsite(link, num), executor);
    }

    public CompletableFuture<Integer> getNewsCountForDate(String link, int dayPast)
    {
        return CompletableFuture.supplyAsync(() ->
        dateEngine.getNewsCountForDay(link, dayPast),
                executor);
    }

    public CompletableFuture<List<RSSItemModel>> searchInArticles(String text)
    {
        return CompletableFuture.supplyAsync(() ->
        searchEngine.searchArticle(text),
                executor);
    }

    public CompletableFuture<List<RSSItemModel>> searchInTitles(String text)
    {
        return CompletableFuture.supplyAsync(() ->
        searchEngine.searchTitle(text),
                executor);
    }

    public CompletableFuture<List<RSSItemModel>> searchBoth(String text)
    {
        return CompletableFuture.supplyAsync(() ->
        searchEngine.searchAll(text),
                executor);
    }
}

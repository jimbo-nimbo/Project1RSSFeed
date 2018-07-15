package main;

import database.implementation.DataBase;
import RSSTable.interfaces.RSSItemRepository;
import searchEngine.interfaces.SearchEngine;
import websiteTable.interfaces.WebSiteRepository;
import websiteTable.model.NewsWebPageModel;
import RSSTable.model.RSSItemModel;

import java.util.List;

public class RSSService
{
    private WebSiteRepository webSiteRepository;
    private RSSItemRepository rssItemRepository;
    private SearchEngine searchEngine;

    public RSSService()
    {
        DataBase dataBase = DataBase.getInstance();
        webSiteRepository = dataBase;
        rssItemRepository = dataBase;
        searchEngine = dataBase;
    }

    public void addWebSite(NewsWebPageModel newsWebPageModel)
    {
        webSiteRepository.addWebSite(newsWebPageModel);
    }

    public void addWebSite(String websiteLink, String targetClass, String datePattern)
    {
        webSiteRepository.addWebSite(new NewsWebPageModel(websiteLink, targetClass, datePattern, rssItemRepository));
    }

    public List<NewsWebPageModel> getWebSites()
    {
        return webSiteRepository.getWebsites();
    }

    public void updateDataBase()
    {
        webSiteRepository.getWebsites().forEach(e ->
        {
            System.out.println(e.getLink());
            e.update();
        });
    }

    public void updateDatabaseForWebsite(String webSiteLink)
    {
        webSiteRepository.getWebsite(webSiteLink).update();
    }

    public List<RSSItemModel> getWebSiteRssData(String webPageLink) {
        return rssItemRepository.getRSSDataFromWebSite(webPageLink);
    }

    public List<RSSItemModel> getAllRssData()
    {
        return rssItemRepository.getAllRSSData();
    }

    public String getArticle(String articleLink)
    {
        return rssItemRepository.getArticle(articleLink);
    }
}

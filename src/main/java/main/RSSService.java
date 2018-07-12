package main;

import database.DataBaseService;
import database.RSSItemRepository;
import database.WebSiteRepository;
import models.NewsWebPageModel;
import models.RSSItemModel;

import java.util.List;

public class RSSService
{
    private WebSiteRepository webSiteRepository;
    private RSSItemRepository rssItemRepository;

    public RSSService()
    {
        DataBaseService dataBaseService= DataBaseService.getInstance();
        webSiteRepository = dataBaseService;
        rssItemRepository = dataBaseService;
    }

    public void addWebSite(NewsWebPageModel newsWebPageModel)
    {
        webSiteRepository.addWebSite(newsWebPageModel);
    }

    public void addWebSite(String websiteLink, String targetClass)
    {
        webSiteRepository.addWebSite(new NewsWebPageModel(websiteLink,targetClass,rssItemRepository));
    }

    public List<NewsWebPageModel> getWebSites()
    {
        return webSiteRepository.getWebsites();
    }

    public void updateDataBase()
    {
        webSiteRepository.getWebsites().forEach(NewsWebPageModel::update);
    }

    public void updateDatabaseForWebsite(String webSiteLink)
    {
        webSiteRepository.getWebsite(webSiteLink).update();
    }

    public List<RSSItemModel> getWebSiteRssData(String webPageLink) {
        return webSiteRepository.getRSSDataFromWebSite(webPageLink);
    }

    public List<RSSItemModel> getAllRssData()
    {
        return webSiteRepository.getAllRSSData();
    }

    public String getArticle(String articleLink)
    {
        return rssItemRepository.getArticle(articleLink);
    }
}

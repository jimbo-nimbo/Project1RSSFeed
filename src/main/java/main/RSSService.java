package main;

import database.RSSItemRepository;
import database.WebSiteRepository;
import models.NewsWebPageModel;
import models.RSSItemModel;

import java.util.List;

public class RSSService
{
    private WebSiteRepository webSiteRepository;
    private RSSItemRepository rssItemRepository;

    public void updateDataBase()
    {
        webSiteRepository.getWebsites().forEach(NewsWebPageModel::update);
    }

    public void addWebSite(NewsWebPageModel newsWebPageModel)
    {
        webSiteRepository.addWebSite(newsWebPageModel);
    }

    public List<RSSItemModel> getWebSiteRssData(String webPageLink)
    {
        return webSiteRepository.getRSSDataFromWebSite(webPageLink);
    }

    public List<RSSItemModel> getAllRssData()
    {
        return webSiteRepository.getAllRSSData();
    }

    public NewsWebPageModel getWebpage(String webSiteLink)
    {
        return webSiteRepository.getWebsite(webSiteLink);
    }

    public String getArticle(String articleLink)
    {
        return rssItemRepository.getArticle(articleLink);
    }

    public List<NewsWebPageModel> getWebSites()
    {
        return webSiteRepository.getWebsites();
    }
}

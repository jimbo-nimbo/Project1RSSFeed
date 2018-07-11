package main;

import database.DataBaseService;
import database.RSSItemRepository;
import database.WebSiteRepository;
import models.NewsWebPageModel;

import java.util.ArrayList;
import java.util.List;

public class RSSService
{
    private WebSiteRepository webSiteRepository;
    private RSSItemRepository rssItemRepository;

    public void updateDataBase()
    {

    }

    public void addWebSite(String url, String targetClass)
    {
        NewsWebPageModel newsWebPageModel = new NewsWebPageModel(url, targetClass, rssItemRepository);
        webSiteRepository.addWebSite(newsWebPageModel);
    }

    public String getWebSiteRssData(String url)
    {
        return null;
    }

    public String getArticle()
    {
        return null;
    }

    public List<NewsWebPageModel> getWebSites()
    {
        return webSiteRepository.getWebsites();
    }
}

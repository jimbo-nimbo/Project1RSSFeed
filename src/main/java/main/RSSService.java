package main;

import database.DataBaseService;
import database.WebSiteRepository;
import models.NewsWebPageModel;

import java.util.ArrayList;
import java.util.List;

public class RSSService
{
    private WebSiteRepository webSiteRepository;

    public void updateDataBase()
    {
    }

    public void addWebSite(NewsWebPageModel newsWebPageModel)
    {
        webSiteRepository.addWebSite(newsWebPageModel);
    }

    public String getWebSiteRssData()
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

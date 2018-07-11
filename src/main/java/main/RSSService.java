package main;

import database.DataBaseService;
import database.WebSiteRepository;
import models.NewsWebPageModel;

import java.util.ArrayList;

public class RSSService
{
    private WebSiteRepository webSiteRepository;

    public void upDateDataBase()
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

    public ArrayList<String> getWebSites()
    {
        return null;
    }
}

package main;

import database.implementation.DataBase;
import RSSTable.interfaces.RSSItemRepository;
import websiteTable.interfaces.WebSiteRepository;
import websiteTable.model.NewsWebPageModel;
import RSSTable.model.RSSItemModel;

import java.util.List;

public class RSSService
{
    private WebSiteRepository webSiteRepository;
    private RSSItemRepository rssItemRepository;

    public RSSService()
    {
        DataBase dataBase = DataBase.getInstance();
        webSiteRepository = dataBase;
        rssItemRepository = dataBase;
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
//        webSiteRepository.getWebsites().forEach(NewsWebPageModel::update);
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

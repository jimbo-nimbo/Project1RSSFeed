package main;

import database.implementation.DataBase;
import RSSTable.interfaces.RSSItemRepository;
import database.implementation.DataBaseThreadManager;
import searchEngine.interfaces.SearchEngine;
import websiteTable.interfaces.WebSiteRepository;
import websiteTable.model.NewsWebPageModel;
import RSSTable.model.RSSItemModel;

import java.util.List;

public class RSSService
{
    private DataBaseThreadManager dataBase;

    public RSSService()
    {
        dataBase = DataBaseThreadManager.getInstance();
    }

//    public List<RSSItemModel> getWebSiteRssData(String webPageLink) {
//        return rssItemRepository.getRSSDataFromWebSite(webPageLink);
//    }
//
//    public List<RSSItemModel> getAllRssData()
//    {
//        return rssItemRepository.getAllRSSData();
//    }
//
//    public String getArticle(String articleLink)
//    {
//        return rssItemRepository.getArticle(articleLink);
//    }
}

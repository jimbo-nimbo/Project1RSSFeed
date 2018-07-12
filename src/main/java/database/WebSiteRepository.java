package database;

import models.NewsWebPageModel;
import models.RSSItemModel;

import java.util.List;

public interface WebSiteRepository
{
    void addWebSite(NewsWebPageModel newsWebPageModel);
    List<NewsWebPageModel> getWebsites();
    List<RSSItemModel> getAllRSSData();
    List<RSSItemModel> getRSSDataFromWebSite(String webPageLink);
    NewsWebPageModel getWebsite(String websiteLink);
}

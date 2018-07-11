package database;

import models.NewsWebPageModel;

import java.util.List;

public interface WebSiteRepository
{
    void addWebSite(NewsWebPageModel newsWebPageModel);
    List<NewsWebPageModel> getWebsites();
}

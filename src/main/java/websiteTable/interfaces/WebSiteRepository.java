package websiteTable.interfaces;

import websiteTable.model.NewsWebPageModel;

import java.util.List;

public interface WebSiteRepository {
  void addWebSite(NewsWebPageModel newsWebPageModel);

  List<NewsWebPageModel> getWebsites();

  NewsWebPageModel getWebsite(String websiteLink);
}

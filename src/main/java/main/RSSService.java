package main;

import database.implementation.DataBaseThreadManager;

public class RSSService {
  private DataBaseThreadManager dataBase;

  public RSSService() {
    dataBase = DataBaseThreadManager.getInstance();
  }



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

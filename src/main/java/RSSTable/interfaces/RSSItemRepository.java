package RSSTable.interfaces;

import RSSTable.model.RSSItemModel;

import java.util.List;

public interface RSSItemRepository {
  void addRSSItem(RSSItemModel rssItemModel);

  String getArticle(String link);

  RSSItemModel getRSSItem(String link);

  List<RSSItemModel> getAllRSSData();

  List<RSSItemModel> getRSSDataFromWebSite(String webPageLink);
}

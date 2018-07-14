package RSSTable.interfaces;

import RSSTable.model.RSSItemModel;

public interface RSSItemRepository
{
    void addRSSItem(RSSItemModel rssItemModel);
    String getArticle(String link);
    RSSItemModel getRSSItem(String link);
}

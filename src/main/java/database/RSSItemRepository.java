package database;

import models.RSSItemModel;

public interface RSSItemRepository
{
    void addRSSItem(RSSItemModel rssItemModel);
    String getArticle(String link);
}

package rssRepository;

public enum RSSItemTableQueries {
  CREATE_RSSITEM_TABLE_IF_NOT_EXISTS(
      "CREATE TABLE IF NOT EXISTS RssItem"
          + "(RID int NOT NULL AUTO_INCREMENT,"
          + "title mediumtext,"
          + "description mediumtext,"
          + "link varchar(300),"
          + "pubDate timestamp,"
          + "article longtext,"
          + "WID int NOT NULL,"
          + "PRIMARY KEY (RID),"
          + "UNIQUE (link),"
          + " FOREIGN KEY (WID) REFERENCES WebSite(WID))"
          + "CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;"),
  INSERT_INTO_RSS_ITEM_TABLE(
      "INSERT  INTO RssItem "
          + "(title, description, link, pubDate, article, WID) VALUES "
          + "(?, ?, ?, ?, ?, (SELECT (WID) FROM WebSite WHERE url LIKE ?));"),
  SELECT_FROM_RSSITEM_BY_NEWSPAGE_LINK("SELECT * FROM RssItem WHERE newsWebPage LIKE ?;"),
  SELECT_RSS_ITEM_BY_LINK("SELECT * FROM RssItem WHERE link LIKE ?;"),
  SELECT_ALL_RSS_ITEMS("SELECT * FROM RssItem;"),
  DROP_RSS_ITEM("DROP TABLE RssItem;");

  private String query;

  RSSItemTableQueries(String query) {
    this.query = query;
  }

  @Override
  public String toString() {
    return query;
  }
}

package rssRepository;

public enum RSSItemTableQueries
{
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
                    + "FOREIGN KEY (WID) REFERENCES WebSite(WID))"
                    + "CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;"),
    INSERT_INTO_RSS_ITEM_TABLE(
            "INSERT  INTO RssItem "
                    + "(title, description, link, pubDate, article, WID) VALUES "
                    + "(?, ?, ?, ?, ?, (SELECT (WID) FROM WebSite WHERE WID = ?));"),
    SELECT_RSS_ITEM_BY_LINK("SELECT * FROM RssItem WHERE link = ?;"),
    SELECT_RSS_ITEM_BY_ID("SELECT * FROM RssItem WHERE RID = ?;"),
    SELECT_ALL_RSS_FROM_WEBSITE_BY_WID("SELECT * FROM RssItem WHERE WID = ?;"),
    SELECT_ALL_RSS_ITEMS("SELECT * FROM RssItem;"),
    DROP_RSS_ITEM("DROP TABLE RssItem;"),
    CLEAR_TABLE_RSSITEM("DELETE from RssItem;");


    private String query;

    RSSItemTableQueries(String query)
    {
        this.query = query;
    }

    @Override
    public String toString()
    {
        return query;
    }
}

package database;

public enum RSSItemTableQueries
{
    CREATE_RSSITEM_TABLE_IF_NOT_EXISTS(
            "CREATE TABLE IF NOT EXISTS RssItem" +
                    "(title mediumtext," +
                    " description mediumtext," +
                    " link varchar(300) PRIMARY KEY ," +
                    " pubDate Date," +
                    " article longtext," +
                    " newsWebPage varchar(300)," +
                    " FOREIGN KEY (newsWebPage) REFERENCES WebSite(url)) " +
                    "CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;" ),
    INSERT_INTO_RSSITEM_TABLE(
            "INSERT  INTO RssItem " +
                    "(title, description, link, pubDate, article, newsWebPage) VALUES " +
                    "(?, ?, ?, ?, ?, (SELECT (url) FROM WebSite WHERE url LIKE ?));"
    ),
    SELECT_FROM_RSSITEM_BY_NEWSPAGE_LINK(
            "SELECT * FROM RssItem WHERE newsWebPage LIKE ?;"
    ),
    SELECT_FROM_RSSITEM_BY_ARTICLE_LINK(
            "SELECT * FROM RssItem WHERE link LIKE ?;"
    ),
    SELECT_ALL_RSS_ITEMS(
            "SELECT * FROM RssItem;"
    )
    ;

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

package searchEngine.implementions;

public enum SearchQuery {
    INJECT_DATA_TO_RSS_ITEM(
            "INSERT  INTO RssItem " +
                    "(title, description, link, pubDate, article, newsWebPage) VALUES " +
                    "(?, ?, ?, ?, ?, (SELECT (url) FROM WebSite WHERE url LIKE ?));"
    ),
    DELETE_DATA_FROM_RSS_ITEM(
            "DELETE FROM RssItem " +
                    "WHERE link = ?;"
    );
    String query;
    SearchQuery(String query){
        this.query = query;
    }
    @Override
    public String toString()
    {
        return query;
    }
}
package searchEngine.enumarations;

public enum SearchSqlQuery {
    SEARCH_FOR_TITLE_IN_RSSITEM(
            "SELECT * FROM RssItem WHERE title LIKE ?;"
    ),
    SEARCH_FOR_ARTICLE_IN_RSSITEM(
            "SELECT * FROM RssItem WHERE article LIKE ?;"
    ),
    SEARCH_FOR_TITLE_OR_ARTICLE_IN_RSSITEM(
            "SELECT * FROM RssItem WHERE title LIKE ? OR article LIKE ?;"
    );
    String query;
    SearchSqlQuery(String query){
        this.query = query;
    }
    @Override
    public String toString()
    {
        return query;
    }
}

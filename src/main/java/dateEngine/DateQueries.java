package dateEngine;

public enum DateQueries
{
    SELECT_SOME_LAST_RSS_ITEM_BY_WID(
            "select * from RssItem" +
                    "where WID = ? "
                    + " ORDER BY pubDate DESC" +
                    "LIMIT ?;"),
    SELECT_RSS_FROM_WEBSITE_BY_DATE(
            "SELECT * FROM RssItem WHERE" +
                    " WID = ? "
                    + "AND DATE(pubDate) = ?;");

    private String query;

    DateQueries(String query)
    {
        this.query = query;
    }

    @Override
    public String toString()
    {
        return query;
    }
}

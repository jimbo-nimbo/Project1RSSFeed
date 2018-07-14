package dateEngine.enumaration;

public enum DateQueries
{
    SELECT_TEN_LAST_RSS_ITEM_BY_WEBSITE(
            "select * from RssItem where newsWebPage " +
                    "like (select url from WebSite where url like ?);"
    );

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

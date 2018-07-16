package dateEngine;

public enum DateQueries {
  SELECT_TEN_LAST_RSS_ITEM_BY_WEBSITE(
      "select * from RssItem where newsWebPage "
          + "like (select url from WebSite where url like ?);"),
  SELECT_BY_DATE_NEWS_BY_WEBSITE(
      "SELECT * FROM RssItem WHERE newsWebPage "
          + "LIKE (SELECT url FROM WebSite WHERE url LIKE ?) AND pubDate LIKE ?;");

  private String query;

  DateQueries(String query) {
    this.query = query;
  }

  @Override
  public String toString() {
    return query;
  }
}

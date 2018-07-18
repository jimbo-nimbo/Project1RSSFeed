package rssRepository;

public enum SearchInjectQuery {
  INJECT_DATA_TO_RSS_ITEM(
      "INSERT  INTO RssItem "
          + "(title, description, link, pubDate, article, WID) VALUES "
          + "(?, ?, ?, ?, ?, (SELECT (WID) FROM WebSite WHERE link LIKE ?));"),
  DELETE_DATA_FROM_RSS_ITEM("DELETE FROM RssItem " + "WHERE link = ?;");
  String query;

  SearchInjectQuery(String query) {
    this.query = query;
  }

  @Override
  public String toString() {
    return query;
  }
}

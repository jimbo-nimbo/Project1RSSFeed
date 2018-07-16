package websiteTable.enumaration;

public enum WebsiteTableQueries {
  CREATE_WEBSITE_TABLE_IF_NOT_EXISTS(
      "CREATE TABLE IF NOT EXISTS WebSite"
          + "(url varchar (300) PRIMARY KEY ,"
          + " class varchar (200) ,"
          + " datePattern varchar(200))"
          + " CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;"),
  SELECT_WEBSITE_BY_LINK("SELECT * FROM WebSite WHERE url LIKE ?;"),
  INSERT_INTO_TABLE("INSERT INTO WebSite (url, class, datePattern) VALUES (?,?,?);"),
  UPDATE_TARGET_CLASS_BY_LINK("UPDATE WebSite SET class = ?, datePattern = ? WHERE url LIKE ?;"),
  SELECT_ALL_WEBSITES("SELECT * FROM WebSite;"),
  DROP_WEB_SITE_TABLE("DROP TABLE WebSite;");

  private String query;

  WebsiteTableQueries(String query) {
    this.query = query;
  }

  @Override
  public String toString() {
    return query;
  }
}

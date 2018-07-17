package webSiteRepository;

public enum WebsiteTableQueries {
  CREATE_WEBSITE_TABLE_IF_NOT_EXISTS(
      "CREATE TABLE IF NOT EXISTS WebSite"
          + "(WID int NOT NULL AUTO_INCREMENT,"
          + "link varchar (300) ,"
          + " class varchar (200) ,"
          + " datePattern varchar(200),"
          + "PRIMARY KEY (WID),"
          + "UNIQUE (link))"
          + " CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;"),
  SELECT_WEBSITE_BY_LINK("SELECT * FROM WebSite WHERE link = ?;"),
  INSERT_INTO_TABLE("INSERT INTO WebSite (link, class, datePattern) VALUES (?,?,?);"),
  UPDATE_TARGET_CLASS_BY_LINK("UPDATE WebSite SET class = ?, datePattern = ? WHERE link = ?;"),
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

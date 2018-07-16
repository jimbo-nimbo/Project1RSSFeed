package RSSTable.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import websiteTable.model.NewsWebPageInformation;

import java.io.IOException;
import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RSSItemModel {
  private String title;
  private String description;
  private String link;
  private String article;
  private String dateString;
  private String newsWebPage;
  private Date date;

  private NewsWebPageInformation newsWebPageInformation;

  /**
   * constructor for getting model from database
   *
   * @param resultSet
   * @param newsWebPageInformation
   */
  public RSSItemModel(ResultSet resultSet, NewsWebPageInformation newsWebPageInformation) {
    this.newsWebPageInformation = newsWebPageInformation;
    newsWebPage = newsWebPageInformation.getLink();

    try {
      title = resultSet.getString("title");
      description = resultSet.getString("description");
      link = resultSet.getString("link");
      date = resultSet.getDate("pubDate");
      article = resultSet.getString("article");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * constructor for fetching data for first time
   *
   * @param title
   * @param description
   * @param link
   * @param dateString
   * @param newsWebPageInformation
   */
  public RSSItemModel(
      String title,
      String description,
      String link,
      String dateString,
      NewsWebPageInformation newsWebPageInformation) {
    this.title = title;
    this.description = description;
    this.link = link;
    this.dateString = dateString;
    this.newsWebPageInformation = newsWebPageInformation;
    newsWebPage = newsWebPageInformation.getLink();

    parseDate();
    fetch();
  }

  private void parseDate() {
    SimpleDateFormat simpleDateFormat =
        new SimpleDateFormat(newsWebPageInformation.getDatePattern());
    try {
      date = simpleDateFormat.parse(dateString);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  private void fetch() {
    // for non-ASCII URLs
    String asciiLink = URI.create(link).toASCIIString();
    try {
      Document document = Jsoup.connect(asciiLink).get();
      article = document.select("div." + newsWebPageInformation.getTargetClass()).text();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getArticle() {
    return article;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getDateString() {
    return dateString;
  }

  public Date getDate() {
    return date;
  }

  public void setDateString(String dateString) {
    this.dateString = dateString;
  }

  public String getNewsWebPage() {
    return newsWebPage;
  }

  @Override
  public String toString() {
    return "RSSItemModel{"
        + "title='"
        + title
        + '\''
        + ", description='"
        + description
        + '\''
        + ", link='"
        + link
        + '\''
        + ", article='"
        + article
        + '\''
        + ", dateString='"
        + dateString
        + '\''
        + ", newsWebPageInformation="
        + newsWebPageInformation
        + '}';
  }
}

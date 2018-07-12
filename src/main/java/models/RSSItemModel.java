package models;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;

public class RSSItemModel
{
    private String title;
    private String description;
    private String link;
    private String article;
    private String pubDate;
    private String newsWebPage;

    private NewsWebPageInformation newsWebPageInformation;

    //constructor for getting data from database
    public RSSItemModel(String title, String description, String link, String article, String pubDate, NewsWebPageInformation newsWebPageInformation)
    {
        this.title = title;
        this.description = description;
        this.link = link;
        this.article = article;
        this.pubDate = pubDate;
        this.newsWebPageInformation = newsWebPageInformation;
        newsWebPage = newsWebPageInformation.getLink();
    }

    public RSSItemModel(String title, String description, String link,
                        String pubDate, NewsWebPageInformation newsWebPageInformation)
    {
        this.title = title;
        this.description = description;
        this.link = link;
        this.pubDate = pubDate;
        this.newsWebPageInformation = newsWebPageInformation;
        newsWebPage = newsWebPageInformation.getLink();

        fetch();
    }

    public void fetch()
    {
        //for non-ASCII URLs
        String asciiLink = URI.create(link).toASCIIString();
        try {
            Document document = Jsoup.connect(asciiLink).get();
            article = document.select("div."+ newsWebPageInformation.getTargetClass()).text();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getArticle()
    {
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

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getNewsWebPage()
    {
        return newsWebPage;
    }

    @Override
    public String toString()
    {
        return "RSSItemModel{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", article='" + article + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", newsWebPageInformation=" + newsWebPageInformation +
                '}';
    }
}
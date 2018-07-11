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
    private String author;
    private String article;
    private String pubDate;

    private NewsWebPageInformation newsWebPageInformation;

    public RSSItemModel(String title, String description, String link, String author,
                        String pubDate, NewsWebPageInformation newsWebPageInformation)
    {
        this.title = title;
        this.description = description;
        this.link = link;
        this.author = author;
        this.pubDate = pubDate;
        this.newsWebPageInformation = newsWebPageInformation;

        fetch();
    }

    public void fetch()
    {
        //for non-ASCII URLs
        link = URI.create(link).toASCIIString();
        try {
            Document document = Jsoup.connect(link).get();

            article = document.select("div."+ newsWebPageInformation.getTargetClass()).text();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public String toString()
    {
        return "RSSItemModel{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", author='" + author + '\'' +
                ", article='" + article + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", newsWebPageInformation=" + newsWebPageInformation +
                '}';
    }
}
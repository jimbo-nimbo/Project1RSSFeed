package models;

public class SearchResult {
    private String title;
    private String description;
    private String link;
    private String article;
    private String pubDate;

    public SearchResult(String title, String description, String link, String article, String pubDate)
    {
        this.title = title;
        this.description = description;
        this.link = link;
        this.article = article;
        this.pubDate = pubDate;
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

    @Override
    public String toString()
    {
        return "RSSItemModel{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", article='" + article + '\'' +
                ", pubDate='" + pubDate + '\'' +
                '}';
    }
}

package models;

import database.RSSItemRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Objects;

public class NewsWebPageModel implements NewsWebPageInformation
{
    private String link;
    private String targetClass;
    private String title;
    private String description;

    private RSSItemRepository rssItemRepository;

    public NewsWebPageModel(String link, String targetClass, RSSItemRepository rssItemRepository)
    {
        this.link = link;
        this.targetClass = targetClass;
        this.rssItemRepository = rssItemRepository;

        fetch();
    }

    public void fetch()
    {
         try {
            Document document = Jsoup.connect(link).get();
            title = document.select("title").first().text();
            description = document.select("description").first().text();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update()
    {
        try {
            Document document = Jsoup.connect(link).get();

            for (Element element: document.select("item"))
            {
                rssItemRepository.addRSSItem(new RSSItemModel(
                        element.select("title").first().text(),
                        element.select("description").first().text(),
                        element.select("link").first().text(),
                        element.select("pubDate").first().text(),
                        this));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    @Override
    public String getLink()
    {
        return link;
    }

    public String getTargetClass()
    {
        return targetClass;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsWebPageModel that = (NewsWebPageModel) o;
        return Objects.equals(link, that.link) &&
                Objects.equals(targetClass, that.targetClass) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode()
    {

        return Objects.hash(link, targetClass, title, description);
    }

    @Override
    public String toString()
    {
        return "NewsWebPageModel{" +
                "link='" + link + '\'' +
                ", targetClass='" + targetClass + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

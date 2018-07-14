package websiteTable.model;

import RSSTable.interfaces.RSSItemRepository;
import RSSTable.model.RSSItemModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class NewsWebPageModel implements NewsWebPageInformation
{
    private String link;
    private String targetClass;
    private String datePattern;
    private String title;
    private String description;

    private RSSItemRepository rssItemRepository;

    public NewsWebPageModel(ResultSet resultSet, RSSItemRepository rssItemRepository)
    {
        this.rssItemRepository = rssItemRepository;
        try
        {
            link = resultSet.getString("url");
            targetClass = resultSet.getString("class");
            datePattern = resultSet.getString("datePattern");
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        fetchTitleAndDescription();
    }

    public NewsWebPageModel(String link, String targetClass, String dataPattern, RSSItemRepository rssItemRepository)
    {
        this.link = link;
        this.targetClass = targetClass;
        this.rssItemRepository = rssItemRepository;
        this.datePattern = dataPattern;

        fetchTitleAndDescription();
    }

    @Override
    public String getDatePattern()
    {
        return datePattern;
    }

    private void fetchTitleAndDescription()
    {
         try {
            Document document = Jsoup.connect(link).get();
            title = document.select("title").first() == null ?
            null : document.select("title").first().text();
            description = document.select("description").first() == null?
            null : document.select("description").first().text();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update()
    {
        try {
            Document document = Jsoup.connect(link).get();

            Element title;
            Element description;
            Element link;
            Element pubDate;
            for (Element element: document.select("item"))
            {
                title = document.select("title").first();
                description = document.select("description").first();
                link = document.select("link").first();
                pubDate = document.select("pubDate").first();
                rssItemRepository.addRSSItem(new RSSItemModel(
                        title == null ? null : title.text(),
                        description == null ? null : description.text(),
                        link == null ? null : link.text(),
                        pubDate == null ? null : pubDate.text(),
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

package webSiteRepository;

import core.Core;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import rssRepository.RSSItemModel;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class NewsWebPageModel
{
    private int id;
    private String link;
    private String targetClass;
    private String datePattern;
    private String title;
    private String description;

    private static DatePatterns datePatterns;

    {
        try
        {
            datePatterns = new DatePatterns();
        } catch (IOException e)
        {
            System.out.println("cannot read the config file of date patterns!\n" + e.getMessage() + "\n");
        }
    }

    public NewsWebPageModel(ResultSet resultSet) throws SQLException
    {
        try
        {
            id = resultSet.getInt("WID");
            link = resultSet.getString("link");
            targetClass = resultSet.getString("class");
            datePattern = resultSet.getString("datePattern");
        } catch (SQLException e)
        {
            Core.getInstance().logToFile(e.getMessage());
            throw e;
        }
    }

    /**
     * id is null first
     *
     * @param link
     * @param targetClass
     */
    public NewsWebPageModel(String link, String targetClass) throws IOException, ParseException
    {
        this.link = link;
        this.targetClass = targetClass;

        fetchTitleAndDescriptionAndDatePattern();
    }


    private void fetchTitleAndDescriptionAndDatePattern() throws ParseException, IOException
    {
        try
        {
            Document document = Jsoup.connect(link).get();
            title =
                    document.select("title").first() == null ? null : document.select("title").first().text();
            description =
                    document.select("description").first() == null
                            ? null
                            : document.select("description").first().text();

            String dateString = document.select("pubDate").first().text();
            SimpleDateFormat simpleDateFormat;
            for (String datePattern : datePatterns.getPatterns())
            {
                simpleDateFormat = new SimpleDateFormat(datePattern);
                try
                {
                    simpleDateFormat.parse(dateString);
                    this.datePattern = datePattern;
                    break;
                } catch (ParseException ignored)
                {
                }
            }
            if (this.datePattern == null)
                throw new ParseException("cannot read the date of items in " + getLink() + "!", 2);

        } catch (IOException e)
        {
            Core.getInstance().logToFile(e.getMessage());
            throw new IOException("cannot connect to " + getLink() + "!");
        }
    }

    public void update() throws SQLException, IOException, ParseException
    {
        Document document = null;
        try
        {
            document = Jsoup.connect(link).get();
        } catch (IOException e)
        {
            Core.getInstance().logToFile(e.getMessage());
            throw new IOException("cannot connect to " + getLink() + "!");
        }

        Element title;
            Element description;
            Element link;
            Element pubDate;
            for (Element element : document.select("item"))
            {
                title = element.select("title").first();
                description = element.select("description").first();
                link = element.select("link").first();
                pubDate = element.select("pubDate").first();
                Core.getInstance()
                        .getRssRepository()
                        .addRSSItem(
                                new RSSItemModel(
                                        title == null ? null : title.text(),
                                        description == null ? null : description.text(),
                                        link == null ? null : link.text(),
                                        pubDate == null ? null : pubDate.text(),
                                        this));
            }
    }

    public String getDatePattern()
    {
        return datePattern;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public String getLink()
    {
        return link;
    }

    public String getTargetClass()
    {
        return targetClass;
    }

    public int getId()
    {
        return id;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsWebPageModel that = (NewsWebPageModel) o;
        return Objects.equals(link, that.link)
                && Objects.equals(targetClass, that.targetClass)
                && Objects.equals(title, that.title)
                && Objects.equals(description, that.description);
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
                "id=" + id +
                ", link='" + link + '\'' +
                ", targetClass='" + targetClass + '\'' +
                ", datePattern='" + datePattern + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

package rssRepository;

import core.Core;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import webSiteRepository.NewsWebPageModel;

import java.io.IOException;
import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RSSItemModel
{
    private String title;
    private String description;
    private String link;
    private String article;
    private String dateString;
    private int id;
    private Timestamp date;

    private NewsWebPageModel newsWebPageModel;

    /**
     * constructor for getting model from database
     *
     * @param resultSet
     * @param newsWebPageModel
     */
    public RSSItemModel(ResultSet resultSet, NewsWebPageModel newsWebPageModel) throws SQLException
    {
        this.newsWebPageModel = newsWebPageModel;

        try
        {
            title = resultSet.getString("title");
            description = resultSet.getString("description");
            link = resultSet.getString("link");
            date = resultSet.getTimestamp("pubDate");
            article = resultSet.getString("article");
            id = resultSet.getInt("RID");
        } catch (SQLException e)
        {
            Core.getInstance().logToFile(e.getMessage());
            throw e;
        }
    }

    /**
     * constructor for fetching data for first time
     *
     * @param title
     * @param description
     * @param link
     * @param dateString
     * @param newsWebPageModel
     */
    public RSSItemModel(
            String title,
            String description,
            String link,
            String dateString,
            NewsWebPageModel newsWebPageModel) throws IOException, ParseException
    {
        this.title = title;
        this.description = description;
        this.link = link;
        this.dateString = dateString;
        this.newsWebPageModel = newsWebPageModel;
        this.id = -1;

        parseDate();
        fetch();
    }

    public NewsWebPageModel getNewsWebPageModel()
    {
        return newsWebPageModel;
    }

    private void parseDate() throws ParseException
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(newsWebPageModel.getDatePattern());
        try
        {
            Long time = simpleDateFormat.parse(dateString).getTime();
            date = new Timestamp(time);
        } catch (ParseException e)
        {
            Core.getInstance().logToFile(e.getMessage());
            throw new ParseException("cannot read date format of " + getLink() + "!", 2);
        }
    }

    private void fetch() throws IOException
    {
        // for non-ASCII URLs
        String asciiLink = URI.create(link).toASCIIString();
        try
        {
            Document document = Jsoup.connect(asciiLink).get();
            article = document.select("div." + newsWebPageModel.getTargetClass()).text();
        } catch (IOException e)
        {
            Core.getInstance().logToFile(e.getMessage());
            throw new IOException("cannot connect to " + getLink() + "!");
        }
    }

    public String getArticle()
    {
        return article;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public String getDateString()
    {
        return dateString;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDateString(String dateString)
    {
        this.dateString = dateString;
    }

    public int getId()
    {
        return id;
    }

    @Override
    public String toString()
    {
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
                + ", newsWebPageModel="
                + newsWebPageModel.toString()
                + '}';
    }
}

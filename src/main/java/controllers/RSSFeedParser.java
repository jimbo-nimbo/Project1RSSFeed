package controllers;

import models.RSSFeedModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class RSSFeedParser
{
    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String CHANNEL = "channel";
    static final String LANGUAGE = "language";
    static final String COPYRIGHT = "copyright";
    static final String LINK = "link";
    static final String AUTHOR = "author";
    static final String ITEM = "item";
    static final String PUB_DATE = "pubDate";
    static final String GUID = "guid";

    private String feedUrl;


    public RSSFeedParser(String feedUrl)
    {
        this.feedUrl = feedUrl;
    }

    public RSSFeedModel readFeed()
    {
        RSSFeedModel feed = null;

        try {
            Document document = Jsoup.connect(feedUrl).get();

            for (Element element: document.select("item"))
                System.out.println("=====> " + element);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return feed;
    }

}
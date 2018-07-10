package parser;

import models.RSSFeedModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class RSSFeedParser
{
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
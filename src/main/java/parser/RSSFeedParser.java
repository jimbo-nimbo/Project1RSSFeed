package parser;

import models.RSSFeedModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class RSSFeedParser
{

    public RSSFeedModel readFeed(String feedUrl)
    {
        RSSFeedModel feed = null;

        try {
            Document document = Jsoup.connect(feedUrl).get();

            for (Element element: document.select("item"))
            {
                System.out.println(element.select("link").text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return feed;
    }

}
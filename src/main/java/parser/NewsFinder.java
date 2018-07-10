package parser;

import models.RSSFeedModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URI;

public class NewsFinder
{
    private String url;
    public NewsFinder(String url)
    {
        //for non-ASCII URLs
        url = URI.create(url).toASCIIString();
        this.url = url;

        try {
            Document document = Jsoup.connect(url).get();
            System.out.println(document.select("div.news-page--news-text").text());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args)
    {
        new NewsFinder("http://www.varzesh3.com/news/1537429/تیر-لوپتگی-برای-حفظ-رونالدو-به-سنگ-خورد");
    }
}

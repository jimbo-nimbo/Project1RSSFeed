package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;

public class ArticleFinder
{
    private static ArticleFinder newsFinder;

    public String findArticle(String url, String targetClass)
    {
        //for non-ASCII URLs
        url = URI.create(url).toASCIIString();
        String article = null;

        try {
            Document document = Jsoup.connect(url).get();
            article = document.select("div."+ targetClass).text();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return article;
    }

}

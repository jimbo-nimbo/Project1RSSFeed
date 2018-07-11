package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class RSSItemModelTest
{
    @Test
    public void RSSItemConstructorTest()
    {
        RSSItemModel rssItemModel = new RSSItemModel("title",
                "description",
                "http://www.varzesh3.com/news/1537695/گرت-ساوتگیت-17-سال-پیش-در-چنین-روزی",
                 "2016.11.13",
                new NewsWebPageModel("http://www.varzesh3.com/rss/all", "news-page--news-text"));

        System.out.println(rssItemModel);
    }

}
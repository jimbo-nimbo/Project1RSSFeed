package parser;

import org.junit.Test;

import static org.junit.Assert.*;

public class RSSFeedParserTest
{
    @Test
    public void makingInstanceAndCheckGetters()
    {
        RSSFeedParser rssFeedParser = new RSSFeedParser();
        System.out.println(rssFeedParser.readFeed("http://www.varzesh3.com/rss/index"));
    }

}
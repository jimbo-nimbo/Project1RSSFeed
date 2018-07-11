package main;


public class main
{
    public static void main(String[] args)
    {
        RSSService rssService = new RSSService();

        rssService.addWebSite("http://www.varzesh3.com/rss/all", "news-page--news-text");
    }
}

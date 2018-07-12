package main;

public class Test
{
    private static RSSService rssService;

    public static void main(String[] args)
    {
        rssService = new RSSService();

        //add some websites to database
        rssService.addWebSite("http://www.irinn.ir/fa/rss/allnews","body");
        rssService.addWebSite("http://www.varzesh3.com/rss/all","news-page--news-text");
        rssService.addWebSite("http://www.irna.ir/en/rss.aspx?kind=-1&area=0","bodytext");
        rssService.addWebSite("http://www.tabnak.ir/fa/rss/allnews","item-text");
        rssService.addWebSite("https://www.yjc.ir/fa/rss/allnews","body");

        System.out.println(rssService.getWebSites());

        rssService.updateDatabaseForWebsite("http://www.varzesh3.com/rss/all");
//        System.out.println(rssService.getWebSiteRssData("http://www.varzesh3.com/rss/all"));

        rssService.updateDataBase();
//        System.out.println(rssService.getAllRssData());
    }
}

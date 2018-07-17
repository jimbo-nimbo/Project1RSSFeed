package database.implementation;

import core.Core;
import database.DatabaseConnectionPool;
import dateEngine.DateEngine;
import rssRepository.RSSItemTableQueries;
import rssRepository.RSSItemModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rssRepository.RssItemRepository;
import searchEngine.SearchEngine;
import webSiteRepository.WebSiteRepository;
import webSiteRepository.WebsiteTableQueries;
import webSiteRepository.NewsWebPageModel;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataBaseTest {
  DatabaseConnectionPool dataBase = Core.getInstance().getDatabaseConnectionPool();
    RssItemRepository rssItemRepository = Core.getInstance().getRssRepository();
    WebSiteRepository webSiteRepository = Core.getInstance().getWebSiteRepository();
    SearchEngine searchEngine = Core.getInstance().getSearchEngine();

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getInstance() {
    }

    @Test
    public void executeQuery() {
        try(Connection conn = Core.getInstance().getDatabaseConnectionPool().getConnection()) {
            ResultSet resultSet = dataBase.executeQuery(conn,"describe RssItem;");
            resultSet.first();
            System.err.println(resultSet.getString(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute() {

    }

    @Test
    public void addWebSite() {
    try (Connection conn = Core.getInstance().getDatabaseConnectionPool().getConnection()) {
      dataBase.execute(conn, RSSItemTableQueries.DROP_RSS_ITEM.toString());
      dataBase.execute(conn, WebsiteTableQueries.DROP_WEB_SITE_TABLE.toString());
      dataBase.execute(conn, WebsiteTableQueries.CREATE_WEBSITE_TABLE_IF_NOT_EXISTS.toString());
      dataBase.execute(conn, RSSItemTableQueries.CREATE_RSSITEM_TABLE_IF_NOT_EXISTS.toString());
        webSiteRepository.addWebSite(new NewsWebPageModel("http://www.irinn.ir/fa/rss/allnews", "body",
                "dd MMM yyyy HH:mm:ss zzz"));
        ResultSet resultSet = dataBase.executeQuery(conn, WebsiteTableQueries.SELECT_ALL_WEBSITES.toString());
            resultSet.beforeFirst();
            while (resultSet.next()){
                System.err.println("added this webpagemodel : " + resultSet.getString("url"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getWebsite() {

    }

    @Test
    public void getWebsites() {
    }

    @Test
    public void addRSSItem() {
    }

    @Test
    public void getAllRSSData() {
        addWebSite();
        NewsWebPageModel newsWebPageModel =
                webSiteRepository.getWebsite("http://www.irinn.ir/fa/rss/allnews");
        newsWebPageModel.update();
        ArrayList<RSSItemModel> ans = (ArrayList<RSSItemModel>) rssItemRepository.getAllRSSData();
        for(RSSItemModel rssItemModel : ans){
            System.err.println(rssItemModel.getLink());
        }
    }

    @Test
    public void getRSSDataFromWebSite() {
    }

    @Test
    public void getArticle() {
    }

    @Test
    public void getRSSItem() {
    }

    @Test
    public void getTenLastNewsForWebsite() {
    }

    @Test
    public void getTodayNewsForWebsite() {
        Date today = new Date(System.currentTimeMillis());
        ArrayList<RSSItemModel> ans = (ArrayList<RSSItemModel>) Core.getInstance().getDateEngine()
                .getNewsForWebsiteByDate("http://www.irinn.ir/fa/rss/allnews", today);
        for(RSSItemModel rssItemModel : ans){
            System.err.println("today news: " + rssItemModel.getTitle());
        }

    }

    @Test
    public void searchTitle() {
    }

    @Test
    public void searchArticle() {
    }

    @Test
    public void searchAll() {
    }


    @Test
    public void addWebSiteTest(){
//        webSiteRepository.addWebSite(new NewsWebPageModel("http://www.varzesh3.com/rss/all", "news-page--news-text",
//                ""));
        webSiteRepository.addWebSite(new NewsWebPageModel("http://www.irna.ir/en/rss.aspx?kind=-1&area=0", "bodytext", "EEE, dd MMM yyyy HH:mm"));
    }

    @Test
    public void getWebsiteListTest(){

    System.out.println(webSiteRepository.getWebsites());
        for (NewsWebPageModel newsWebPageModel : webSiteRepository.getWebsites())
            System.out.println(newsWebPageModel);
    }

    @Test
    public void updateRSSItemsTest()
    {
        webSiteRepository.getWebsites().get(1).update();
    }

    @Test
    public void getWebsiteTest()
    {
        System.out.println(webSiteRepository.getWebsite("http://www.varzesh3.com/rss/all"));
    }

    @Test
    public void getAllRSSDataTest()
    {
        System.out.println(rssItemRepository.getAllRSSData());
    }

    @Test
    public void getRSSDataFromWebSiteTest()
    {
        System.out.println(rssItemRepository.getRSSDataFromWebSite("https://en.isna.ir/rss"));
    }

    @Test
    public void getArticleTest()
    {
        System.out.println(rssItemRepository.getArticle("http://www.varzesh3.com/news/1537867/ضیایی-اتاق-رییس-فدراسیون-خانه-بازیکنان-است"));
    }
}
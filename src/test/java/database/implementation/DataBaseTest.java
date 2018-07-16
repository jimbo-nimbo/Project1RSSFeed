package database.implementation;

import rssRepository.RSSItemTableQueries;
import rssRepository.interfaces.RSSItemRepository;
import rssRepository.RSSItemModel;
import database.DataBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import searchEngine.interfaces.SearchEngine;
import webSiteRepository.WebsiteTableQueries;
import webSiteRepository.interfaces.WebSiteRepository;
import webSiteRepository.NewsWebPageModel;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataBaseTest {
    DataBase dataBase = null;
    RSSItemRepository rssItemRepository;
    WebSiteRepository webSiteRepository;
    SearchEngine searchEngine;
    @Before
    public void setUp() throws Exception {
        dataBase = DataBase.getInstance();
        rssItemRepository = dataBase;
        webSiteRepository = dataBase;
        searchEngine = dataBase;
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getInstance() {
    }

    @Test
    public void executeQuery() {
        ResultSet resultSet = dataBase.executeQuery("describe RssItem;");
        try {
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
        dataBase.execute(RSSItemTableQueries.DROP_RSS_ITEM.toString());
        dataBase.execute(WebsiteTableQueries.DROP_WEB_SITE_TABLE.toString());
        dataBase.execute(WebsiteTableQueries.CREATE_WEBSITE_TABLE_IF_NOT_EXISTS.toString());
        dataBase.execute(RSSItemTableQueries.CREATE_RSSITEM_TABLE_IF_NOT_EXISTS.toString());
        dataBase.addWebSite(new NewsWebPageModel("http://www.irinn.ir/fa/rss/allnews", "body",
                "dd MMM yyyy HH:mm:ss zzz", rssItemRepository));
        ResultSet resultSet = dataBase.executeQuery(WebsiteTableQueries.SELECT_ALL_WEBSITES.toString());
        try {
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
        DateQuery dateQuery = DataBase.getInstance();
        Date today = new Date(System.currentTimeMillis());
        ArrayList<RSSItemModel> ans = (ArrayList<RSSItemModel>) dateQuery.getNewsForWebsiteByDate("http://www.irinn.ir/fa/rss/allnews", today);
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
        DataBase.getInstance().addWebSite(new NewsWebPageModel("http://www.varzesh3.com/rss/all", "news-page--news-text", "",DataBase.getInstance()));
        DataBase.getInstance().addWebSite(new NewsWebPageModel("https://en.isna.ir/rss" , "item-body", "", DataBase.getInstance()));
    }

    @Test
    public void getWebsiteListTest(){

        for (NewsWebPageModel newsWebPageModel : DataBase.getInstance().getWebsites())
            System.out.println(newsWebPageModel);
    }

    @Test
    public void updateRSSItemsTest()
    {
        DataBase.getInstance().getWebsites().get(1).update();
    }

    @Test
    public void getWebsiteTest()
    {
        System.out.println(DataBase.getInstance().getWebsite("http://www.varzesh3.com/rss/all"));
    }

    @Test
    public void getAllRSSDataTest()
    {
        System.out.println(DataBase.getInstance().getAllRSSData());
    }

    @Test
    public void getRSSDataFromWebSiteTest()
    {
        System.out.println(DataBase.getInstance().getRSSDataFromWebSite("https://en.isna.ir/rss"));
    }

    @Test
    public void getArticleTest()
    {
        System.out.println(DataBase.getInstance().getArticle("http://www.varzesh3.com/news/1537867/ضیایی-اتاق-رییس-فدراسیون-خانه-بازیکنان-است"));
    }
}
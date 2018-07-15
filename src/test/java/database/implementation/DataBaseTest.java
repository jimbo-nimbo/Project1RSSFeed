package database.implementation;

import RSSTable.model.RSSItemModel;
import dateEngine.interfaces.DateQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import websiteTable.model.NewsWebPageModel;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DataBaseTest {
    DataBase dataBase = null;
    @Before
    public void setUp() throws Exception {
        dataBase = DataBase.getInstance();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getInstance() {
    }

    @Test
    public void executeQuery() {
    }

    @Test
    public void execute() {
    }

    @Test
    public void addWebSite() {
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
        ArrayList<RSSItemModel> ans = (ArrayList<RSSItemModel>) dateQuery.getTodayNewsForWebsite("http://www.irinn.ir/fa/rss/allnews", "2018-07-15");
        System.err.println(ans.get(0));

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
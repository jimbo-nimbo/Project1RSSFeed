package searchEngine.implementions;

import RSSTable.model.RSSItemModel;
import database.implementation.DataBase;
import searchEngine.enumarations.SearchInjectQuery;
import searchEngine.interfaces.SearchEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;


public class SearchEngineTest
{
    private SearchEngine searchEngine = DataBase.getInstance();
    @Before
    public void setup(){
        injectDate();
    }

    @After
    public void clean(){
        DataBase.getInstance().execute(SearchInjectQuery.DELETE_DATA_FROM_RSS_ITEM.toString(), "testLink");
    }

    @Test
    public void injectDate(){
        String title = "byeTestblahblah";
        String description = "describtion";
        String link = "testLink";
        String article = "helloTestblahblah";
        Date pubDate = new Date(System.currentTimeMillis());
        String newsWebPage = "http://www.irinn.ir/fa/rss/allnews";
//        DataBase.getInstance().execute(SearchInjectQuery.INJECT_DATA_TO_RSS_ITEM.toString(), title, description, link, pubDate, article, newsWebPage);

    }

    @Test
    public void searchTitle() {
//        ArrayList<RSSItemModel> searchResult = (ArrayList<RSSItemModel>) searchEngine.searchTitle("bye");
//        System.err.println(searchResult.get(0).getTitle());
        ArrayList<RSSItemModel> searchResult = (ArrayList<RSSItemModel>) searchEngine.searchTitle("10");
        System.err.println(searchResult.get(0).getTitle());
        searchResult = (ArrayList<RSSItemModel>) searchEngine.searchTitle("برق");
        System.err.println(searchResult.get(0).getTitle());
    }

    @Test
    public void searchArticle() {
        ArrayList<RSSItemModel> searchResult = (ArrayList<RSSItemModel>) searchEngine.searchArticle("hello");
        System.err.println(searchResult.get(0).getTitle());
        searchResult = (ArrayList<RSSItemModel>) searchEngine.searchArticle("24");
        System.err.println(searchResult.get(0).getArticle());
        searchResult = (ArrayList<RSSItemModel>) searchEngine.searchArticle("اسپورت");
        System.err.println(searchResult.get(0).getArticle());

    }

    @Test
    public void searchAll() {
        ArrayList<RSSItemModel> searchResult = (ArrayList<RSSItemModel>) searchEngine.searchAll("hello");
        System.err.println(searchResult.get(0).getTitle());
        searchResult = (ArrayList<RSSItemModel>) searchEngine.searchAll("24");
        System.err.println(searchResult.get(0).getArticle());
        searchResult = (ArrayList<RSSItemModel>) searchEngine.searchAll("اسپورت");
        System.err.println(searchResult.get(0).getArticle());
    }

}
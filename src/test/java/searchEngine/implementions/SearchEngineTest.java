package searchEngine.implementions;

import database.implementation.DataBase;
import searchEngine.enumarations.SearchInjectQuery;
import searchEngine.interfaces.SearchEngine;
import searchEngine.model.SearchResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        String pubDate = "null";
        String newsWebPage = "http://www.irinn.ir/fa/rss/allnews";
        DataBase.getInstance().execute(SearchInjectQuery.INJECT_DATA_TO_RSS_ITEM.toString(), title, description, link, pubDate, article, newsWebPage);

    }

    @Test
    public void searchTitle() {
        ArrayList<SearchResult> searchResult = (ArrayList<SearchResult>) searchEngine.searchTitle("bye");
        System.err.println(searchResult.get(0).getTitle());
        searchResult = (ArrayList<SearchResult>) searchEngine.searchTitle("10");
        System.err.println(searchResult.get(0).getTitle());
        searchResult = (ArrayList<SearchResult>) searchEngine.searchTitle("کربوهیدرات");
        System.err.println(searchResult.get(0).getTitle());
    }

    @Test
    public void searchArticle() {
        ArrayList<SearchResult> searchResult = (ArrayList<SearchResult>) searchEngine.searchArticle("hello");
        System.err.println(searchResult.get(0).getTitle());
        searchResult = (ArrayList<SearchResult>) searchEngine.searchArticle("24");
        System.err.println(searchResult.get(0).getArticle());
        searchResult = (ArrayList<SearchResult>) searchEngine.searchArticle("اسپورت");
        System.err.println(searchResult.get(0).getArticle());

    }

    @Test
    public void searchAll() {
        ArrayList<SearchResult> searchResult = (ArrayList<SearchResult>) searchEngine.searchAll("hello");
        System.err.println(searchResult.get(0).getTitle());
        searchResult = (ArrayList<SearchResult>) searchEngine.searchAll("24");
        System.err.println(searchResult.get(0).getArticle());
        searchResult = (ArrayList<SearchResult>) searchEngine.searchAll("اسپورت");
        System.err.println(searchResult.get(0).getArticle());
    }

}
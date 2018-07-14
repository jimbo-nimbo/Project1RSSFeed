package searchEngine.implementions;

import database.implementation.DataBase;
import searchEngine.interfaces.SearchEngineRepository;
import searchEngine.model.SearchResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class SearchEngineImplemantationTest
{
    private SearchEngineImplemantation searchEngineImplemention;
    private SearchEngineRepository searchEngineRepository;
    @Before
    public void setup(){
        searchEngineImplemention = new SearchEngineImplemantation(DataBase.getInstance());
        searchEngineRepository = DataBase.getInstance();
        injectDate();
    }

    @After
    public void clean(){
        searchEngineRepository.execute(SearchQuery.DELETE_DATA_FROM_RSS_ITEM.toString(), "testLink");
    }

    @Test
    public void injectDate(){
        String title = "byeTestblahblah";
        String description = "describtion";
        String link = "testLink";
        String article = "helloTestblahblah";
        String pubDate = "null";
        String newsWebPage = "http://www.irinn.ir/fa/rss/allnews";
        searchEngineRepository.execute(SearchQuery.INJECT_DATA_TO_RSS_ITEM.toString(), title, description, link, pubDate, article, newsWebPage);

    }

    @Test
    public void searchTitle() {
        ArrayList<SearchResult> searchResult = (ArrayList<SearchResult>) searchEngineImplemention.searchTitle("bye");
        System.err.println(searchResult.get(0).getTitle());
        searchResult = (ArrayList<SearchResult>) searchEngineImplemention.searchTitle("10");
        System.err.println(searchResult.get(0).getTitle());
        searchResult = (ArrayList<SearchResult>) searchEngineImplemention.searchTitle("کربوهیدرات");
        System.err.println(searchResult.get(0).getTitle());
    }

    @Test
    public void searchArticle() {
        ArrayList<SearchResult> searchResult = (ArrayList<SearchResult>) searchEngineImplemention.searchArticle("hello");
        System.err.println(searchResult.get(0).getTitle());
        searchResult = (ArrayList<SearchResult>) searchEngineImplemention.searchArticle("24");
        System.err.println(searchResult.get(0).getArticle());
        searchResult = (ArrayList<SearchResult>) searchEngineImplemention.searchArticle("اسپورت");
        System.err.println(searchResult.get(0).getArticle());

    }

    @Test
    public void searchAll() {
        ArrayList<SearchResult> searchResult = (ArrayList<SearchResult>) searchEngineImplemention.searchAll("hello");
        System.err.println(searchResult.get(0).getTitle());
        searchResult = (ArrayList<SearchResult>) searchEngineImplemention.searchAll("24");
        System.err.println(searchResult.get(0).getArticle());
        searchResult = (ArrayList<SearchResult>) searchEngineImplemention.searchAll("اسپورت");
        System.err.println(searchResult.get(0).getArticle());
    }

}
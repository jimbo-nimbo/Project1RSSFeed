package dataAnalysis;

import rssRepository.interfaces.RSSItemRepository;
import database.DataBase;
import org.junit.Before;
import org.junit.Test;
import searchEngine.interfaces.SearchEngine;
import webSiteRepository.interfaces.WebSiteRepository;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class DataAnalyserTest {

    RSSItemRepository rssItemRepository;
    WebSiteRepository webSiteRepository;
    DateQuery dateQuery;
    SearchEngine searchEngine;
    DataBase dataBase;
    DataAnalyser dataAnalyser;

    @Before
    public void setup(){
        dataBase = DataBase.getInstance();
        rssItemRepository = dataBase;
        webSiteRepository = dataBase;
        dateQuery = dataBase;
        searchEngine = dataBase;
        dataAnalyser = new DataAnalyser(rssItemRepository, webSiteRepository, searchEngine, dateQuery);
    }

    @Test
    public void getLastDayStatics() {
        HashMap<Date, Integer> myMap = (HashMap<Date, Integer>) dataAnalyser.getLastDayStatics("http://www.irinn.ir/fa/rss/allnews", 3);
        for(Map.Entry<Date, Integer> ans : myMap.entrySet()){
            System.err.println(ans.getKey() + " " + ans.getValue());
        }
    }
}
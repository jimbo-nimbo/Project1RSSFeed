package dataAnalysis;

import core.Core;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class DataAnalyserTest {

    @Test
    public void getLastDayStatics() {
    HashMap<Date, Integer> myMap =
        (HashMap<Date, Integer>)
            Core.getInstance().getDateEngine().getLastDayStatics("http://www.irinn.ir/fa/rss/allnews", 3);
        for(Map.Entry<Date, Integer> ans : myMap.entrySet()){
            System.err.println(ans.getKey() + " " + ans.getValue());
        }
    }
}
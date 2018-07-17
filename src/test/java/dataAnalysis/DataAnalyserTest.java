package dataAnalysis;

import core.Core;
import org.junit.Test;

public class DataAnalyserTest
{

    @Test
    public void getLastDayStatics()
    {
        System.out.println(
                Core.getInstance().getDateEngine().
                        getNewsCountForDay("http://www.irinn.ir/fa/rss/allnews", 3));
    }

}

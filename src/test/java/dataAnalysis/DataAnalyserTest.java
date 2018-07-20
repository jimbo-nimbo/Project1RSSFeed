package dataAnalysis;

import core.Core;
import org.junit.Test;

import java.sql.SQLException;

public class DataAnalyserTest {

  @Test
  public void getLastDayStatics() throws SQLException
  {
    for (int i = 0; i < 10; i++) {
      System.out.println(
          Core.getInstance()
              .getDateEngine()
              .getNewsCountForDay("https://www.isna.ir/rss", i));
    }
  }
}

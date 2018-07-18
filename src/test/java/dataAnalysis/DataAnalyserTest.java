package dataAnalysis;

import core.Core;
import org.junit.Test;

public class DataAnalyserTest {

  @Test
  public void getLastDayStatics() {
    for (int i = 0; i < 10; i++) {
      System.out.println(
          Core.getInstance()
              .getDateEngine()
              .getNewsCountForDay("https://www.isna.ir/rss", i));
    }
  }
}

package dateEngine;

import core.Core;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rssRepository.RSSItemModel;

import java.sql.Date;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DateEngineTest {

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void getNewsForWebsiteByDate() {}

  @Test
  public void getLastDayStatics() {}

  @Test
  public void getTodayNewsForWebsite() {
    Date today = new Date(System.currentTimeMillis());
    ArrayList<RSSItemModel> ans =
            (ArrayList<RSSItemModel>)
                    Core.getInstance()
                            .getDateEngine()
                            .getNewsForWebsiteByDate("http://www.irinn.ir/fa/rss/allnews", today);
    for (RSSItemModel rssItemModel : ans) {
      System.err.println("today news: " + rssItemModel.getTitle());
    }
  }

}
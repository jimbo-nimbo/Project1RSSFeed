package dateEngine;

import core.Core;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rssRepository.RSSItemModel;
import webSiteRepository.NewsWebPageModel;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class DateEngineTest {

  private static final Long DAY_IN_MIL_SECOND = 3600 * 24 * 1000L;
  Core core = Core.getInstance();
  DateEngine dateEngine = core.getDateEngine();

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void getNewsForWebsiteByDate() {
//    Date date = new Date(System.currentTimeMillis());
//    date.setTime(date.getTime() - DAY_IN_MIL_SECOND);
//    ArrayList<RSSItemModel> ans =
//        (ArrayList<RSSItemModel>)
//            Core.getInstance()
//                .getDateEngine()
//                .getNewsForWebsiteByDate("https://www.isna.ir/rss", date);
//    for (RSSItemModel rssItemModel : ans) {
//      System.err.println(
//          "today news: "
//              + rssItemModel.getTitle()
//              + " at time "
//              + rssItemModel.getDate().toString());
//    }
  }

  @Test
  public void getNewsCountForDay() {}

  @Test
  public void getSomeLastRssForWebsiteTest() {
//    NewsWebPageModel newsWebPageModel = core.getWebSiteRepository().getWebsite(1);
//    ArrayList<RSSItemModel> rssItemModels =
//        (ArrayList<RSSItemModel>)
//            dateEngine.getSomeLastRssForWebsite(newsWebPageModel.getLink(), 10);
//    System.out.println(" newsPAge : " + newsWebPageModel.getLink());
//    for(RSSItemModel rssItemModel : rssItemModels){
//      System.out.println(rssItemModel.toString());
//    }
  }

  @Test
  public void getTodayNewsForWebsite() {
//    Date today = new Date(System.currentTimeMillis());
//    ArrayList<RSSItemModel> ans =
//        (ArrayList<RSSItemModel>)
//            Core.getInstance()
//                .getDateEngine()
//                .getNewsForWebsiteByDate("https://www.isna.ir/rss", today);
//    for (RSSItemModel rssItemModel : ans) {
//      System.err.println(
//          "today news: "
//              + rssItemModel.getTitle()
//              + " at time "
//              + rssItemModel.getDate().toString());
//    }
  }
}

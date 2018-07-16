package dataAnalysis;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class DataAnalyser{

  private RSSItemRepository rssItemRepository;
  private WebSiteRepository webSiteRepository;
  private SearchEngine searchEngine;
  private DateQuery dateQuery;
  private static final Long DAY_IN_MILI_SECOND = 3600 * 24 * 1000L;

  public DataAnalyser(
      RSSItemRepository rssItemRepository,
      WebSiteRepository webSiteRepository,
      SearchEngine searchEngine,
      DateQuery dateQuery) {
    this.rssItemRepository = rssItemRepository;
    this.webSiteRepository = webSiteRepository;
    this.searchEngine = searchEngine;
    this.dateQuery = dateQuery;
  }

  @Override
  public Map<Date, Integer> getLastDayStatics(String webLink, int days) {
    Map<Date, Integer> myMap = new HashMap<>();
    Date date = new Date(System.currentTimeMillis());
    for (int i = 0; i < days; i++) {
      Date tempDate = new Date(date.getTime());
      myMap.put(tempDate, dateQuery.getNewsForWebsiteByDate(webLink, date).size());
      date.setTime(date.getTime() - DAY_IN_MILI_SECOND);
    }
    return myMap;
  }
}

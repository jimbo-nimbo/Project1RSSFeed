package dataAnalysis;

import java.sql.Date;
import java.util.Map;

public interface AnalysisRepository {
  public Map<Date, Integer> getLastDayStatics(String webLink, int days);
}

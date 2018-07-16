package dateEngine;

import core.Core;
import core.Service;
import rssRepository.RSSItemModel;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DateEngine extends Service
{
	private static final Long DAY_IN_MILI_SECOND = 3600 * 24 * 1000L;

	public DateEngine(Core core)
	{
		super(core);
		core.setDateEngine(this);
	}

	public List<RSSItemModel> getTenLastNewsForWebsite(String newsWebPage) {
		List<RSSItemModel> ans = new ArrayList<>();
		ResultSet resultSet =
				core.getDatabaseConnectionPool().executeQuery(DateQueries.SELECT_TEN_LAST_RSS_ITEM_BY_WEBSITE.toString(), newsWebPage);
		return core.getRssRepository().takeRssItemFromResultSetWithHashCheck(resultSet);
	}

	public List<RSSItemModel> getNewsForWebsiteByDate(String newsWebPage, Date date) {
		List<RSSItemModel> ans = new ArrayList<>();
		ResultSet resultSet = null;
		try {
			PreparedStatement statement =
					core.getDatabaseConnectionPool().getConnection().
							prepareStatement(DateQueries.SELECT_BY_DATE_NEWS_BY_WEBSITE.toString());
			statement.setString(1, newsWebPage);
			statement.setDate(2, date);
			resultSet = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return core.getRssRepository().takeRssItemFromResultSetWithHashCheck(resultSet);
	}

	public Map<Date, Integer> getLastDayStatics(String webLink, int days)
	{
		Map<Date, Integer> myMap = new HashMap<>();
		Date date = new Date(System.currentTimeMillis());
		for (int i = 0; i < days; i++) {
			Date tempDate = new Date(date.getTime());
			myMap.put(tempDate, getNewsForWebsiteByDate(webLink, date).size());
			date.setTime(date.getTime() - DAY_IN_MILI_SECOND);
		}
		return myMap;
	}
}

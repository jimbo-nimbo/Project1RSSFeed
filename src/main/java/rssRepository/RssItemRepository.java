package rssRepository;

import core.Core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RssItemRepository
{
	private static RssItemRepository ourInstance = new RssItemRepository();
	private HashMap<String, RSSItemModel> rssItemModelHashMap;
	private static Core core = Core.getInstance();

	public static RssItemRepository getInstance()
	{
		return ourInstance;
	}

	private RssItemRepository()
	{
	}


	public void addRSSItem(RSSItemModel rssItemModel) {
		if (rssItemModelHashMap.containsKey(rssItemModel.getLink())) return;
		rssItemModelHashMap.put(rssItemModel.getLink(), rssItemModel);
		try {
			ResultSet resultSet =
					core.getDatabaseConnectionPool().executeQuery(
							RSSItemTableQueries.SELECT_RSS_ITEM_BY_LINK.toString(), rssItemModel.getLink());

			resultSet.beforeFirst();
			if (!resultSet.next()) {
				PreparedStatement addRSS =
						conn.prepareStatement(RSSItemTableQueries.INSERT_INTO_RSS_ITEM_TABLE.toString());
				java.sql.Date date = new java.sql.Date(rssItemModel.getDate().getTime());
				addRSS.setString(1, rssItemModel.getTitle());
				addRSS.setString(2, rssItemModel.getDescription());
				addRSS.setString(3, rssItemModel.getLink());
				addRSS.setDate(4, date);
				addRSS.setString(5, rssItemModel.getArticle());
				addRSS.setString(6, rssItemModel.getNewsWebPage());
				synchronized (rssItemTableLock) {
					addRSS.execute();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<RSSItemModel> getAllRSSData() {
		ArrayList<RSSItemModel> ans = new ArrayList<>();

		try {
			ResultSet resultSet = executeQuery(RSSItemTableQueries.SELECT_ALL_RSS_ITEMS.toString());

			resultSet.beforeFirst();
			while (resultSet.next()) {
				if (rssItemModelHashMap.containsKey(resultSet.getString("link"))) {
					ans.add(rssItemModelHashMap.get(resultSet.getString("link")));
				} else {
					NewsWebPageInformation webPageModel = getWebsite(resultSet.getString("newsWebPage"));

					RSSItemModel rssItemModel = new RSSItemModel(resultSet, webPageModel);
					ans.add(rssItemModel);
					rssItemModelHashMap.put(rssItemModel.getLink(), rssItemModel);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ans;
	}

	public List<RSSItemModel> getRSSDataFromWebSite(String webPageLink) {
		ArrayList<RSSItemModel> ans = new ArrayList<>();
		try {
			ResultSet resultSet =
					executeQuery(
							RSSItemTableQueries.SELECT_FROM_RSSITEM_BY_NEWSPAGE_LINK.toString(), webPageLink);

			resultSet.first();
			NewsWebPageInformation webPageModel = getWebsite(resultSet.getString("newsWebPage"));
			resultSet.beforeFirst();
			while (resultSet.next()) {
				System.out.println(resultSet.getString("newsWebPage"));
				if (rssItemModelHashMap.containsKey(resultSet.getString("link"))) {
					ans.add(rssItemModelHashMap.get(resultSet.getString("link")));
				} else {
					RSSItemModel rssItemModel = new RSSItemModel(resultSet, webPageModel);
					ans.add(rssItemModel);
					rssItemModelHashMap.put(rssItemModel.getLink(), rssItemModel);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ans;
	}

	public String getArticle(String link) {
		try {
			ResultSet resultSet =
					executeQuery(RSSItemTableQueries.SELECT_RSS_ITEM_BY_LINK.toString(), link);

			resultSet.beforeFirst();
			if (resultSet.next()) {
				return resultSet.getString("article");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public RSSItemModel getRSSItem(String link) {
		if (rssItemModelHashMap.containsKey(link)) {
			return rssItemModelHashMap.get(link);
		} else {
			RSSItemModel rssItemModel = null;
			try {
				ResultSet resultSet =
						executeQuery(RSSItemTableQueries.SELECT_RSS_ITEM_BY_LINK.toString(), link);
				rssItemModel = new RSSItemModel(resultSet, getWebsite(resultSet.getString("newsWebPage")));
				rssItemModelHashMap.put(link, rssItemModel);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return rssItemModel;
		}
	}
}

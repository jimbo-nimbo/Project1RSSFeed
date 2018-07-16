package database;

import dateEngine.DateQueries;
import rssRepository.RSSItemModel;
import rssRepository.RSSItemTableQueries;
import rssRepository.interfaces.RSSItemRepository;
import searchEngine.SearchSqlQuery;
import searchEngine.interfaces.SearchEngine;
import webSiteRepository.NewsWebPageModel;
import webSiteRepository.WebsiteTableQueries;
import webSiteRepository.interfaces.WebSiteRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBase implements WebSiteRepository, RSSItemRepository, DateQuery, SearchEngine {




    @Override
    public List<RSSItemModel> getTenLastNewsForWebsite(String newsWebPage) {
        List<RSSItemModel> ans = new ArrayList<>();
        ResultSet resultSet =
                executeQuery(DateQueries.SELECT_TEN_LAST_RSS_ITEM_BY_WEBSITE.toString(), newsWebPage);
        return takeRssItemFromResultSetWithHashCheck(resultSet);
    }

    @Override
    public List<RSSItemModel> getNewsForWebsiteByDate(String newsWebPage, Date date) {
        List<RSSItemModel> ans = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            PreparedStatement statement =
                    conn.prepareStatement(DateQueries.SELECT_BY_DATE_NEWS_BY_WEBSITE.toString());
            statement.setString(1, newsWebPage);
            statement.setDate(2, date);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return takeRssItemFromResultSetWithHashCheck(resultSet);
    }

    @Override
    public List<RSSItemModel> searchTitle(String context) {
        context = "%" + context + "%";
        ResultSet resultSet =
                executeQuery(SearchSqlQuery.SEARCH_FOR_TITLE_IN_RSSITEM.toString(), context);
        return takeRssItemFromResultSetWithHashCheck(resultSet);
    }

    @Override
    public List<RSSItemModel> searchArticle(String context) {
        context = "%" + context + "%";
        ResultSet resultSet =
                executeQuery(SearchSqlQuery.SEARCH_FOR_ARTICLE_IN_RSSITEM.toString(), context);
        return takeRssItemFromResultSetWithHashCheck(resultSet);
    }

    @Override
    public List<RSSItemModel> searchAll(String context) {
        context = "%" + context + "%";
        ResultSet resultSet =
                executeQuery(
                        SearchSqlQuery.SEARCH_FOR_TITLE_OR_ARTICLE_IN_RSSITEM.toString(), context, context);
        return takeRssItemFromResultSetWithHashCheck(resultSet);
    }

    private List<RSSItemModel> takeRssItemFromResultSetWithHashCheck(ResultSet resultSet) {
        List<RSSItemModel> ans = new ArrayList<>();
        try {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                if (rssItemModelHashMap.containsKey(resultSet.getString("link"))) {
                    ans.add(rssItemModelHashMap.get(resultSet.getString("link")));
                } else {
                    RSSItemModel rssItemModel =
                            new RSSItemModel(resultSet, getWebsite(resultSet.getString("newsWebPage")));
                    rssItemModelHashMap.put(rssItemModel.getLink(), rssItemModel);
                    ans.add(rssItemModel);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ans;
    }
}

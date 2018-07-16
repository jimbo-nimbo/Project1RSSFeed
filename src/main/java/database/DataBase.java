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

public class DataBase {

    public List<RSSItemModel> getTenLastNewsForWebsite(String newsWebPage) {
        List<RSSItemModel> ans = new ArrayList<>();
        ResultSet resultSet =
                executeQuery(DateQueries.SELECT_TEN_LAST_RSS_ITEM_BY_WEBSITE.toString(), newsWebPage);
        return takeRssItemFromResultSetWithHashCheck(resultSet);
    }

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




}

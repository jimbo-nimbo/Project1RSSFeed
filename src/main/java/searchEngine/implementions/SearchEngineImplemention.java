package searchEngine.implementions;

import com.mysql.cj.jdbc.PreparedStatement;
import database.DataBaseService;
import database.SearchEngineRepository;
import models.RSSItemModel;
import models.SearchResult;
import searchEngine.SearchSqlQuery;
import searchEngine.interfaces.SearchEngine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchEngineImplemention implements SearchEngine {
    private SearchEngineRepository searchEngineRepository;

    SearchEngineImplemention(SearchEngineRepository searchEngineRepository) {
        this.searchEngineRepository = searchEngineRepository;
    }

    @Override
    public List<SearchResult> searchTitle(String context) {
        context = "%" + context + "%";
        ResultSet resultSet = searchEngineRepository.runQuery(SearchSqlQuery.SEARCH_FOR_TITLE_IN_RSSITEM.toString(), context);
        return convertResultSetToSearchResult(resultSet);
    }

    @Override
    public List<SearchResult> searchArticle(String context) {
        context = "%" + context + "%";
        ResultSet resultSet = searchEngineRepository.runQuery(SearchSqlQuery.SEARCH_FOR_ARTICLE_IN_RSSITEM.toString(), context);
        return convertResultSetToSearchResult(resultSet);
    }

    @Override
    public List<SearchResult> searchAll(String context) {
        context = "%" + context + "%";
        ResultSet resultSet = searchEngineRepository.runQuery(SearchSqlQuery.SEARCH_FOR_TITLE_OR_ARTICLE_IN_RSSITEM.toString(), context, context);
        return convertResultSetToSearchResult(resultSet);
    }

    private List<SearchResult> convertResultSetToSearchResult(ResultSet resultSet) {
        ArrayList<SearchResult> ans = new ArrayList<>();
        try {
            while (resultSet.next()) {

                ans.add(new SearchResult(
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("link"),
                        resultSet.getString("article"),
                        resultSet.getString("pubDate")
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ans;
    }
}

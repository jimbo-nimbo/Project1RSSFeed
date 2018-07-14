package searchEngine.interfaces;

import searchEngine.model.SearchResult;

import java.util.List;

public interface SearchEngine {
    List<SearchResult> searchTitle(String context);
    List<SearchResult> searchArticle(String context);
    List<SearchResult> searchAll(String context);
}

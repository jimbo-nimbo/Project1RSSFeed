package searchEngine.interfaces;

import models.SearchResult;

import java.util.List;

public interface SearchEngine {
    public List<SearchResult> searchTitle(String context);
    public List<SearchResult> searchArticle(String context);
    public List<SearchResult> searchAll(String context);
}

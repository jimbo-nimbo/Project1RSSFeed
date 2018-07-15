package searchEngine.interfaces;

import RSSTable.model.RSSItemModel;

import java.util.List;

public interface SearchEngine {
    List<RSSItemModel> searchTitle(String context);
    List<RSSItemModel> searchArticle(String context);
    List<RSSItemModel> searchAll(String context);
}

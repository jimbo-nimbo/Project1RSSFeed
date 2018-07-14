package dateEngine.interfaces;

import RSSTable.model.RSSItemModel;

import java.util.List;

public interface DateQuery
{
    List<RSSItemModel> getTenLastNewsForWebsite(String newsWebPage);
}

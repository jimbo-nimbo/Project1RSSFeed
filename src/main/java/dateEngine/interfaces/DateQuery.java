package dateEngine.interfaces;

import RSSTable.model.RSSItemModel;

import java.sql.Date;
import java.util.List;

public interface DateQuery
{
    List<RSSItemModel> getTenLastNewsForWebsite(String newsWebPage);
    List<RSSItemModel> getNewsForWebsiteByDate(String newsWebPage, Date date);
}

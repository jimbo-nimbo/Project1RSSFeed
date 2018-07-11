package models;

import java.util.ArrayList;
import java.util.List;

public class NewsWebPageModel implements NewsWebPageInformation
{
    private String url;
    private String targetClass;

    private RSSFeedModel rssFeedModel;
    private List<RSSItemModel> rssItemModels;

    public NewsWebPageModel(String url, String targetClass)
    {
        this.url = url;
        this.targetClass = targetClass;
        rssItemModels = new ArrayList<>();
    }

    @Override
    public String getUrl()
    {
        return url;
    }

    public RSSFeedModel getRssFeedModel()
    {
        return rssFeedModel;
    }

    public void setRssFeedModel(RSSFeedModel rssFeedModel)
    {
        this.rssFeedModel = rssFeedModel;
    }

    @Override
    public String getTargetClass()
    {
        return targetClass;
    }

    @Override
    public String toString()
    {
        return "NewsWebPageModel{" +
                "url='" + url + '\'' +
                ", targetClass='" + targetClass + '\'' +
                '}';
    }
}

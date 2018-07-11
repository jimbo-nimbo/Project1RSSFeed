package models;

public class NewsWebPageModel
{
    private String url;
    private String targetClass;

    public NewsWebPageModel(String url, String targetClass)
    {
        this.url = url;
        this.targetClass = targetClass;
    }

    public String getUrl()
    {
        return url;
    }

    public String getTargetClass()
    {
        return targetClass;
    }
}

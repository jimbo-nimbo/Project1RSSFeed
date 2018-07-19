package cli;

import asg.cliche.Command;
import asg.cliche.ShellFactory;
import core.Core;
import rssRepository.RSSItemModel;
import webSiteRepository.NewsWebPageModel;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Cli
{

    RssService rssService = Core.getInstance().getRssService();

    public static void main(String[] args) throws IOException
    {
        ShellFactory.createConsoleShell("RssProject-Jimbo", "", new Cli()).commandLoop();
    }

    @Command
    public String getWebSites()
    {
        try
        {
            return listToString(rssService.getWebSites().get());
        } catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Command
    public String addWebSite(String link, String targetClass)
    {
        try
        {
            rssService.addWebSite(link, targetClass).get();
            return "website added!";
        } catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Command
    public String update(String link)
    {
        try
        {
            rssService.updateWebsite(link).get();
            return link + "updated!";
        } catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Command
    public String update()
    {
        try
        {
            for (CompletableFuture<Void> cf : rssService.updateAllWebsites())
            {

                cf.get();

            }
            return "all site updated!!";
        } catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Command
    public String getSomeLast(String link, int num)
    {
        try
        {
            return listToString(rssService.getSomeLastNewsForWebsite(link, num).get());
        } catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Command
    public int getNewsCountForDay(String link, int dayPast)
    {
        try
        {
            return rssService.getNewsCountForDate(link, dayPast).get();
        } catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    @Command
    public String getTodayNews(String link)
    {
        try
        {
            return listToString(rssService.getTodayNewsForWebsite(link).get());
        } catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Command
    public String getAllNews()
    {
        try
        {
            return listToString(rssService.getAllRssData().get());
        } catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Command
    public String getNewsForWebSite(String link)
    {
        try
        {
            return listToString(rssService.getWebSiteRssData(link).get());
        } catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Command
    public String searchArticle(String text)
    {
        try
        {
            return listToString(rssService.searchInArticles(text).get());
        } catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Command
    public String searchTitle(String text)
    {
        try
        {
            return listToString(rssService.searchInTitles(text).get());
        } catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Command
    public String search(String text)
    {
        try
        {
            return listToString(rssService.searchBoth(text).get());
        } catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private String listToString(List list)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object o: list)
            stringBuilder.append(o.toString()).append('\n');
        return stringBuilder.toString();
    }
}

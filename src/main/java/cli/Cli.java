package cli;

import asg.cliche.Command;
import asg.cliche.ShellFactory;
import core.Core;
import webSiteRepository.NewsWebPageModel;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Cli
{

    RssService dataBase = Core.getInstance().getRssService();

    public static void main(String[] args) throws IOException
    {
        ShellFactory.createConsoleShell("RssProject-Jimbo", "", new Cli()).commandLoop(); // and three.
    }

    @Command
    public String getWebSites()
    {

        Future<List<NewsWebPageModel>> news = dataBase.getWebSites();
        StringBuilder myString = new StringBuilder();
        try
        {
            List<NewsWebPageModel> myList = news.get();
            for (NewsWebPageModel newsWebPageModel : myList)
            {
                myString.append(newsWebPageModel.toString() + "\n");
            }
        } catch (InterruptedException | ExecutionException e)
        {
            myString.append("some problem happen + \n");
            myString.append(e.getMessage());
        }
        return myString.toString();
    }

    @Command
    public String addWebSite(String link, String targetClass)
    {
        return null;
    }

    @Command
    public String update(String link)
    {
        return null;
    }

    @Command
    public String update()
    {
        dataBase.updateAllWebsites();
        return "done";
    }

    @Command
    public String getLastTen()
    {
        return "hi";
    }


}

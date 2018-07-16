package cli;

import asg.cliche.Command;
import asg.cliche.ShellFactory;
import database.implementation.DataBaseThreadManager;
import websiteTable.model.NewsWebPageModel;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Cli {

    DataBaseThreadManager dataBase = DataBaseThreadManager.getInstance();

    public static void main(String[] args) throws IOException {
        ShellFactory.createConsoleShell("RssProject-Jimbo", "", new Cli()).commandLoop(); // and three.
    }

    @Command
    public String getwebsites() {
        Future<List<NewsWebPageModel>> news = (Future<List<NewsWebPageModel>>) dataBase.getWebSites();
        StringBuilder myString = new StringBuilder();
        try {
            List<NewsWebPageModel> myList = news.get();
            for (NewsWebPageModel newsWebPageModel : myList) {
                myString.append(newsWebPageModel.toString() + "\n");
            }
        } catch (InterruptedException | ExecutionException e) {
            myString.append("some problem happen");
            e.printStackTrace();
        }
        return myString.toString();
    }

    @Command
    public String addwebsite(String link, String targetClass, String timeFormat) {
        try {
            dataBase.addWebSite(link, targetClass, timeFormat);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "done";
    }


}

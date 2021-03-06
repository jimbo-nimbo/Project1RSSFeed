package cli;

import asg.cliche.Command;
import asg.cliche.ShellFactory;
import core.Core;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Cli {

  private RssService rssService = Core.getInstance().getRssService();

  public static void main(String[] args) {
    try {
      ShellFactory.createConsoleShell("RssProject-Jimbo", "", new Cli()).commandLoop();
    } catch (IOException e) {
      Core.getInstance().logToFile(e.getMessage());
      System.err.println(
          "some problem happened to cli! (check the config file for more information)");
    }
  }

  @Command
  public String getWebSites() {
    try {
      return listToString(rssService.getWebSites().get());
    } catch (InterruptedException | ExecutionException e) {
      Core.getInstance().logToFile(e.getMessage());
      return null;
    }
  }

  @Command
  public String addWebSite(String link, String targetClass) {
    try {
      boolean ans = rssService.addWebSite(link, targetClass).get();
      if(ans)
        return "website added!";
      else
        return "website not added";
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Command
  public String update(String link) {
    try {
      rssService.updateWebsite(link).get();
      return link + " updated!";
    } catch (InterruptedException | ExecutionException | SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Command
  public String update() {
    try {
      for (CompletableFuture<Void> cf : rssService.updateAllWebsites()) {

        cf.get();
      }
      return "all site checked!";
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Command
  public String getSomeLast(String link, int num) {
    try {
      return listToString(rssService.getSomeLastNewsForWebsite(link, num).get());
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Command
  public int getNewsCountForDay(String link, int dayPast) {
    try {
      return rssService.getNewsCountForDate(link, dayPast).get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      return 0;
    }
  }

  @Command
  public String getTodayNews(String link) {
    try {
      return listToString(rssService.getTodayNewsForWebsite(link).get());
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Command
  public String getAllNews() {
    try {
      return listToString(rssService.getAllRssData().get());
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Command
  public String getNewsForWebSite(String link) {
    try {
      return listToString(rssService.getWebSiteRssData(link).get());
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Command
  public String searchArticle(String text) {
    try {
      return listToString(rssService.searchInArticles(text).get());
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Command
  public String searchTitle(String text) {
    try {
      return listToString(rssService.searchInTitles(text).get());
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Command
  public String search(String text) {
    try {
      return listToString(rssService.searchBoth(text).get());
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      return null;
    }
  }

  private String listToString(List list) {
    StringBuilder stringBuilder = new StringBuilder();
    for (Object o : list) stringBuilder.append(o.toString()).append('\n');
    return stringBuilder.toString();
  }
}

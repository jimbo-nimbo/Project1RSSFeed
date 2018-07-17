package cli;

import core.Core;
import core.Service;
import database.DatabaseConnectionPool;
import rssRepository.RSSItemModel;
import webSiteRepository.NewsWebPageModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class RssService extends Service {

  private static final int THREAD_NUM = 20;
  private static RssService rssService = null;
  private DatabaseConnectionPool databaseConnectionPool;
  private ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUM);

  public RssService(Core core) {
    super(core);
    databaseConnectionPool = core.getDatabaseConnectionPool();
    ScheduledExecutorService scheduledExecutorService =
        Executors.newSingleThreadScheduledExecutor();
    scheduledExecutorService.scheduleAtFixedRate(() -> updateDataBase(), 0, 100, TimeUnit.SECONDS);
  }

  public void updateSite(NewsWebPageModel newsWebPageModel) {
    Runnable siteUpdate = newsWebPageModel::update;
    executor.execute(siteUpdate);
  }

  public void addWebSite(String websiteLink, String targetClass, String datePattern)
      throws InterruptedException {
    Runnable addWebsite =
        () -> {
          core.getWebSiteRepository()
              .addWebSite(new NewsWebPageModel(websiteLink, targetClass, datePattern));
        };
    executor.execute(addWebsite);
  }

  public Future<List<NewsWebPageModel>> getWebSites() {
    Callable<List<NewsWebPageModel>> getWebsites = () -> core.getWebSiteRepository().getWebsites();
    Future<List<NewsWebPageModel>> websites = executor.submit(getWebsites);
    return websites;
  }

  public void updateDataBase() {
    List<Future<Boolean>> futures = new ArrayList<>();
    for (NewsWebPageModel newsWebPageModel : core.getWebSiteRepository().getWebsites())
      futures.add(updateDatabaseForWebsite(newsWebPageModel.getLink()));
    executor.submit(
        () -> {
          for (Future future : futures) {
            try {
              future.get();
            } catch (InterruptedException | ExecutionException e) {
              e.printStackTrace();
            }
          }
          System.out.println("update completed!");
        });
  }

  public Future<Boolean> updateDatabaseForWebsite(String webSiteLink) {
    Callable<Boolean> callable =
        () -> {
          System.out.println("updating " + webSiteLink + "....");
          core.getWebSiteRepository().getWebsite(webSiteLink).update();
          return true;
        };

    Future<Boolean> future = executor.submit(callable);

    Runnable getNotify =
        () -> {
          try {
            future.get();
            System.out.println("website " + webSiteLink + " updated!");
          } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
          }
        };

    executor.submit(getNotify);
    return future;
  }

  public Future<List<RSSItemModel>> getWebSiteRssData(String webPageLink) {
    Callable<List<RSSItemModel>> callableList =
        () -> core.getRssRepository().getRSSDataFromWebSite(webPageLink);
    Future<List<RSSItemModel>> future = executor.submit(callableList);
    return future;
  }

  public ExecutorService getExecutor() {
    return executor;
  }

  public Future<List<RSSItemModel>> getAllRssData() {
    Callable<List<RSSItemModel>> callable = () -> core.getRssRepository().getAllRSSData();
    Future<List<RSSItemModel>> future = executor.submit(callable);
    return future;
  }
}

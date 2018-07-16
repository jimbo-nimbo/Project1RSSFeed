package database.implementation;

import RSSTable.model.RSSItemModel;
import websiteTable.model.NewsWebPageModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class DataBaseThreadManager {

    private static final int THREAD_NUM = 20;
    private static DataBaseThreadManager dataBaseThreadManager = null;
    private DataBase dataBase;
    private ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUM);

    public static synchronized DataBaseThreadManager getInstance() {
        if (dataBaseThreadManager == null) {
            dataBaseThreadManager = new DataBaseThreadManager(DataBase.getInstance());
        }
        return dataBaseThreadManager;
    }

    private DataBaseThreadManager(DataBase dataBase) {
        this.dataBase = dataBase;
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(() -> updateDataBase(),
        100, TimeUnit.SECONDS);
    }

    public void updateSite(NewsWebPageModel newsWebPageModel) {
        Runnable siteUpdate = newsWebPageModel::update;
        executor.execute(siteUpdate);
    }

    public void addWebSite(String websiteLink, String targetClass, String datePattern) {
        Runnable addWebsite =
                () ->
                        dataBase.addWebSite(
                                new NewsWebPageModel(websiteLink, targetClass, datePattern, dataBase));
        executor.execute(addWebsite);
    }

    public Future<List<NewsWebPageModel>> getWebSites() {
        Callable<List<NewsWebPageModel>> getWebsites = () -> dataBase.getWebsites();
        Future<List<NewsWebPageModel>> websites = executor.submit(getWebsites);
        return websites;
    }

    public void updateDataBase() {
        List<Future<Boolean>> futures = new ArrayList<>();
        for (NewsWebPageModel newsWebPageModel : dataBase.getWebsites())
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
                    dataBase.getWebsite(webSiteLink).update();
                    return true;
                };

        Future future = executor.submit(callable);

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
        Callable<List<RSSItemModel> > callableList = new Callable<List<RSSItemModel>>() {
            @Override
            public List<RSSItemModel> call() throws Exception {
                return dataBase.getRSSDataFromWebSite(webPageLink);
            }
        };
        Future<List<RSSItemModel> > future = executor.submit(callableList);
        return future;
    }

    public Future<List<RSSItemModel>> getAllRssData()
    {
        Callable<List<RSSItemModel>> callable = () -> dataBase.getAllRSSData();
        Future<List<RSSItemModel>> future = executor.submit(callable);
        return future;
    }
}
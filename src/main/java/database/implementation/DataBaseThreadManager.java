package database.implementation;

import websiteTable.model.NewsWebPageModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class DataBaseThreadManager {

    private static DataBaseThreadManager dataBaseThreadManager = null;
    private DataBase dataBase;
    private static final int THREAD_NUM = 10;
    private ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUM);

    public synchronized static DataBaseThreadManager getInstance()
    {
        if (dataBaseThreadManager == null)
        {
            dataBaseThreadManager = new DataBaseThreadManager(DataBase.getInstance());
        }
        return dataBaseThreadManager;
    }

    public DataBaseThreadManager(DataBase dataBase)
    {
        this.dataBase = dataBase;
    }

    public void updateSite(NewsWebPageModel newsWebPageModel){
        Runnable siteUpdate = newsWebPageModel::update;
        executor.execute(siteUpdate);
    }

    public void addWebSite(String websiteLink, String targetClass, String datePattern)
    {
        Runnable addWebsite = () -> dataBase.addWebSite(new NewsWebPageModel(websiteLink,
                targetClass, datePattern, dataBase));
        executor.execute(addWebsite);
    }

    public List<NewsWebPageModel> getWebSites()
    {
        Callable<List<NewsWebPageModel> > getWebsites = () -> dataBase.getWebsites();
        Future<List<NewsWebPageModel> > websites = executor.submit(getWebsites);
        try
        {
            return websites.get();
        } catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void updateDataBase()
    {
        List<Future<Boolean> > futures = new ArrayList<>();
        for ( NewsWebPageModel newsWebPageModel: dataBase.getWebsites())
            futures.add(updateDatabaseForWebsite(newsWebPageModel.getLink()));
        executor.submit(() -> {
            for (Future future: futures)
            {
                try
                {
                    future.get();
                } catch (InterruptedException | ExecutionException e)
                {
                    e.printStackTrace();
                }
            }
            System.out.println("update completed!");
        });
    }

    public Future<Boolean> updateDatabaseForWebsite(String webSiteLink)
    {
        Callable<Boolean> callable = () -> {
            dataBase.getWebsite(webSiteLink).update();
            return true;
        };

        Future future = executor.submit(callable);

        Runnable getNotify = () -> {
            try
            {
                future.get();
                System.out.println("website " + webSiteLink + " updated!");
            } catch (InterruptedException | ExecutionException e)
            {
                e.printStackTrace();
            }
        };

        executor.submit(getNotify);
        return future;
    }

}

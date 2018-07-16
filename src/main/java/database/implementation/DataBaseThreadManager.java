package database.implementation;

import websiteTable.model.NewsWebPageModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataBaseThreadManager {

    private static final int THREAD_NUM = 10;
    private ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUM);

    public void updateSite(NewsWebPageModel newsWebPageModel){
        Runnable siteUpdate = new Runnable() {
            @Override
            public void run() {
                newsWebPageModel.update();
            }
        };
        executor.execute(siteUpdate);
    }

}

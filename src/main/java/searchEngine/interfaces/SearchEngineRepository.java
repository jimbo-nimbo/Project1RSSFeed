package searchEngine.interfaces;

import java.sql.ResultSet;

public interface SearchEngineRepository {
    ResultSet executeQuery(String query, String ...param);
    void execute(String query, String ...param);
}

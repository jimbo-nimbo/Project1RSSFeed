package database;

import java.sql.ResultSet;

public interface SearchEngineRepository {
    public ResultSet runQuery(String query, String ...param);
    public void runExecute(String query, String ...param);
}

package database;

public enum DataBaseCreationQueries
{
    CREATE_DATABASE_IF_NOT_EXISTS(
            "CREATE DATABASE IF NOT EXISTS "
    );

    private String query;

    DataBaseCreationQueries(String query)
    {
        this.query = query;
    }

    @Override
    public String toString()
    {
        return query;
    }
}

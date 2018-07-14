package database.enumaration;

public enum DataBaseCreationQueries
{
    DATABASE_CONFIG_PATH(
            "src/main/resources/config.properties"
    ),
    DATABASE_DRIVER_NAME(
            "com.mysql.cj.jdbc.Driver"
    ),
    DATABASE_TYPE(
            "jdbc:mysql://"
    ),
    CREATE_DATABASE_IF_NOT_EXISTS(
            "CREATE DATABASE IF NOT EXISTS "
    ),
    DATABASE_CONFIG(
            "useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8"
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

package core;

import database.DatabaseConnectionPool;

public class Core
{
	private static Core core;
	public synchronized static Core getInstance()
	{
		if (core == null)
			core = new Core();
		return core;
	}

	private DatabaseConnectionPool databaseConnectionPool;
	private RssRepository rssRepository;

	private Core()
	{
		databaseConnectionPool = DatabaseConnectionPool.getInstance();
	}

	public DatabaseConnectionPool getDatabaseConnectionPool()
	{
		return databaseConnectionPool;
	}
}

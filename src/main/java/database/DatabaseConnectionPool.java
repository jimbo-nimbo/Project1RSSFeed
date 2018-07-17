package database;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import core.Core;
import core.Service;
import rssRepository.RSSItemTableQueries;
import webSiteRepository.WebsiteTableQueries;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnectionPool extends Service
{
	private static DatabaseConnectionPool databaseConnectionPool;
	private ComboPooledDataSource comboPooledDataSource;
	private DataBaseConfig dataBaseConfig;

	public DatabaseConnectionPool(Core core) {
		super(core);
		dataBaseConfig = new DataBaseConfig(DataBaseCreationQueries.DATABASE_CONFIG_PATH.toString());
		try {
			comboPooledDataSource = new ComboPooledDataSource();
			comboPooledDataSource.setDriverClass(DataBaseCreationQueries.DATABASE_DRIVER_NAME.toString());
			comboPooledDataSource.setJdbcUrl(
					DataBaseCreationQueries.DATABASE_TYPE.toString()
							+ dataBaseConfig.getHostIP()
							+ ":"
							+ dataBaseConfig.getHostPort()
							+ "?"
							+ DataBaseCreationQueries.DATABASE_CONFIG.toString());
			comboPooledDataSource.setUser(dataBaseConfig.getUsername());
			comboPooledDataSource.setPassword(dataBaseConfig.getPassword());

			comboPooledDataSource.setMaxStatements(100);
			comboPooledDataSource.setMaxStatementsPerConnection(50);
			comboPooledDataSource.setAutoCommitOnClose(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}

		try (Connection connection = getConnection()) {
			connection
					.createStatement()
					.execute(
							DataBaseCreationQueries.CREATE_DATABASE_IF_NOT_EXISTS.toString()
									+ dataBaseConfig.getDbName()
									+ ";");
			connection
					.createStatement()
					.execute(WebsiteTableQueries.CREATE_WEBSITE_TABLE_IF_NOT_EXISTS.toString());
			connection
					.createStatement()
					.execute(RSSItemTableQueries.CREATE_RSSITEM_TABLE_IF_NOT_EXISTS.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		core.setDatabaseConnectionPool(this);
	}

	public synchronized Connection getConnection() {
		try {
			Connection connection = comboPooledDataSource.getConnection();
			connection.createStatement().execute("USE " + dataBaseConfig.getDbName() + ";");
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ResultSet executeQuery(String query, String... param) {
		ResultSet resultSet = null;
		try (Connection conn = getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			for (int i = 0; i < param.length; i++) {
				statement.setString(i + 1, param[i]);
			}
			resultSet = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	public void execute(String query, String... param) {
		try (Connection conn = getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			for (int i = 0; i < param.length; i++) {
				statement.setString(i + 1, param[i]);
			}
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
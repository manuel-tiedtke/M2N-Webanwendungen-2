package org.m2n.webapplications2.database;

import org.m2n.webapplications2.database.helpers.DbInit;
import org.m2n.webapplications2.exceptions.DatabaseException;
import org.m2n.webapplications2.logging.Logging;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    private static Database instance;

    public static Database getInstance() throws DatabaseException {
        return instance == null ? (instance = new Database()).init() : instance;
    }

    private Connection connection;

    private Database() throws DatabaseException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new DatabaseException("Could not load database driver", e);
        }

        try {
//            connection = DriverManager.getConnection("jdbc:sqlite:/tmp/db.db");
            connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        } catch (SQLException e) {
            throw new DatabaseException("Could not acquire a database connection", e);
        }
    }

    private Database init() throws DatabaseException {
        Logging.debug("[Database] init");

        try {
            ResultSet resultSet = instance.getConnection().createStatement().executeQuery("SELECT version FROM app");

            if (resultSet.next()) {
                String dbVersion = resultSet.getString("version");
                Logging.debug("[Database] Found existing database - Version " + dbVersion);
            } else throw new DatabaseException("Database invalid", null);
        } catch (SQLException e) {
            Logging.debug("[Database] Initializing new database");

            DbInit.createSchema();
            DbInit.insertTestData();
        }

        return this;
    }

    public Connection getConnection() {
        return connection;
    }
}

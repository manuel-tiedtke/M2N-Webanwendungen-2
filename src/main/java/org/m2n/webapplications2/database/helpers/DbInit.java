package org.m2n.webapplications2.database.helpers;

import org.m2n.webapplications2.database.Database;
import org.m2n.webapplications2.exceptions.DatabaseException;

import java.sql.SQLException;
import java.sql.Statement;

public class DbInit {

    public static void createSchema() throws DatabaseException {
        try {
            Statement statement = Database.getInstance().getConnection().createStatement();

            statement.addBatch("CREATE TABLE flashcard (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    question TEXT NOT NULL,\n" +
                "    answer TEXT NOT NULL,\n" +
                "    dueDate TEXT DEFAULT (date('now'))\n" +
                ");");
            statement.addBatch("CREATE TABLE category (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name TEXT UNIQUE NOT NULL,\n" +
                "    tagline TEXT,\n" +
                "    description TEXT\n" +
                ");");
            statement.addBatch("CREATE TABLE flashcard2category (\n" +
                "    flashcardId INTEGER REFERENCES flashcard (id) ON DELETE CASCADE,\n" +
                "    categoryId INTEGER REFERENCES category (id) ON DELETE CASCADE,\n" +
                "    PRIMARY KEY (flashcardId, categoryId)\n" +
                ");");
            statement.addBatch("CREATE TABLE app (\n" +
                "    version TEXT\n" +
                ");");
            statement.addBatch("INSERT INTO app (version) VALUES ('0.1.0-alpha+1');");

            statement.executeBatch();
        } catch (SQLException e) {
            throw new DatabaseException("Could not initialize database (create-schema.sql)", e);
        }
    }

    public static void insertTestData() throws DatabaseException {
        try {
            Statement statement = Database.getInstance().getConnection().createStatement();

            statement.addBatch("INSERT INTO flashcard (question, answer)\n" +
                "VALUES ('U wanna test the default?', 'Yes');");
            statement.addBatch("INSERT INTO flashcard (question, answer, dueDate)\n" +
                "VALUES\n" +
                "('How ru doin?', 'Good', '2021-01-01 10:00:00'),\n" +
                "('Was ist 1 + 1?', '3', '2021-01-01 18:00:00'),\n" +
                "('Ja oder Nein?', 'Nein', '2021-01-01 12:00:00'),\n" +
                "('Ist die Welt klein?', 'Ja', date('now','+5 days'));");
            statement.addBatch("INSERT INTO category (name)\n" +
                "VALUES ('Default');");
            statement.addBatch("INSERT INTO category (name, tagline, description)\n" +
                "VALUES\n" +
                "('Python', 'Dunno', 'Python is awesome'),\n" +
                "('Food', 'Dunno', 'Eat! Or you''ll starve!'),\n" +
                "('Health', 'Dunno', 'See category Food to stay healthy'),\n" +
                "('Hardware', 'Dunno', 'Hardware Hacking Topics');\n");
            statement.addBatch("INSERT INTO flashcard2category (flashcardId, categoryId)\n" +
                "VALUES\n" +
                "(1, 1),\n" +
                "(2, 1),\n" +
                "(3, 2),\n" +
                "(4, 3),\n" +
                "(5, 4), (5, 5);");

            statement.executeBatch();
        } catch (SQLException e) {
            throw new DatabaseException("Could not insert test data (test-data.sql)", e);
        }
    }

}

package org.m2n.webapplications2.database.helpers;

import org.m2n.webapplications2.database.Database;
import org.m2n.webapplications2.exceptions.DatabaseException;
import org.m2n.webapplications2.models.Flashcard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbFlashcard {

    public static int create(Flashcard flashcard) throws DatabaseException {
        Connection connection = Database.getInstance().getConnection();

        try {
            PreparedStatement statement = connection
                .prepareStatement("INSERT INTO flashcard (question, answer) VALUES (?, ?)");

            int i = 1;
            statement.setString(i++, flashcard.getQuestion());
            statement.setString(i, flashcard.getAnswer());

            statement.executeUpdate();

            Statement idStatement = connection.createStatement();
            ResultSet resultSet = idStatement.executeQuery("SELECT last_insert_rowid() id");

            if (resultSet.next()) return resultSet.getInt("id");
            else throw new SQLException("Could not get flashcard id after insert");
        } catch (SQLException e) {
            throw new DatabaseException("Could not insert flashcard", e);
        }
    }

    public static Flashcard get(int id) throws DatabaseException {
        try {
            PreparedStatement statement = Database.getInstance().getConnection()
                .prepareStatement("SELECT * FROM flashcard WHERE id = ?");

            int i = 1;
            statement.setInt(i, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Flashcard flashcard = new Flashcard();
                flashcard.setId(resultSet.getInt("id"));
                flashcard.setQuestion(resultSet.getString("question"));
                flashcard.setAnswer(resultSet.getString("answer"));
                return flashcard;
            } else return null;
        } catch (SQLException e) {
            throw new DatabaseException("Could not select flashcard", e);
        }
    }

    public static List<Flashcard> getForCategory(int categoryId) throws DatabaseException {
        try {
            PreparedStatement statement = Database.getInstance().getConnection()
                .prepareStatement("SELECT f.* FROM flashcard f JOIN flashcard2category f2c ON f2c.flashcardId = f.id where f2c.categoryId = ?");

            int i = 1;
            statement.setInt(i, categoryId);

            ResultSet resultSet = statement.executeQuery();

            List<Flashcard> flashcards = new ArrayList<>();
            while (resultSet.next()) {
                Flashcard flashcard = new Flashcard();
                flashcard.setId(resultSet.getInt("id"));
                flashcard.setQuestion(resultSet.getString("question"));
                flashcard.setAnswer(resultSet.getString("answer"));
                flashcards.add(flashcard);
            }

            return flashcards;
        } catch (SQLException e) {
            throw new DatabaseException("Could not select flashcards for category", e);
        }
    }
}

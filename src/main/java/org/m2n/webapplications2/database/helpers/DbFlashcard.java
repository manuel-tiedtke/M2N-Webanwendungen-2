package org.m2n.webapplications2.database.helpers;

import org.m2n.webapplications2.database.Database;
import org.m2n.webapplications2.exceptions.DatabaseException;
import org.m2n.webapplications2.models.Flashcard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbFlashcard {

    public static void create(Flashcard flashcard) throws DatabaseException {
        try {
            PreparedStatement statement = Database.getInstance().getConnection()
                .prepareStatement("INSERT INTO flashcard (question, answer) VALUES (?, ?)");

            int i = 1;
            statement.setString(i++, flashcard.getQuestion());
            statement.setString(i, flashcard.getAnswer());

            statement.executeUpdate();
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

package org.m2n.webapplications2.database.helpers;

import org.m2n.webapplications2.database.Database;
import org.m2n.webapplications2.exceptions.DatabaseException;
import org.m2n.webapplications2.models.Flashcard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}

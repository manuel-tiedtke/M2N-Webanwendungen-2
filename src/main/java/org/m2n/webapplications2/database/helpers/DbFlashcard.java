package org.m2n.webapplications2.database.helpers;

import org.m2n.webapplications2.database.Database;
import org.m2n.webapplications2.exceptions.DatabaseException;
import org.m2n.webapplications2.models.Flashcard;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DbFlashcard {

    public static final DateFormat DUE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

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

    public static void updateInternal(Flashcard flashcard) throws DatabaseException {
        Connection connection = Database.getInstance().getConnection();

        try {
            PreparedStatement statement = connection
                .prepareStatement("UPDATE flashcard SET question = ?, answer = ?, dueDate = ?, interval = ?, easiness = ? WHERE id = ?");

            int i = 1;
            statement.setString(i++, flashcard.getQuestion());
            statement.setString(i++, flashcard.getAnswer());
            statement.setString(i++, DUE_DATE_FORMAT.format(flashcard.getDueDate()));
            statement.setInt(i++, flashcard.getInterval());
            statement.setDouble(i++, flashcard.getEasiness());
            statement.setInt(i, flashcard.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Could not update flashcard", e);
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
                flashcard.setDueDate(DUE_DATE_FORMAT.parse(resultSet.getString("dueDate")));
                flashcard.setInterval(resultSet.getInt("interval"));
                flashcard.setEasiness(resultSet.getDouble("easiness"));
                return flashcard;
            } else return null;
        } catch (SQLException | ParseException e) {
            throw new DatabaseException("Could not select flashcard", e);
        }
    }

    public static List<Flashcard> getForCategory(int categoryId, boolean onlyDue, int limit) throws DatabaseException {
        try {
            PreparedStatement statement = Database.getInstance().getConnection().prepareStatement("SELECT f.*\n" +
                "FROM flashcard f JOIN flashcard2category f2c ON f2c.flashcardId = f.id\n" +
                "WHERE f2c.categoryId = ?" +  (onlyDue ? "AND date(f.dueDate) <= date('now')\n" : "\n") +
                "ORDER BY dueDate\n" +
                "LIMIT ?");

            int i = 1;
            statement.setInt(i++, categoryId);
            statement.setInt(i, limit);

            ResultSet resultSet = statement.executeQuery();

            List<Flashcard> flashcards = new ArrayList<>();
            while (resultSet.next()) {
                Flashcard flashcard = new Flashcard();
                flashcard.setId(resultSet.getInt("id"));
                flashcard.setQuestion(resultSet.getString("question"));
                flashcard.setAnswer(resultSet.getString("answer"));
                flashcard.setDueDate(DUE_DATE_FORMAT.parse(resultSet.getString("dueDate")));
                flashcard.setInterval(resultSet.getInt("interval"));
                flashcard.setEasiness(resultSet.getDouble("easiness"));
                flashcards.add(flashcard);
            }

            return flashcards;
        } catch (SQLException | ParseException e) {
            throw new DatabaseException("Could not select flashcards for category", e);
        }
    }
}

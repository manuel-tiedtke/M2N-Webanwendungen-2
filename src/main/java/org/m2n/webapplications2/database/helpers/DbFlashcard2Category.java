package org.m2n.webapplications2.database.helpers;

import org.m2n.webapplications2.database.Database;
import org.m2n.webapplications2.exceptions.DatabaseException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbFlashcard2Category {

    public static void addFlashcard2Category(int flashcardId, int categoryId) throws DatabaseException {
        try {
            PreparedStatement statement = Database.getInstance().getConnection()
                .prepareStatement("INSERT INTO flashcard2category (flashcardId, categoryId) VALUES (?, ?)");

            int i = 1;
            statement.setInt(i++, flashcardId);
            statement.setInt(i, categoryId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Could not add flashcard 2 category", e);
        }
    }

}

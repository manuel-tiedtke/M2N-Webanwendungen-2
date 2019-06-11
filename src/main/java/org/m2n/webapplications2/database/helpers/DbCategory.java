package org.m2n.webapplications2.database.helpers;

import org.m2n.webapplications2.database.Database;
import org.m2n.webapplications2.exceptions.DatabaseException;
import org.m2n.webapplications2.models.Category;
import org.m2n.webapplications2.models.Flashcard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbCategory {

    public static void create(Category category) throws DatabaseException {
        try {
            PreparedStatement statement = Database.getInstance().getConnection()
                .prepareStatement("INSERT INTO category (name) VALUES (?)");

            int i = 1;
            statement.setString(i, category.getName());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Could not insert category", e);
        }
    }

    public static List<Category> getAll() throws DatabaseException {
        try {
            PreparedStatement statement = Database.getInstance().getConnection()
                .prepareStatement("SELECT * FROM category");

            ResultSet resultSet = statement.executeQuery();

            List<Category> categories = new ArrayList<>();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                categories.add(category);
            }

            return categories;
        } catch (SQLException e) {
            throw new DatabaseException("Could not select flashcard", e);
        }
    }

}

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

    public static void update(int categoryId, Category category) throws DatabaseException {
        try {
            PreparedStatement statement = Database.getInstance().getConnection()
                .prepareStatement("UPDATE category SET name = ?, tagline = ?, description = ? WHERE id = ?");

            int i = 1;
            statement.setString(i++, category.getName());
            statement.setString(i++, category.getTagline());
            statement.setString(i++, category.getDescription());
            statement.setInt(i, categoryId);


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
                category.setTagline(resultSet.getString("tagline"));
                category.setDescription(resultSet.getString("description"));
                categories.add(category);
            }

            return categories;
        } catch (SQLException e) {
            throw new DatabaseException("Could not select flashcard", e);
        }
    }

    public static Category get(int categoryId) throws DatabaseException {
        try {
            PreparedStatement statement = Database.getInstance().getConnection()
                .prepareStatement("SELECT * FROM category WHERE id = ?");

            int i = 1;
            statement.setInt(i, categoryId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Category category = new Category();
                category.setName(resultSet.getString("name"));
                category.setDescription((resultSet.getString("description")));
                category.setTagline(resultSet.getString("tagline"));

                return category;
            } else return null;
        } catch (SQLException e) {
            throw new DatabaseException("Could not select category", e);
        }
    }

}

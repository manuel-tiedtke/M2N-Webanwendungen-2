package org.m2n.webapplications2.database.helpers;

import org.m2n.webapplications2.database.Database;
import org.m2n.webapplications2.exceptions.DatabaseException;
import org.m2n.webapplications2.models.Category;
import org.m2n.webapplications2.models.Flashcard;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbCategory {

    public static int create(Category category) throws DatabaseException {
        Connection connection = Database.getInstance().getConnection();

        try {
            PreparedStatement statement = connection
                .prepareStatement("INSERT INTO category (name, tagline, description) VALUES (?, ?, ?)");

            int i = 1;
            statement.setString(i++, category.getName());
            statement.setString(i++, category.getTagline());
            statement.setString(i, category.getDescription());

            statement.executeUpdate();

            Statement idStatement = connection.createStatement();
            ResultSet resultSet = idStatement.executeQuery("SELECT last_insert_rowid() id");

            if (resultSet.next()) return resultSet.getInt("id");
            else throw new SQLException("Could not get category id after insert");
        } catch (SQLException e) {
            throw new DatabaseException("Could not insert category", e);
        }
    }

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
                .prepareStatement("SELECT * FROM category ORDER BY name");

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
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                category.setDescription((resultSet.getString("description")));
                category.setTagline(resultSet.getString("tagline"));

                return category;
            } else return null;
        } catch (SQLException e) {
            throw new DatabaseException("Could not select category", e);
        }
    }

    public static Category getForName(String name) throws DatabaseException {
        try {
            PreparedStatement statement = Database.getInstance().getConnection()
                .prepareStatement("SELECT * FROM category WHERE name = ?");

            int i = 1;
            statement.setString(i, name);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                category.setDescription((resultSet.getString("description")));
                category.setTagline(resultSet.getString("tagline"));

                return category;
            } else return null;
        } catch (SQLException e) {
            throw new DatabaseException("Could not get category id for name", e);
        }
    }

    public static List<Category> getForFlashcard(int flashcardId) throws DatabaseException {
        try {
            PreparedStatement statement = Database.getInstance().getConnection()
                .prepareStatement("SELECT c.* FROM category c JOIN flashcard2category f2c ON f2c.categoryId = c.id where f2c.flashcardId = ? ORDER BY name");

            int i = 1;
            statement.setInt(i, flashcardId);

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
            throw new DatabaseException("Could not select categories for flashcard", e);
        }
    }

}

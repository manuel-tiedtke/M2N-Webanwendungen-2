package org.m2n.webapplications2.controllers.api;

import org.m2n.webapplications2.database.helpers.DbCategory;
import org.m2n.webapplications2.database.helpers.DbFlashcard;
import org.m2n.webapplications2.exceptions.DatabaseException;
import org.m2n.webapplications2.exceptions.ValidationException;
import org.m2n.webapplications2.logging.Logging;
import org.m2n.webapplications2.models.Category;
import org.m2n.webapplications2.models.Flashcard;

import javax.ws.rs.*;
import java.util.List;

@Path("/api/category")
public class CategoryController {

    @GET
    @Produces("application/json")
    public List<Category> getAll() throws DatabaseException {
        Logging.debug("[CategoryController] getAll");

        return DbCategory.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Category get(@PathParam("id") int categoryId) throws DatabaseException {
        Logging.debug("[CategoryController] get");

        return DbCategory.get(categoryId);
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public void update(@PathParam("id") int categoryId, Category category) throws DatabaseException, ValidationException {
        Logging.debug("[CategoryController] update");

        if (category == null ||
            category.getName() == null || category.getName().equals(""))
            throw new ValidationException("Not a valid category");

        DbCategory.update(categoryId, category);
    }

    @GET
    @Path("/{id}/flashcards")
    @Produces("application/json")
    public List<Flashcard> getFlashcards(@PathParam("id") int categoryId) throws DatabaseException {
        Logging.debug("[CategoryController] getFlashcards");

        return DbFlashcard.getForCategory(categoryId);
    }
}

package org.m2n.webapplications2.controllers.api;

import org.m2n.webapplications2.database.helpers.DbCategory;
import org.m2n.webapplications2.exceptions.DatabaseException;
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
    public void update(@PathParam("id") int categoryId, Category category) throws DatabaseException {
        Logging.debug("[CategoryController] update");

        DbCategory.update(categoryId, category);
    }
}

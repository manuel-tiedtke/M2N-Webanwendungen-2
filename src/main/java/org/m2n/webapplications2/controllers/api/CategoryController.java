package org.m2n.webapplications2.controllers.api;

import org.m2n.webapplications2.database.helpers.DbCategory;
import org.m2n.webapplications2.exceptions.DatabaseException;
import org.m2n.webapplications2.logging.Logging;
import org.m2n.webapplications2.models.Category;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/api/category")
public class CategoryController {

    @GET
    @Produces("application/json")
    public List<Category> getAll() throws DatabaseException {
        Logging.debug("[CategoryController] getAll");

        return DbCategory.getAll();
    }

}
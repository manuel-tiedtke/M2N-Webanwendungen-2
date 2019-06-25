package org.m2n.webapplications2.controllers.api;

import org.m2n.webapplications2.database.helpers.DbCategory;
import org.m2n.webapplications2.database.helpers.DbFlashcard;
import org.m2n.webapplications2.database.helpers.DbFlashcard2Category;
import org.m2n.webapplications2.exceptions.DatabaseException;
import org.m2n.webapplications2.exceptions.ValidationException;
import org.m2n.webapplications2.logging.Logging;
import org.m2n.webapplications2.models.Category;
import org.m2n.webapplications2.models.Flashcard;

import javax.ws.rs.*;
import java.util.List;

@Path("/api/flashcard")
public class FlashcardController {

    @GET
    @Produces("application/json")
    public List<Flashcard> getForCategory(@QueryParam("category") int categoryId) throws DatabaseException {
        Logging.debug("[FlashcardController] getForCategory");

        return DbFlashcard.getForCategory(categoryId);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public int create(Flashcard flashcard) throws ValidationException, DatabaseException {
        Logging.debug("[FlashcardController] create");

        if (flashcard == null ||
            flashcard.getAnswer() == null || flashcard.getAnswer().equals("") ||
            flashcard.getQuestion() == null || flashcard.getQuestion().equals(""))
            throw new ValidationException("Not a valid flashcard");

        return DbFlashcard.create(flashcard);
    }

    @POST
    @Path("/{id}/categories")
    @Consumes("application/json")
    public void addCategories(@PathParam("id") int flashcardId, Category[] categories) throws DatabaseException {
        Logging.debug("[FlashcardController] addCategories");

        for (Category category : categories) {
            Category categoryFromDb = DbCategory.getForName(category.getName());

            int categoryId;
            if (categoryFromDb == null) {
                categoryId = DbCategory.create(category);
            } else {
                categoryId = categoryFromDb.getId();
            }

            DbFlashcard2Category.addFlashcard2Category(flashcardId, categoryId);
        }
    }

}

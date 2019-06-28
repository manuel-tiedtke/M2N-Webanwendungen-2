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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Path("/api/flashcard")
public class FlashcardController {

    public static final double EASINESS_MAX = 2.8;
    public static final double EASINESS_GAIN = 0.1;
    public static final double EASINESS_LOSS = 0.5;
    public static final double EASINESS_MIN = 1.3;

    public static final int RATING_MAX = 3;
    public static final int RATING_EVEN = 2;
    public static final int RATING_WRONG = 0;

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
    @Path("/{id}/answer")
    @Consumes("application/json")
    public void submitAnswer(@PathParam("id") int flashcardId, int rating) throws DatabaseException {
        Logging.debug("[FlashcardController] submitAnswer");

        Flashcard flashcard = DbFlashcard.get(flashcardId);

        double quadraticFactor = (EASINESS_GAIN + EASINESS_LOSS - EASINESS_LOSS*RATING_MAX/RATING_EVEN) / (RATING_MAX*RATING_MAX - RATING_MAX*RATING_EVEN);
        double linearFactor = EASINESS_LOSS/RATING_EVEN - RATING_EVEN*quadraticFactor;
        double deltaEasiness = quadraticFactor*rating*rating + linearFactor*rating - EASINESS_LOSS;
        double easiness = flashcard.getEasiness() + deltaEasiness;
        easiness = Math.max(EASINESS_MIN, Math.min(easiness, EASINESS_MAX));

        int interval;
        if (rating <= RATING_WRONG) interval = 1;
        else interval = (int)(flashcard.getInterval() * easiness);

        Date dueDate = new Date();
        if (rating >= RATING_EVEN) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dueDate);
            calendar.add(Calendar.DATE, interval);
            dueDate = calendar.getTime();
        }

        flashcard.setDueDate(dueDate);
        flashcard.setInterval(interval);
        flashcard.setEasiness(easiness);

        DbFlashcard.updateInternal(flashcard);
    }

    @GET
    @Path("/{id}/categories")
    @Produces("application/json")
    public List<Category> getCategories(@PathParam("id") int flashcardId) throws DatabaseException {
        Logging.debug("[FlashcardController] getCategories");

        return DbCategory.getForFlashcard(flashcardId);
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

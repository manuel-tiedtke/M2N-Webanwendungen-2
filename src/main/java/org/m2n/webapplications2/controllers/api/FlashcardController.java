package org.m2n.webapplications2.controllers.api;

import org.m2n.webapplications2.database.helpers.DbFlashcard;
import org.m2n.webapplications2.exceptions.DatabaseException;
import org.m2n.webapplications2.exceptions.ValidationException;
import org.m2n.webapplications2.logging.Logging;
import org.m2n.webapplications2.models.Flashcard;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/api/flashcard")
public class FlashcardController {

    @POST
    @Consumes("application/json")
    public void create(Flashcard flashcard) throws ValidationException, DatabaseException {
        Logging.debug("[FlashcardController] create");

        if (flashcard == null ||
            flashcard.getAnswer() == null || flashcard.getAnswer().equals("") ||
            flashcard.getQuestion() == null || flashcard.getQuestion().equals(""))
            throw new ValidationException("Not a valid flashcard");

        DbFlashcard.create(flashcard);
    }

}

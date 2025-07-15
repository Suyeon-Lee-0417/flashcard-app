package model;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestFlashcard {
    private Flashcard testCard;

    @BeforeEach
    void runBefore() {
        testCard = new Flashcard("Wild(slang)", "Crazy");
        assertTrue(testCard.getDescription().equals("Crazy"));
        assertTrue(testCard.getTerm().equals("Wild(slang)"));
    }

    @Test
    void testFlashCard() {

        // test constructor
        assertTrue(testCard.getTerm().equals("Wild(slang)"));
        assertTrue(testCard.getDescription().equals("Crazy"));
        assertFalse(testCard.getLearningStatus());

        // test copy constructor
        Flashcard testCard2 = new Flashcard(testCard);
        assertTrue(testCard2.getTerm().equals("Wild(slang)"));
        assertTrue(testCard2.getDescription().equals("Crazy"));
        assertFalse(testCard2.getLearningStatus());

        // test constructor with isLearned parameter
        Flashcard testCard3 = new Flashcard("term", "description", false);
        assertTrue(testCard3.getTerm().equals("term"));
        assertTrue(testCard3.getDescription().equals("description"));
        assertFalse(testCard3.getLearningStatus());
    }

    @Test
    void testEditDescription() {
        assertTrue(testCard.getDescription().equals("Crazy"));
        testCard.editDescription("Nuts");
        assertTrue(testCard.getDescription().equals("Nuts"));
    }

    @Test
    void testUpdateAfterReview() {
        // add a card, set as it's learned. 
        // Update its status to still learning
        assertFalse(testCard.getLearningStatus());
        testCard.updateAfterReview(true);
        assertTrue(testCard.getLearningStatus());

        // add a card
        // Update its status as learned.
        testCard.markAsStillLearning();
        testCard.updateAfterReview(false);
        assertFalse(testCard.getLearningStatus());
    }

    @Test
    void testMarkAsLearned() {
        assertFalse(testCard.getLearningStatus());
        testCard.markAsLearned();
        assertTrue(testCard.getLearningStatus());

        // try marking as Learned once again
        testCard.markAsLearned();
        assertTrue(testCard.getLearningStatus());
    }

    @Test
    void testMarkAsStillLearning() {
        testCard.markAsLearned();
        assertTrue(testCard.getLearningStatus());
        testCard.markAsStillLearning();
        assertFalse(testCard.getLearningStatus());

        // try mark as still learning once again
        testCard.markAsStillLearning();
        assertFalse(testCard.getLearningStatus());
    }

    @Test
    void testToJson() {
        JSONObject json = testCard.toJson();
        assertTrue(json.getString("term").equals("Wild(slang)"));
        assertTrue(json.getString("description").equals("Crazy"));
    }
}

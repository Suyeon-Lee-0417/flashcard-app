package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestFCardCollection {

    private FCardCollection testCollection1;
    private Flashcard testCard1;
    private Flashcard testCard2;

    @BeforeEach
    void runBefore() {
        testCollection1 = new FCardCollection("Sydney's flashcard list");
        testCard1 = new Flashcard("Stoked", "Very Excited");
        testCard2 = new Flashcard("Wild", "Crazy");
    }

    @Test
    void TestConstructor() {
        assertEquals(testCollection1.getNumFCards(), 0);
        assertTrue(testCollection1.getName().equals("Sydney's flashcard list"));
    }

    @Test
    void TestAddNewCard() {
        testCollection1.addNewCard(testCard1);
        assertEquals(testCollection1.getNumFCards(), 1);
        testCollection1.addNewCard(testCard2);
        assertEquals(testCollection1.getNumFCards(), 2);
    }

    @Test
    void TestDeleteAndGetFlashCard() {
        testCollection1.addNewCard(testCard1);
        assertEquals(testCollection1.getNumFCards(), 1);
        testCollection1.addNewCard(testCard2);
        assertEquals(testCollection1.getNumFCards(), 2);

        Flashcard tempCard1 = testCollection1.getFlashCard(0);
        Flashcard tempCard2 = testCollection1.getFlashCard(1);
        assertTrue(testCard1.getTerm().equals(tempCard1.getTerm()));
        assertTrue(testCard2.getTerm().equals(tempCard2.getTerm()));

        testCollection1.deleteCard(0);
        assertFalse(testCollection1.getFlashCard(0).getTerm().equals(tempCard1.getTerm()));

        testCollection1.deleteCard(-1);
        assertFalse(testCollection1.getFlashCard(0).getTerm().equals(tempCard1.getTerm()));
    }

    @Test
    void TestMarkThisCardAsLearned() {
        // add a card, mark as Learned, check if the
        // stillLearning is correctly updated
        testCollection1.addNewCard(new Flashcard("Stoked", "Very Excited"));
        assertEquals(testCollection1.getNumFCards(), 1);
        testCollection1.markThisCardAsLearned(0);
        assertEquals(testCollection1.getNumFCards(), 1);
    }

    @Test
    void TestMarkThisCardAsStillLearning() {
        // add a card, mark as learned
        // then mark as still learning, check if the
        // stillLearning is correctly updated
        testCollection1.addNewCard(testCard1);
        assertEquals(testCollection1.getNumFCards(), 1);

        testCollection1.markThisCardAsLearned(0);
        assertEquals(testCollection1.getNumFCards(), 1);

        testCollection1.markThisCardStillLearning(0);
        ;
        assertEquals(testCollection1.getNumFCards(), 1);
    }

    @Test
    void TestDeleteCard() {
        // add a card and delete the card.
        testCollection1.addNewCard(testCard1);
        assertEquals(testCollection1.getNumFCards(), 1);
        testCollection1.deleteCard(0);
        assertEquals(testCollection1.getNumFCards(), 0);

        // try delete a card that doesn't exist
        testCollection1.deleteCard(0);
        assertEquals(testCollection1.getNumFCards(), 0);

        // try delete a card with a empty collection
        testCollection1.addNewCard(testCard2);
        assertEquals(testCollection1.getNumFCards(), 1);
        testCollection1.deleteCard(2);
        assertEquals(testCollection1.getNumFCards(), 1);
    }

    @Test
    void TestDeleteCardLearned() {
        // delete learned flashcard in the collection
        testCollection1.addNewCard(new Flashcard("Stoked", "Very Excited"));
        testCollection1.addNewCard(new Flashcard("Howdy", "How are you?"));

        assertEquals(testCollection1.getNumFCards(), 2);

        testCollection1.markThisCardAsLearned(0);
        testCollection1.deleteCard(0);
    }

    @Test
    void TestGetNumStillLearning() {
        // add 3 Flashcards
        // mark as Learned
        // check if numberStillLearning decreased.
        testCollection1.addNewCard(new Flashcard("Stoked", "Very Excited"));
        testCollection1.addNewCard(new Flashcard("Wild", "Crazy"));

    }

    @Test
    void TestgatherFlashcardsToReview() {
        // add cards
        // check the number of cards to review
        // mark some cards as learned
        // check the number of cards to review
        testCollection1.addNewCard(testCard1);
        testCollection1.addNewCard(testCard2);
        assertEquals(testCollection1.gatherFlashcardsToReview().size(), 2);
        testCollection1.markThisCardAsLearned(0);
        assertEquals(testCollection1.gatherFlashcardsToReview().size(), 1);
    }

    @Test
    void TestShowEveryFlashcards() {
        // add cards
        // check the size of cardList
        // mark some cards as learned
        // check the size of cardList remains same
        testCollection1.addNewCard(new Flashcard("Stoked", "Very Excited"));
        testCollection1.addNewCard(new Flashcard("Wild", "Crazy"));
        assertEquals(testCollection1.showEveryFlashcards().size(), 2);
        testCollection1.markThisCardAsLearned(0);
        assertEquals(testCollection1.showEveryFlashcards().size(), 2);
    }

    @Test
    void TestToJson() {
        testCollection1.addNewCard(testCard1);
        testCollection1.addNewCard(testCard2);
        JSONObject json = testCollection1.toJson();
        assertTrue(json.getString("name").equals("Sydney's flashcard list"));
        JSONArray jsonArray = json.getJSONArray("flashcards");
        assertEquals(jsonArray.length(), 2);
        JSONObject jsonCard1 = jsonArray.getJSONObject(0);
        JSONObject jsonCard2 = jsonArray.getJSONObject(1);
        assertTrue(jsonCard1.getString("term").equals("Stoked"));
        assertTrue(jsonCard1.getString("description").equals("Very Excited"));

        assertTrue(jsonCard2.getString("term").equals("Wild"));
        assertTrue(jsonCard2.getString("description").equals("Crazy"));
    }

    @Test
    void TestGetFCards() {
        testCollection1.addNewCard(testCard1);
        testCollection1.addNewCard(testCard2);

        List<Flashcard> tempListFalse = testCollection1.getFCards(false);
        assertEquals(tempListFalse.size(), 2);
        assertTrue(tempListFalse.get(0).getTerm().equals("Stoked"));
        assertTrue(tempListFalse.get(1).getTerm().equals("Wild"));
        
        List<Flashcard> tempListTrue = testCollection1.getFCards(true);
        assertEquals(tempListTrue.size(), 2);
        assertTrue(tempListTrue.get(0).getTerm().equals("Stoked"));
        assertTrue(tempListTrue.get(1).getTerm().equals("Wild"));
    }

    @Test
    void testGetNumOfLearned() {
        testCollection1.addNewCard(new Flashcard("Stoked", "Very Excited"));
        testCollection1.addNewCard(new Flashcard("Wild", "Crazy"));
        testCollection1.markThisCardAsLearned(0);
        assertTrue(testCollection1.getFlashCard(0).getLearningStatus());
        assertEquals(1, testCollection1.getNumOfLearned());
        testCollection1.markThisCardAsLearned(1);
        assertTrue(testCollection1.getFlashCard(1).getLearningStatus());
        assertEquals(2, testCollection1.getNumOfLearned());
    }
}

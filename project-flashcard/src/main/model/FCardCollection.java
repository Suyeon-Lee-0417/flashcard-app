package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

/**
 * 
 * FCardCollection class collects Flashcard objects.
 * Gather cards into a list for review session of flashcards 'still in
 * learning';
 * Can delete/edit/add flashcards in the collection;
 * 
 * @version phase 1, phase 2
 * @author Sydney Lee
 */
public class FCardCollection implements Writable {
    private ArrayList<Flashcard> flashcards;
    private String name;

    /*
     * EFFECTS: creates an empty list of Flashcard;
     */
    public FCardCollection(String name) {
        this.name = name;
        flashcards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    /*
     * REQUIRES: newCard != null
     * MODIFIES: this
     * EFFECTS: add a new Flashcard into the cardList;
     */
    public void addNewCard(Flashcard newCard) {
        flashcards.add(newCard);
        EventLog.getInstance().logEvent(new Event("Added a new card, Card: " + newCard.getTerm()));
    }

    /*
     * REQUIRES: cardList must contain at least one Flashcard;
     * MODIFIES: this
     * EFFECTS: delete a target Flashcard from cardList;
     * Nothing happens if the index is invalid for cardList
     */
    public void deleteCard(int index) {
        Flashcard removed = null;
        if (flashcards.size() > 0 && index <= flashcards.size() - 1 && index >= 0) {
            removed = flashcards.remove(index);
        }
        try {
            EventLog.getInstance().logEvent(new Event("Deleted the card, Card: " + removed.getTerm()));
        } catch (NullPointerException e) {
            EventLog.getInstance().logEvent(new Event("Failed to delete. Invalid card selected"));
        }
    }

    /*
     * REQUIRES: cardList should contains at least one Flashcard;
     * EFFECTS: Return a list of Flashcards that are still in learning.
     */
    public ArrayList<Flashcard> gatherFlashcardsToReview() {
        ArrayList<Flashcard> cardsToReview = new ArrayList<>();
        for (Flashcard card : flashcards) {
            if (!card.getLearningStatus()) {
                cardsToReview.add(new Flashcard(card));
            }
        }
        return cardsToReview;
    }

    /*
     * REQUIRES: cardList should have at least one Flashcard;
     * (cardList length > 0)
     * EFFECTS: shows the entire list of Flashcard.
     */
    public ArrayList<Flashcard> showEveryFlashcards() {
        ArrayList<Flashcard> listToShow = new ArrayList<>();
        for (Flashcard card : flashcards) {
            listToShow.add(new Flashcard(card));
        }
        return listToShow;
    }

    /*
     * EFFECTS: returns the number of Flashcards in the cardList.
     */
    public int getNumFCards() {
        return flashcards.size();
    }

    /*
     * REQUIRES: index < cardList.size()
     */
    public void markThisCardAsLearned(int index) {
        Flashcard card = flashcards.get(index);
        card.markAsLearned();
        EventLog.getInstance().logEvent(new Event("Marked a card, Card: " + card.getTerm() + ", as Learned"));
    }

    /*
     * REQUIRES: index < cardList.size()
     */
    public void markThisCardStillLearning(int index) {
        Flashcard card = flashcards.get(index);
        card.markAsStillLearning();
        EventLog.getInstance().logEvent(new Event("Marked a card, Card: " + card.getTerm() + ", as Still Learning"));
    }

    /*
     * REQUIRES: index < cardList.size()
     */
    public Flashcard getFlashCard(int index) {
        return flashcards.get(index);
    }

    // EFFECTS: returns this as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("flashcards", fcardsToJson());
        return json;
    }

    // EFFECTS: returns flashcards in this FCardCollection as a JSON array
    private JSONArray fcardsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Flashcard card : flashcards) {
            jsonArray.put(card.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns an unmodifiable list of flashcards in this FCardCollection
    // Referenced from the JsonSerialization Demo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    public List<Flashcard> getFCards(boolean update) {
        if (!update) {
            EventLog.getInstance().logEvent(new Event("Displaying all cards in your flashcards"));
        }
        return Collections.unmodifiableList(flashcards);
    }

    /*
     * REQUIRES: flashcards size > 0
     * EFFECTS: Returns the number of the cards that has been learned.
     */
    public int getNumOfLearned() {
        int learned = 0;
        for (Flashcard card : flashcards) {
            if (card.getLearningStatus()) {
                learned++;
            }
        }
        return learned;
    }
}

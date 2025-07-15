package model;

import org.json.JSONObject;

/**
 * Flashcard class that saves terms and description;
 * Has two learning status- Learned and Still in Learning;
 * 
 * @version phase 1
 * @author Sydney Lee
 */

public class Flashcard {
    private boolean isLearned;
    private String term = "";
    private String description = "";

    /*
     * REQUIRES: both term and description should has length > 0
     * EFFECTS: set the learning status of flashcard to false
     * term of this card is set to 'term';
     * term of description is set to 'description';
     */
    public Flashcard(String term, String description) {
        this.term = term;
        this.description = description;
        isLearned = false;
    }

    public Flashcard(String term, String description, boolean isLearned) {
        this.term = term;
        this.description = description;
        this.isLearned = isLearned;
    }

    /*
     * REQUIRES: other must be instantiated
     * EFFECTS: create a deep copy of 'other' Flashcard object
     */
    public Flashcard(Flashcard other) {
        this.term = other.term;
        this.description = other.description;
        this.isLearned = other.isLearned;
    }

    /*
     * REQUIRES: newDescription has length > 0
     * MODIFIES: this
     * EFFECTS: replace the original description with newDescription
     */
    public void editDescription(String newDescription) {
        this.description = newDescription;
    }

    /*
     * REQUIRES: isLearned is false
     * MODIFIES: this
     * EFFECTS: mark the status isLearned true
     */
    public void markAsLearned() {
        if (!this.isLearned) {
            this.isLearned = true;
        }
    }

    /*
     * REQUIRES: isLearned is true
     * MODIFIES: this
     * EFFECTS: mark the status isLearned false
     */
    public void markAsStillLearning() {
        if (this.isLearned) {
            this.isLearned = false;
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: check if this card is memorized; mark stillLearning as
     * false if learned is False. Otherwise, mark as Learned.
     */
    public void updateAfterReview(boolean learned) {
        if (!learned) {
            markAsStillLearning();
        } else {
            markAsLearned();
        }
    }

    public String getDescription() {
        return this.description;
    }

    public String getTerm() {
        return this.term;
    }

    public boolean getLearningStatus() {
        return isLearned;
    }

    // EFFECTS: make a JSON object representation of this
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("term", term);
        json.put("description", description);
        json.put("isLearned?", isLearned);
        return json;
    }

}

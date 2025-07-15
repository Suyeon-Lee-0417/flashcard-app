package persistence;

import model.Flashcard;

import static org.junit.jupiter.api.Assertions.assertEquals;


// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonTest {
    protected void checkFlashcard(String term, String description, Boolean isLearned, Flashcard card) {
        assertEquals(term, card.getTerm());
        assertEquals(description, card.getDescription());
        assertEquals(isLearned, card.getLearningStatus());
    }
}

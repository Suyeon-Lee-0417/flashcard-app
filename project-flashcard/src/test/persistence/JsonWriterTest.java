package persistence;

import model.Flashcard;
import model.FCardCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            FCardCollection fc = new FCardCollection("Sydney's flashcard list");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyFCardCollection() {
        try {
            FCardCollection fc = new FCardCollection("Sydney's flashcard list");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyFCardCollection.json");
            writer.open();
            writer.write(fc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyFCardCollection.json");
            fc = reader.read();
            assertEquals(0, fc.getNumFCards());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralFCardCollection() {
        try {
            FCardCollection fCardList = new FCardCollection("Sydney's flashcard list");
            fCardList.addNewCard(new Flashcard("jiwon", "babo"));
            fCardList.addNewCard(new Flashcard("suyeon", "cute"));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralFCardCollection.json");
            writer.open();
            writer.write(fCardList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralFCardCollection.json");
            fCardList = reader.read();
            assertEquals("Sydney's flashcard list", fCardList.getName());
            List<Flashcard> thingies = fCardList.getFCards(false);
            assertEquals(2, thingies.size());
            checkFlashcard("jiwon", "babo", fCardList.getFlashCard(0).getLearningStatus(), fCardList.getFlashCard(0));
            checkFlashcard("suyeon", "cute", fCardList.getFlashCard(1).getLearningStatus(), fCardList.getFlashCard(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
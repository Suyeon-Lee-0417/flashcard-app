package persistence;

import model.Flashcard;
import model.FCardCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            FCardCollection fc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyFCardCollection() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyFCardCollection.json");
        try {
            FCardCollection fc = reader.read();
            assertEquals("Sydney's flashcard list", fc.getName());
            assertEquals(0, fc.getNumFCards());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralFCardCollection() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralFCardCollection.json");
        try {
            FCardCollection fc = reader.read();
            assertEquals("Sydney's flashcard list", fc.getName());
            List<Flashcard> fCardList = fc.getFCards(false);
            assertEquals(2, fCardList.size());
            checkFlashcard("jiwon", "babo", fCardList.get(0).getLearningStatus(), fCardList.get(0));
            checkFlashcard("suyeon", "cute", fCardList.get(1).getLearningStatus(), fCardList.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

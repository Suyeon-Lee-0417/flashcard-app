package persistence;

import model.FCardCollection;
import model.Flashcard;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads FCardCollection from JSON data stored in file
// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReader {
    private String source;
    
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads FCardCollection from file and returns it;
    // throws IOException if an error occurs reading data from file
    public FCardCollection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseFCardCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses FCardCollection from JSON object and returns it
    private FCardCollection parseFCardCollection(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        FCardCollection fc = new FCardCollection(name);
        addFlashcards(fc, jsonObject);
        return fc;
    }

    // MODIFIES: fc
    // EFFECTS: parses Flashcards from JSON object and adds them to FCardCollection
    private void addFlashcards(FCardCollection fc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("flashcards");
        for (Object json : jsonArray) {
            JSONObject nextCard = (JSONObject) json;
            addFlashcard(fc, nextCard);
        }
    }

    // MODIFIES: fc
    // EFFECTS: parses a Flashcard from JSON object and adds it to FCardCollection
    private void addFlashcard(FCardCollection fc, JSONObject jsonObject) {
        String term = jsonObject.getString("term");
        String description = jsonObject.getString("description");
        Boolean isLearned = jsonObject.getBoolean("isLearned?");

        Flashcard flashcard = new Flashcard(term, description, isLearned);
        fc.addNewCard(flashcard);
    }
}

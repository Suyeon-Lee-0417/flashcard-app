package persistence;

import org.json.JSONObject;

// Interface for behavior of making a JSON object representation of implementing class
// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

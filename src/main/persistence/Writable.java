package persistence;

import org.json.JSONObject;

// Interface allowing company state to be stored as JSON object
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

package persistance;

import org.json.JSONObject;

//this interface was created/inspired based on the JsonSerializationDemo
public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

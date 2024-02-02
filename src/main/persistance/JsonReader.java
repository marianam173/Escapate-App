package persistance;

import model.Trip;

import model.TripList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//this class was created/inspired based on the JsonSerializationDemo
// Represents a reader that reads tripList from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs a JsonReader with the given source file path
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads the TripList from the source file and returns it
    // throws IOException if there is an error reading the data from the file
    public TripList readTripList() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTripList(jsonObject);
    }

    // EFFECTS: reads the content of the source file and returns it as a string
    // throws IOException if there is an error reading the data from the file
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            for (String line : Files.readAllLines(Paths.get(source))) {
                contentBuilder.append(line);
            }
        } catch (IOException e) {
            throw new IOException("Error reading file.");
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses tripList from JSON object and returns it
    private TripList parseTripList(JSONObject jsonObject) {
        TripList tripList = new TripList();
        JSONArray tripsArray = jsonObject.getJSONArray("trips");
        for (Object obj : tripsArray) {
            JSONObject jsonTrip = (JSONObject) obj;
            Trip trip = parseTrip(jsonTrip);
            tripList.addTrip(trip);
        }
        return tripList;
    }

    // MODIFIES: trip
    // EFFECTS: parses Trip from the jsonObject and returns it
    private Trip parseTrip(JSONObject jsonObject) {
        JSONArray packingListArray = jsonObject.getJSONArray("packingList");
        List<String> packingList = new ArrayList<>();
        addJPackingList(packingListArray, packingList);
        JSONArray attractionsArray = jsonObject.getJSONArray("attractions");
        List<String> attractions = new ArrayList<>();
        addJAttractions(attractionsArray, attractions);
        JSONArray restaurantsArray = jsonObject.getJSONArray("restaurants");
        List<String> restaurants = new ArrayList<>();
        addJRestaurants(restaurantsArray,restaurants);
        Trip trip = new Trip(getJBoolean(jsonObject), getJName(jsonObject), getJDestination(jsonObject),
                getJStartDay(jsonObject), getJStartMonth(jsonObject), getJStartYear(jsonObject), getJEndDay(jsonObject),
                getJEndMonth(jsonObject), getJEndYear(jsonObject), getJInitialBudget(jsonObject),
                getJMethodOfArrival(jsonObject), getJAccommodation(jsonObject));
        addItems(packingList, trip);
        addRestaurants(restaurants, trip);
        addAttractions(attractions, trip);
        trip.setAccommodationBookingCode(getJBookingNumberAccommodation(jsonObject));
        trip.setTransportationBookingCode(getJTransportationBookingCode(jsonObject));
        return trip;
    }

    // MODIFIES: packingList
    // EFFECTS: parses JSONArray and adds items to the packingList
    private void addJPackingList(JSONArray packingListArray, List<String> packingList) {
        for (Object item : packingListArray) {
            packingList.add((String) item);
        }
    }

    // MODIFIES: attractions
    // EFFECTS: parses JSONArray and adds items to the attractions list
    private void addJAttractions(JSONArray attractionsArray, List<String> attractions) {
        for (Object attraction : attractionsArray) {
            attractions.add((String) attraction);
        }
    }

    // MODIFIES: restaurants
    // EFFECTS: parses JSONArray and adds items to the restaurant list
    public void addJRestaurants(JSONArray restaurantsArray, List<String> restaurants) {
        for (Object restaurant : restaurantsArray) {
            restaurants.add((String) restaurant);
        }
    }

    // MODIFIES: trip
    // EFFECTS: adds items from packingList to the trip
    private void addItems(List<String> packinglist, Trip trip) {
        for (String item : packinglist) {
            trip.addItem(item);
        }
    }

    // MODIFIES: trip
    // EFFECTS: adds items from restaurantList to the trip
    public void addRestaurants(List<String> restaurants, Trip trip) {
        for (String restaurant : restaurants) {
            trip.addRestaurant(restaurant);
        }
    }

    // MODIFIES: trip
    // EFFECTS: adds items from attractionList to the trip
    private void addAttractions(List<String> attractions, Trip trip) {
        for (String attraction : attractions) {
            trip.addAttraction(attraction);
        }
    }

    // EFFECTS: returns the boolean value of "completed" field in the jsonObject
    private boolean getJBoolean(JSONObject jsonObject) {
        return jsonObject.getBoolean("completed");
    }

    // EFFECTS: returns the string value of "name" field in the jsonObject
    private String getJName(JSONObject jsonObject) {
        return jsonObject.getString("name");
    }

    // EFFECTS: returns the string value of "destination" field in the jsonObject
    private String getJDestination(JSONObject jsonObject) {
        return jsonObject.getString("destination");
    }

    // EFFECTS: returns the int value of "startDay" field in the jsonObject
    private int getJStartDay(JSONObject jsonObject) {
        return jsonObject.getInt("startDay");
    }

    // EFFECTS: returns the int value of "startMonth" field in the jsonObject
    private int getJStartMonth(JSONObject jsonObject) {
        return jsonObject.getInt("startMonth");
    }

    // EFFECTS: returns the int value of "startYear" field in the jsonObject
    private int getJStartYear(JSONObject jsonObject) {
        return jsonObject.getInt("startYear");
    }

    // EFFECTS: returns the int value of "endDay" field in the jsonObject
    private int getJEndDay(JSONObject jsonObject) {
        return jsonObject.getInt("endDay");
    }

    // EFFECTS: returns the int value of "endMonth" field in the jsonObject
    private int getJEndMonth(JSONObject jsonObject) {
        return jsonObject.getInt("endMonth");
    }

    // EFFECTS: returns the int value of "endYear" field in the jsonObject
    private int getJEndYear(JSONObject jsonObject) {
        return jsonObject.getInt("endYear");
    }

    // EFFECTS: returns the double value of "initialBudget" field in the jsonObject
    private double getJInitialBudget(JSONObject jsonObject) {
        return jsonObject.getDouble("initialBudget");
    }

    // EFFECTS: returns the string value of "methodOfArrival" field in the jsonObject
    private String getJMethodOfArrival(JSONObject jsonObject) {
        return jsonObject.getString("methodOfArrival");
    }

    // EFFECTS: returns the string value of "accommodation" field in the jsonObject
    private String getJAccommodation(JSONObject jsonObject) {
        return jsonObject.getString("accommodation");
    }

    // EFFECTS: returns the string value of "bookingNumberAccommodation" field in the jsonObject
    private String getJBookingNumberAccommodation(JSONObject jsonObject) {
        return jsonObject.getString("bookingNumberAccommodation");
    }

    // EFFECTS: returns the string value of "transportationBookingCode" field in the jsonObject
    private String getJTransportationBookingCode(JSONObject jsonObject) {
        return jsonObject.getString("transportationBookingCode");
    }
}

package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a list of trips and other methods
public class TripList implements Writable {

    private List<Trip> trips;

    // MODIFIES: this
    // EFFECTS: Initializes a new TripList with an empty list of trips
    public TripList() {
        this.trips = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Adds a trip to the list of trips
    public void addTrip(Trip trip) {
        trips.add(trip);
        EventLog.getInstance().logEvent(new Event("Trip with name \"" + trip.getName() + "\" added"));
    }

    // REQUIRES: tripName is not null
    // MODIFIES: this
    // EFFECTS: Deletes the trip with the given name from the list of trips and returns true, or returns false
    // if it doesn't exist
    public boolean deleteTrip(String tripName) {
        for (Trip trip : trips) {
            if (trip.getName().equalsIgnoreCase(tripName)) {
                trips.remove(trip);
                EventLog.getInstance().logEvent(new Event("Trip with name \"" + tripName + "\" was deleted"));
                return true;
            }
        }
        return false;
    }

    // REQUIRES: tripName is not null
    // EFFECTS: Returns the trip with the given name, or null if it doesn't exist
    public Trip getTrip(String tripName) {
        for (Trip trip : trips) {
            if (trip.getName().equalsIgnoreCase(tripName)) {
                return trip;
            }
        }
        return null;
    }

    // EFFECTS: returns an unmodifiable list of trips in this tripList
    public List<Trip> getAllTrips() {
        return Collections.unmodifiableList(trips);
    }

    public int getNumTrips() {
        return trips.size();
    }

    //REQUIRES: valid user input for either true or false
    // EFFECTS: Returns a list of trips with the specified completion status
    public List<Trip> getTripsByStatus(boolean completed) {
        List<Trip> tripsByStatus = new ArrayList<>();
        for (Trip trip : trips) {
            if (trip.getStatus() == completed) {
                tripsByStatus.add(trip);
            }
        }
        return tripsByStatus;
    }

    // EFFECTS: Returns a list of names of all trips in the TripList
    public List<String> getTripNames() {
        List<String> tripNames = new ArrayList<>();
        for (Trip trip : trips) {
            tripNames.add(trip.getName());
        }
        return tripNames;
    }

    // EFFECTS: create a JSON representation of a TripList object. It creates a JSONArray with trip JSONObjects inside
    @Override
    public JSONObject toJson() {
        JSONArray jsonArray = new JSONArray();
        for (Trip trip : trips) {
            jsonArray.put(trip.toJson());
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("trips", jsonArray);
        return jsonObject;
    }
}


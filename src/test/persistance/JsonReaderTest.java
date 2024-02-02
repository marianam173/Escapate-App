package persistance;

import model.Trip;
import model.TripList;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//this test was created/inspired based on the JsonSerializationDemo
class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            TripList tripList = reader.readTripList();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTripList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTripList.json");
        try {
            TripList tripList = reader.readTripList();
            assertEquals(0, tripList.getNumTrips());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTripList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTripList.json");
        try {
            TripList tripList = reader.readTripList();
            assertEquals(3, tripList.getNumTrips());
            Trip trip1 = tripList.getTrip("Trip to Paris");
            checkTrip1(trip1);
            Trip trip2 = tripList.getTrip("Road Trip USA");
            checkTrip2(trip2);
            Trip trip3 = tripList.getTrip("Trip New York");
            checkTrip3(trip3);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    private void checkTrip1(Trip trip) {
        assertEquals(true, trip.getStatus());
        assertEquals("Trip to Paris", trip.getName());
        assertEquals("Paris", trip.getDestination());
        assertEquals(1, trip.getStartDay());
        assertEquals(6, trip.getStartMonth());
        assertEquals(2022, trip.getStartYear());
        assertEquals(10, trip.getEndDay());
        assertEquals(6, trip.getEndMonth());
        assertEquals(2022, trip.getEndYear());
        assertEquals(5000, trip.getInitialBudget());
        assertEquals("Flight", trip.getMethodOfArrival());
        assertEquals("Hotel", trip.getAccommodation());
        assertEquals("Eiffel Tower", trip.getAttractions().get(0));
    }

    private void checkTrip2(Trip trip) {
        assertEquals(true, trip.getStatus());
        assertEquals("Road Trip USA", trip.getName());
        assertEquals("USA", trip.getDestination());
        assertEquals(10, trip.getStartDay());
        assertEquals(8, trip.getStartMonth());
        assertEquals(2022, trip.getStartYear());
        assertEquals(30, trip.getEndDay());
        assertEquals(9, trip.getEndMonth());
        assertEquals(2022, trip.getEndYear());
        assertEquals(8000, trip.getInitialBudget());
        assertEquals("Car", trip.getMethodOfArrival());
        assertEquals("Motel", trip.getAccommodation());
        assertEquals("Grand Canyon", trip.getPackingList().get(0));
    }

    private void checkTrip3(Trip trip) {
        assertEquals(true, trip.getStatus());
        assertEquals("Trip New York", trip.getName());
        assertEquals("USA", trip.getDestination());
        assertEquals(9, trip.getStartDay());
        assertEquals(8, trip.getStartMonth());
        assertEquals(2022, trip.getStartYear());
        assertEquals(15, trip.getEndDay());
        assertEquals(8, trip.getEndMonth());
        assertEquals(2022, trip.getEndYear());
        assertEquals(18000, trip.getInitialBudget());
        assertEquals("Plain", trip.getMethodOfArrival());
        assertEquals("Hotel", trip.getAccommodation());
        assertEquals("11 Maddison", trip.getRestaurants().get(0));
    }

}

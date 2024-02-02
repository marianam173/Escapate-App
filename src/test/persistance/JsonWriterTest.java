package persistance;

import model.TripList;
import model.Trip;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

//this test was created/inspired based on the JsonSerializationDemo
class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            TripList tripList = new TripList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            writer.writeTripList(tripList);
            writer.close();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTripList() {
        try {
            TripList tripList = new TripList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTripList.json");
            writer.open();
            writer.writeTripList(tripList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTripList.json");
            tripList = reader.readTripList();
            assertEquals(0, tripList.getNumTrips());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralTripList() {
        try {
            TripList tripList = new TripList();
            Trip trip1 = new Trip(true, "Trip to Paris", "Paris", 1, 6,
                    2022, 10, 6, 2022, 5000, "Flight",
                    "Hotel");
            trip1.addAttraction("Eiffel Tower");
            tripList.addTrip(trip1);

            Trip trip2 = new Trip(true, "Road Trip USA", "USA", 10, 8,
                    2022, 30, 9, 2022, 8000, "Car",
                    "Motel");
            trip2.addItem("Grand Canyon");
            tripList.addTrip(trip2);

            Trip trip3 = new Trip(true, "Trip New York", "USA", 9, 8,
                    2022, 15, 8, 2022, 18000, "Plain",
                    "Hotel");
            trip3.addRestaurant("11 Maddison");
            tripList.addTrip(trip3);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTripList.json");
            writer.open();
            writer.writeTripList(tripList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTripList.json");
            tripList = reader.readTripList();
            assertEquals(3, tripList.getNumTrips());
            Trip testTrip1 = tripList.getTrip("Trip to Paris");
            checkTrip1(testTrip1);
            Trip testTrip2 = tripList.getTrip("Road Trip USA");
            checkTrip2(testTrip2);
            Trip testTrip3 = tripList.getTrip("Trip New York");
            checkTrip3(testTrip3);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
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


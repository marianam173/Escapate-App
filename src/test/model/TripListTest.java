package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TripListTest {

    private TripList tripList;
    private Trip trip1;
    private Trip trip2;
    private Trip trip3;

    @BeforeEach
    void runBefore() {
        trip1 = new Trip(false, "Trip to Paris", "Paris", 1, 6,
                2023, 10, 6, 2023, 2000, "Flight", "Hotel");
        trip2 = new Trip(false, "Weekend Getaway", "Cabin in the woods", 15,
                7, 2023, 18, 7, 2023, 500, "Car", "Cabin");
        trip3 = new Trip(true, "Trip to Italy", "Rome", 14,
                7, 2026, 6, 9, 2026, 5000, "Flight", "Hotel");
        tripList = new TripList();
    }

    @Test
    void testAddTrip() {
        tripList.addTrip(trip1);
        tripList.addTrip(trip2);
        assertEquals(2, tripList.getNumTrips());
    }

    @Test
    public void testDeleteTrip() {
        tripList.addTrip(trip1);
        tripList.addTrip(trip2);

        assertTrue(tripList.deleteTrip("Trip to Paris"));
        assertEquals(1, tripList.getNumTrips());
    }

    @Test
    public void testDeleteInvalidTrip() {
        tripList.addTrip(trip1);
        tripList.addTrip(trip2);

        assertFalse(tripList.deleteTrip("Paris"));
        assertEquals(2, tripList.getNumTrips());
    }

    @Test
    public void testGetTrip() {
        tripList.addTrip(trip1);
        tripList.addTrip(trip2);
        Trip wantedTrip = tripList.getTrip("Weekend Getaway");
        assertEquals("Weekend Getaway", wantedTrip.getName());
    }

    @Test
    public void testGetInvalidTrip() {
        tripList.addTrip(trip1);
        tripList.addTrip(trip2);
        Trip wanted = tripList.getTrip("Getaway");
        assertNull(wanted);
    }

    @Test
    public void testGetAllTrips() {
        tripList.addTrip(trip1);
        tripList.addTrip(trip2);
        List<Trip> allTrips = tripList.getAllTrips();
        assertTrue(allTrips.contains(trip1));
        assertTrue(allTrips.contains(trip2));
    }

    @Test
    public void testGetByStatus() {
        tripList.addTrip(trip1);
        tripList.addTrip(trip2);
        tripList.addTrip(trip3);
        List<Trip> completedTrips = tripList.getTripsByStatus(true);
        List<Trip> uncompletedTrips = tripList.getTripsByStatus(false);
        assertTrue(completedTrips.contains(trip3));
        assertTrue(uncompletedTrips.contains(trip1));
        assertTrue(uncompletedTrips.contains(trip2));
    }

    @Test
    public void testGetAllNames() {
        tripList.addTrip(trip1);
        tripList.addTrip(trip2);
        tripList.addTrip(trip3);
        List<String> names = tripList.getTripNames();
        assertTrue(names.contains("Trip to Paris"));
        assertTrue(names.contains("Weekend Getaway"));
        assertTrue(names.contains("Trip to Italy"));
    }


}

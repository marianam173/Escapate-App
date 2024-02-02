package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TripTest {
    private Trip testTrip;

    @BeforeEach
    void runBefore() {
        testTrip = new Trip(false, "Honeymoon", "Hawaii", 2, 5,
                2024, 12, 5, 2024, 30000, "By plane",
                "Dream hotel");
    }

    @Test
    void testConstructor() {
        assertFalse(testTrip.getStatus());
        assertEquals("Honeymoon", testTrip.getName());
        assertEquals("Hawaii", testTrip.getDestination());
        assertEquals(2,testTrip.getStartDay());
        assertEquals(5,testTrip.getStartMonth());
        assertEquals(2024,testTrip.getStartYear());
        assertEquals(12,testTrip.getEndDay());
        assertEquals(5,testTrip.getEndMonth());
        assertEquals(2024,testTrip.getEndYear());
        assertEquals(30000,testTrip.getInitialBudget());
        assertEquals("By plane", testTrip.getMethodOfArrival());
        assertEquals("Dream hotel", testTrip.getAccommodation());
        assertEquals(0,testTrip.getNumRestaurants());
        assertEquals(0,testTrip.getNumAttractions());
        assertEquals(0,testTrip.getNumItems());
        assertEquals(0,testTrip.getTotalAmountSpent());
        assertEquals(30000,testTrip.getBalance());
    }

    void testConstructorNegativeBudget() {
        testTrip = new Trip(false, "Honeymoon", "Hawaii", 2, 5,
                2024, 12, 5, 2024, -90000, "By plane",
                "Dream hotel");
        assertEquals(30000,testTrip.getInitialBudget());
        assertEquals(30000,testTrip.getBalance());
    }

    //RESTAURANT TESTS

    @Test
    void testAddingRestaurant() {
        assertTrue(testTrip.addRestaurant("Beef and roses"));
        assertTrue(testTrip.restaurantExists("Beef and Roses"));
        assertEquals(1,testTrip.getNumRestaurants());
    }

    @Test
    void testAddMultipleRestaurants() {
        assertTrue(testTrip.addRestaurant("Dulce vida"));
        assertTrue(testTrip.addRestaurant("Italian Bistro"));
        assertTrue(testTrip.addRestaurant("Chef's Table"));
        assertTrue(testTrip.restaurantExists("Dulce vida"));
        assertTrue(testTrip.restaurantExists("Italian Bistro"));
        assertTrue(testTrip.restaurantExists("chef's TABLE"));
        assertFalse(testTrip.restaurantExists("random"));
        assertEquals(3, testTrip.getNumRestaurants());
    }

    @Test
    void testEditRestaurantName() {
        assertTrue(testTrip.addRestaurant("Dulce vida"));
        assertTrue(testTrip.addRestaurant("Italian Bistro"));
        assertTrue(testTrip.editNameRestaurant("italian BISTRO","Fresco Italian Restaurant"));
        assertEquals(2,testTrip.getNumRestaurants());
        assertFalse(testTrip.restaurantExists("italian bistro"));
        assertTrue(testTrip.restaurantExists("Fresco Italian Restaurant"));
    }

    @Test
    void testEditInvalidRestaurantName() {
        assertTrue(testTrip.addRestaurant("Dulce vida"));
        assertTrue(testTrip.addRestaurant("Italian Bistro"));
        assertFalse(testTrip.editNameRestaurant("italian","Fresco Italian Restaurant"));
        assertTrue(testTrip.restaurantExists("Italian Bistro"));
        assertTrue(testTrip.restaurantExists("Dulce vida"));
        assertEquals(2,testTrip.getNumRestaurants());
    }

    @Test
    void testEditMultipleRestaurants() {
        assertTrue(testTrip.addRestaurant("Dulce"));
        assertTrue(testTrip.addRestaurant("Italian Bistro"));
        assertTrue(testTrip.editNameRestaurant("Dulce","Dulce Pasta"));
        assertTrue(testTrip.editNameRestaurant("Italian BISTRO","Quiora"));
        assertEquals(2,testTrip.getNumRestaurants());
        assertFalse(testTrip.restaurantExists("italian bistro"));
        assertTrue(testTrip.restaurantExists("Quiora"));
        assertFalse(testTrip.restaurantExists("dulce"));
        assertTrue(testTrip.restaurantExists("dulce pasta"));
    }

    @Test
    void testDeleteRestaurant() {
        assertTrue(testTrip.addRestaurant("Dulce vida"));
        assertTrue(testTrip.addRestaurant("Italian Bistro"));
        assertTrue(testTrip.deleteRestaurant("ITALIAN BISTRO"));
        assertFalse(testTrip.restaurantExists("italian bistro"));
        assertEquals(1,testTrip.getNumRestaurants());
    }

    @Test
    void testDeleteInvalidRestaurant() {
        assertTrue(testTrip.addRestaurant("Dulce vida"));
        assertTrue(testTrip.addRestaurant("Italian Bistro"));
        assertFalse(testTrip.deleteRestaurant("ITALIAN BIS"));
        assertTrue(testTrip.restaurantExists("italian bistro"));
        assertEquals(2,testTrip.getNumRestaurants());
    }

    @Test
    void testAddExistingRestaurant() {
        assertTrue(testTrip.addRestaurant("Dulce vida"));
        assertTrue(testTrip.addRestaurant("Italian Bistro"));
        assertFalse(testTrip.addRestaurant("Dulce vida"));
        assertEquals(2,testTrip.getNumRestaurants());
    }

    //ATTRACTIONS TEST

    @Test
    void testAddingAttraction() {
        assertTrue(testTrip.addAttraction("Sky diving"));
        assertTrue(testTrip.attractionExists("sky DIVING"));
        assertEquals(1,testTrip.getNumAttractions());
    }

    @Test
    void testAddMultipleAttractions() {
        assertTrue(testTrip.addAttraction("Manta Ray snorkel"));
        assertTrue(testTrip.addAttraction("Turtle town"));
        assertTrue(testTrip.addAttraction("Volcano tour"));
        assertTrue(testTrip.attractionExists("Manta Ray snorkel"));
        assertTrue(testTrip.attractionExists("Turtle town"));
        assertTrue(testTrip.attractionExists("Volcano tour"));
        assertFalse(testTrip.attractionExists("random"));
        assertEquals(3, testTrip.getNumAttractions());
    }

    @Test
    void testEditAttractionName() {
        assertTrue(testTrip.addAttraction("Manta Ray snorkel"));
        assertTrue(testTrip.addAttraction("Turtle town"));
        assertTrue(testTrip.editNameAttraction("Turtle TOwn","Turtle mountain"));
        assertEquals(2,testTrip.getNumAttractions());
        assertFalse(testTrip.attractionExists("turtle town"));
        assertTrue(testTrip.attractionExists("turtle mountain"));
    }

    @Test
    void testEditInvalidAttractionName() {
        assertTrue(testTrip.addAttraction("Manta Ray snorkel"));
        assertTrue(testTrip.addAttraction("Turtle town"));
        assertFalse(testTrip.editNameAttraction("manta","ray"));
        assertTrue(testTrip.attractionExists("Manta Ray snorkel"));
        assertTrue(testTrip.attractionExists("turtle town"));
        assertEquals(2,testTrip.getNumAttractions());
    }

    @Test
    void testEditMultipleAttractions() {
        assertTrue(testTrip.addAttraction("Manta Ray snorkel"));
        assertTrue(testTrip.addAttraction("Turtle town"));
        assertTrue(testTrip.editNameAttraction("Manta ray snorkel","shark snorkel"));
        assertTrue(testTrip.editNameAttraction("turtle town","shark town"));
        assertEquals(2,testTrip.getNumAttractions());
        assertFalse(testTrip.attractionExists("Manta ray snorkel"));
        assertTrue(testTrip.attractionExists("shark snorkel"));
        assertFalse(testTrip.attractionExists("turtle town"));
        assertTrue(testTrip.attractionExists("shark town"));
    }

    @Test
    void testDeleteAttractions() {
        assertTrue(testTrip.addAttraction("Manta Ray snorkel"));
        assertTrue(testTrip.addAttraction("Turtle town"));
        assertTrue(testTrip.deleteAttraction("turtle town"));
        assertFalse(testTrip.attractionExists("turtle town"));
        assertEquals(1,testTrip.getNumAttractions());
    }

    @Test
    void testDeleteInvalidAttraction() {
        assertTrue(testTrip.addAttraction("Manta Ray snorkel"));
        assertTrue(testTrip.addAttraction("Turtle town"));
        assertFalse(testTrip.deleteAttraction("shark town"));
        assertTrue(testTrip.attractionExists("turtle town"));
        assertEquals(2,testTrip.getNumAttractions());
    }

    @Test
    void testAddExistingAttraction() {
        assertTrue(testTrip.addAttraction("Manta Ray snorkel"));
        assertTrue(testTrip.addAttraction("Turtle town"));
        assertFalse(testTrip.addAttraction("turtle town"));
        assertEquals(2,testTrip.getNumAttractions());
    }

    // PACKING LIST TEST

    @Test
    void testAddingItem() {
        assertTrue(testTrip.addItem("passport"));
        assertTrue(testTrip.itemExists("passport"));
        assertEquals(1,testTrip.getNumItems());
    }

    @Test
    void testAddMultipleItems() {
        assertTrue(testTrip.addItem("passport"));
        assertTrue(testTrip.addItem("red pants"));
        assertTrue(testTrip.addItem("extra money"));
        assertTrue(testTrip.itemExists("passport"));
        assertTrue(testTrip.itemExists("red pants"));
        assertTrue(testTrip.itemExists("extra money"));
        assertFalse(testTrip.itemExists("random"));
        assertEquals(3, testTrip.getNumItems());
    }

    @Test
    void testEditItemName() {
        assertTrue(testTrip.addItem("passport"));
        assertTrue(testTrip.addItem("red pants"));
        assertTrue(testTrip.editNameItem("red pants","blue pants"));
        assertEquals(2,testTrip.getNumItems());
        assertFalse(testTrip.itemExists("red pants"));
        assertTrue(testTrip.itemExists("blue pants"));
    }

    @Test
    void testEditInvalidItemName() {
        assertTrue(testTrip.addItem("pants"));
        assertTrue(testTrip.addItem("passport"));
        assertFalse(testTrip.editNameItem("pan","red pants"));
        assertTrue(testTrip.itemExists("pants"));
        assertTrue(testTrip.itemExists("passport"));
        assertEquals(2,testTrip.getNumItems());
    }

    @Test
    void testEditMultipleItems() {
        assertTrue(testTrip.addItem("passport"));
        assertTrue(testTrip.addItem("red pants"));
        assertTrue(testTrip.editNameItem("red pants","blue pants"));
        assertTrue(testTrip.editNameItem("blue pants","grey pants"));
        assertTrue(testTrip.editNameItem("passport","VISA"));
        assertEquals(2,testTrip.getNumItems());
        assertFalse(testTrip.itemExists("blue pants"));
        assertTrue(testTrip.itemExists("grey pants"));
        assertFalse(testTrip.itemExists("passport"));
        assertTrue(testTrip.itemExists("VISa"));
    }

    @Test
    void testDeleteItems() {
        assertTrue(testTrip.addItem("VISA"));
        assertTrue(testTrip.addItem("charger"));
        assertTrue(testTrip.deleteItem("VISA"));
        assertFalse(testTrip.itemExists("VISA"));
        assertEquals(1,testTrip.getNumItems());
    }

    @Test
    void testDeleteInvalidItems() {
        assertTrue(testTrip.addItem("VISA"));
        assertTrue(testTrip.addItem("charger"));
        assertFalse(testTrip.deleteItem("passport"));
        assertEquals(2,testTrip.getNumItems());
    }

    @Test
    void testAddExistingItems() {
        assertTrue(testTrip.addItem("VISA"));
        assertTrue(testTrip.addItem("charger"));
        assertFalse(testTrip.addItem("visa"));
        assertEquals(2,testTrip.getNumItems());
    }

    // EXPENSE TRACKER

    @Test
    void testChangeInitialBudget() {
        assertTrue(testTrip.changeInitialBudget(40000));
        assertEquals(40000,testTrip.getInitialBudget());
        assertEquals(40000,testTrip.getBalance());
    }

    @Test
    void testChangeInitialNegativeBudget() {
        assertFalse(testTrip.changeInitialBudget(-2000));
        assertEquals(0,testTrip.getInitialBudget());
        assertEquals(0,testTrip.getBalance());
    }

    @Test
    void testNewExpense() {
        assertEquals("25000.00", testTrip.newExpense(5000));
        assertEquals(5000,testTrip.getTotalAmountSpent());
        assertEquals(25000,testTrip.getBalance());
    }

    @Test
    void testNewExpenseOverBalance() {
        assertEquals("-15000.00", testTrip.newExpense(45000));
        assertEquals(45000,testTrip.getTotalAmountSpent());
        assertEquals(-15000,testTrip.getBalance());
    }

    // DATE TEST

    @Test
    void testModifyValidStartDate() {
        assertTrue(testTrip.modifyStartDate(10,5,2024));
        assertEquals("10/5/2024",testTrip.startDateString());
    }

    @Test
    void testModifyInvalidDayStartDate() {
        assertFalse(testTrip.modifyStartDate(19,5,2024));
        assertEquals("2/5/2024",testTrip.startDateString());
    }

    @Test
    void testModifyInvalidMonthStartDate() {
        assertFalse(testTrip.modifyStartDate(11,9,2024));
        assertEquals("2/5/2024",testTrip.startDateString());
    }

    @Test
    void testModifyInvalidYearStartDate() {
        assertFalse(testTrip.modifyStartDate(11,5,2025));
        assertEquals("2/5/2024",testTrip.startDateString());
    }

    @Test
    void testModifyValidEndDate() {
        assertTrue(testTrip.modifyEndDate(9,6,2025));
        assertEquals("9/6/2025",testTrip.endDateString());
    }

    @Test
    void testModifyInvalidDayEndDate() {
        assertFalse(testTrip.modifyEndDate(1,5,2024));
        assertEquals("12/5/2024",testTrip.endDateString());
    }

    @Test
    void testModifyInvalidMonthEndDate() {
        assertFalse(testTrip.modifyEndDate(12,4,2024));
        assertEquals("12/5/2024",testTrip.endDateString());
    }

    @Test
    void testModifyInvalidYearEndDate() {
        assertFalse(testTrip.modifyEndDate(12,5,2020));
        assertEquals("12/5/2024",testTrip.endDateString());
    }

    // DESTINATION TEST

    @Test
    void testChangeDestination() {
        assertTrue(testTrip.changeDestination("Paris"));
        assertEquals("Paris", testTrip.getDestination());
    }

    // NAME TEST

    @Test
    void testChangeName() {
        assertTrue(testTrip.changeName("Wedding"));
        assertEquals("Wedding",testTrip.getName());
    }

    // TRIP STATUS TEST

    @Test
    void testChangeTripStatusTrue() {
        assertTrue(testTrip.changeStatus());
        assertTrue(testTrip.getStatus());
    }

    @Test
    void testChangeTripStatusFalse() {
        assertTrue(testTrip.changeStatus());
        assertTrue(testTrip.changeStatus());
        assertFalse(testTrip.getStatus());
    }

    // METHOD OF ARRIVAL TEST

    @Test
    void testChangeArrivalMethod() {
        assertTrue(testTrip.changeArrivalMethod("Private Jet"));
        assertEquals("Private Jet",testTrip.getMethodOfArrival());
    }

    @Test
    void testSettingTransportBookingCode() {
        assertTrue(testTrip.setTransportationBookingCode("23AB54"));
        assertEquals("23AB54", testTrip.getTransportationBookingCode());
    }

    // ACCOMODATION TEST

    @Test
    void testChangeAccommodation() {
        assertTrue(testTrip.changeAccommodation("Hotel Hilton"));
        assertEquals("Hotel Hilton", testTrip.getAccommodation());
    }

    @Test
    void testSettingAccommodationBookingNumber() {
        assertTrue(testTrip.setAccommodationBookingCode("2dt4"));
        assertEquals("2dt4", testTrip.getBookingNumberAccommodation());
    }

    @Test
    public void testGetAllRestaurants() {
        testTrip.addRestaurant("Restaurant1");
        testTrip.addRestaurant("Restaurant2");
        testTrip.addRestaurant("Restaurant3");
        String expectedString = "- Restaurant1\n- Restaurant2\n- Restaurant3\n";
        assertEquals(expectedString, testTrip.getAllRestaurants());
    }

    @Test
    public void testGetAllAttractions() {
        testTrip.addAttraction("Attraction1");
        testTrip.addAttraction("Attraction2");
        testTrip.addAttraction("Attraction3");
        String expectedString = "- Attraction1\n- Attraction2\n- Attraction3\n";
        assertEquals(expectedString, testTrip.getAllAttractions());
    }

    @Test
    public void testGetAllItems() {
        testTrip.addItem("Item1");
        testTrip.addItem("Item2");
        testTrip.addItem("Item3");
        String expectedString = "- Item1\n- Item2\n- Item3\n";
        assertEquals(expectedString, testTrip.getAllItems());
    }

    @Test
    public void testGetRestaurants() {
        List<String> restaurantsExpected = new ArrayList<>();
        restaurantsExpected.add("r1");
        restaurantsExpected.add("r2");

        testTrip.addRestaurant("r1");
        testTrip.addRestaurant("r2");

        List<String> restaurantsActual = testTrip.getRestaurants();
        assertEquals(restaurantsExpected, restaurantsActual);
    }
}

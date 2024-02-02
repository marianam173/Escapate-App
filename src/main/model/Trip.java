package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a trip that contains name, dates, destination, etc.
public class Trip implements Writable {

    private boolean completed;
    private String name;
    private String destination;

    private int startDay;
    private int startMonth;
    private int startYear;

    private int endDay;
    private int endMonth;
    private int endYear;

    private double initialBudget;
    private double balance;
    private double totalAmountSpent;
    private List<String> packingList;
    private List<String> attractions;
    private List<String> restaurants;
    private String methodOfArrival;
    private String accommodation;
    private String bookingNumberAccommodation;
    private String transportationBookingCode;

    // Constructor
    // REQUIRES: None of the parameters are null, start date is before or equal to end date, initialBudget >= 0
    // MODIFIES: All instance variables
    // EFFECTS: Initializes a Trip with the provided information and initializes lists
    public Trip(boolean completed, String name, String destination, int startDay, int startMonth, int startYear,
                int endDay, int endMonth, int endYear, double initialBudget, String methodOfArrival,
                String accommodation) {

        this.completed = completed;
        this.name = name;
        this.destination = destination;
        this.restaurants = new ArrayList<>();
        this.attractions = new ArrayList<>();
        this.packingList = new ArrayList<>();
        this.startDay = startDay;
        this.startMonth = startMonth;
        this.startYear = startYear;
        this.endDay = endDay;
        this.endMonth = endMonth;
        this.endYear = endYear;
        this.initialBudget = initialBudget;
        this.methodOfArrival = methodOfArrival;
        this.accommodation = accommodation;
        this.balance = initialBudget;
        this.totalAmountSpent = 0;
        this.bookingNumberAccommodation = "";
        this.transportationBookingCode = "";

    }

    // RESTAURANTS:
    // REQUIRES: restaurantName is not null
    // EFFECTS: Returns true if the restaurant with the given name exists in the restaurants list, false otherwise
    public boolean restaurantExists(String restaurantName) {
        for (String restaurant : restaurants) {
            if (restaurant.equalsIgnoreCase(restaurantName)) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: restaurantToAdd is not null
    // MODIFIES: this
    // EFFECTS: Adds a restaurant with the given name to the restaurants list and returns true, or returns false if it
    // already exists
    public boolean addRestaurant(String restaurantToAdd) {
        if (!restaurantExists(restaurantToAdd)) {
            this.restaurants.add(restaurantToAdd);
            EventLog.getInstance().logEvent(new Event("Restaurant: " + restaurantToAdd
                    + ", added to restaurant list to the trip: " + name));
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: restaurantToDelete is not null
    // MODIFIES: this
    // EFFECTS: Deletes the restaurant with the given name from the restaurants list and returns true, or returns false
    // if it doesn't exist
    public boolean deleteRestaurant(String restaurantToDelete) {
        for (int i = 0; i < restaurants.size(); i++) {
            if (restaurants.get(i).equalsIgnoreCase(restaurantToDelete)) {
                restaurants.remove(i);
                EventLog.getInstance().logEvent(new Event("Restaurant: " + restaurantToDelete
                        + ", deleted from restaurant list to the trip:" + name));
                return true;
            }
        }
        return false;
    }

    // REQUIRES: oldRestaurant and newRestaurantName are not null
    // MODIFIES: this
    // EFFECTS: Changes the name of the restaurant with the old name to the new name and returns true, or returns false
    // if it doesn't exist
    public boolean editNameRestaurant(String oldRestaurant, String newRestaurantName) {
        for (int i = 0; i < restaurants.size(); i++) {
            if (restaurants.get(i).equalsIgnoreCase(oldRestaurant)) {
                restaurants.set(i, newRestaurantName);
                EventLog.getInstance().logEvent(new Event("Restaurant: " + oldRestaurant
                        + ", changed to: " + newRestaurantName + " to the trip " + name));
                return true; // Restaurant found and name edited
            }
        }
        return false; // Restaurant not found
    }

    // EFFECTS: Returns the number of restaurants in the restaurants list
    public int getNumRestaurants() {
        return restaurants.size();
    }

    // EFFECTS: Returns a formatted string of all restaurants in the restaurants list
    public String getAllRestaurants() {
        StringBuilder allRestaurants = new StringBuilder();
        for (String restaurant: restaurants) {
            allRestaurants.append("- ").append(restaurant).append("\n");
        }
        return allRestaurants.toString();
    }

    // ATTRACTIONS:

    // REQUIRES: attractionToAdd is not null
    // MODIFIES: this
    // EFFECTS: Adds an attraction with the given name to the attractions list and returns true, or returns false if it
    // already exists
    public boolean addAttraction(String attractionToAdd) {
        if (!attractionExists(attractionToAdd)) {
            this.attractions.add(attractionToAdd);
            EventLog.getInstance().logEvent(new Event("Attraction: " + attractionToAdd
                    + ", added to attraction list to the trip: " + name));
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: attractionName is not null
    // EFFECTS: Returns true if the attraction with the given name exists in the attractions list, false otherwise
    public boolean attractionExists(String attractionName) {
        for (String attraction : attractions) {
            if (attraction.equalsIgnoreCase(attractionName)) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: attractionToDelete is not null
    // MODIFIES: this
    // EFFECTS: Deletes the attraction with the given name from the attractions list and returns true, or returns false
    // if it doesn't exist
    public boolean deleteAttraction(String attractionToDelete) {
        for (int i = 0; i < attractions.size(); i++) {
            if (attractions.get(i).equalsIgnoreCase(attractionToDelete)) {
                attractions.remove(i);
                EventLog.getInstance().logEvent(new Event("Attraction: " + attractionToDelete
                        + ", deleted from attraction lis to the trip: " + name));
                return true;
            }
        }
        return false;
    }

    // REQUIRES: oldAttraction and newAttractionName are not null
    // MODIFIES: this
    // EFFECTS: Changes the name of the attraction with the old name to the new name and returns true, or returns false
    // if it doesn't exist
    public boolean editNameAttraction(String oldAttraction, String newAttractionName) {
        for (int i = 0; i < attractions.size(); i++) {
            if (attractions.get(i).equalsIgnoreCase(oldAttraction)) {
                attractions.set(i,newAttractionName);
                EventLog.getInstance().logEvent(new Event("Attraction: " + oldAttraction
                        + ", changed to: " + newAttractionName + " to the trip " + name));

                return true;
            }
        }
        return false;
    }

    public int getNumAttractions() {
        return attractions.size();
    }

    // EFFECTS: Returns a formatted string of all attractions in the attractions list
    public String getAllAttractions() {
        StringBuilder allAttractions = new StringBuilder();
        for (String attraction: attractions) {
            allAttractions.append("- ").append(attraction).append("\n");
        }
        return allAttractions.toString();
    }

    // PACKING LIST

    // REQUIRES: itemToAdd is not null
    // MODIFIES: this
    // EFFECTS: Adds an item with the given name to the packing list and returns true, or returns false if it
    // already exists
    public boolean addItem(String itemToAdd) {
        if (!itemExists(itemToAdd)) {
            this.packingList.add(itemToAdd);
            EventLog.getInstance().logEvent(new Event("Item: " + itemToAdd
                    + ", added to packing list to the trip: " + name));
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: item is not null
    // EFFECTS: Returns true if the item with the given name exists in the packing list, false otherwise
    public boolean itemExists(String item) {
        for (String itemNum : packingList) {
            if (itemNum.equalsIgnoreCase(item)) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: itemToDelete is not null
    // MODIFIES: this
    // EFFECTS: Deletes the item with the given name from the packing list and returns true, or returns false
    // if it doesn't exist
    public boolean deleteItem(String itemToDelete) {
        for (int i = 0; i < packingList.size(); i++) {
            if (packingList.get(i).equalsIgnoreCase(itemToDelete)) {
                packingList.remove(i);
                EventLog.getInstance().logEvent(new Event("Item: " + itemToDelete
                        + ", deleted from packing list to the trip: " + name));
                return true;
            }
        }
        return false;
    }

    // REQUIRES: oldItem and newItemName are not null
    // MODIFIES: this
    // EFFECTS: Changes the name of the item with the old name to the new name and returns true, or returns false
    // if it doesn't exist
    public boolean editNameItem(String oldItem, String newItemName) {
        for (int i = 0; i < packingList.size(); i++) {
            if (packingList.get(i).equalsIgnoreCase(oldItem)) {
                packingList.set(i,newItemName);
                EventLog.getInstance().logEvent(new Event("Item: " + oldItem
                        + ", changed to: " + newItemName + " to the trip: " + name));
                return true;
            }
        }
        return false;
    }


    public int getNumItems() {
        return packingList.size();
    }

    // EFFECTS: Returns a formatted string of all items in the packing list
    public String getAllItems() {
        StringBuilder allAttractions = new StringBuilder();
        for (String item: packingList) {
            allAttractions.append("- ").append(item).append("\n");
        }
        return allAttractions.toString();
    }

    // EXPENSE TRACKER:

    // REQUIRES: newBudget >= 0
    // MODIFIES: this
    // EFFECTS: Changes the initial budget to the new budget and resets the balance
    public boolean changeInitialBudget(double newBudget) {
        if (newBudget >= 0) {
            initialBudget = newBudget;
            balance = initialBudget;
            EventLog.getInstance().logEvent(new Event("New budget changed to: " + newBudget
                    + " to the trip: " + name));
            return true;
        } else {
            initialBudget = 0;
            balance = 0;
            return false;
        }
    }

    // REQUIRES: expense >= 0
    // MODIFIES: this
    // EFFECTS: Records a new expense and updates the balance, returns the updated balance as a formatted string
    public String newExpense(double expense) {
        this.balance -= expense;
        this.totalAmountSpent += expense;
        String balanceStr = String.format("%.2f", this.balance);
        EventLog.getInstance().logEvent(new Event("Expense of: " + expense + " was made for the trip: "
                + name));
        return balanceStr;
    }

    public double getInitialBudget() {
        return initialBudget;
    }

    public double getBalance() {
        return balance;
    }

    public double getTotalAmountSpent() {
        return totalAmountSpent;
    }

    // DATES

    // EFFECTS: Returns the start date as a formatted string
    public String startDateString() {
        return startDay + "/" + startMonth + "/" + startYear;
    }

    // EFFECTS: Returns the end date as a formatted string
    public String endDateString() {
        return endDay + "/" + endMonth + "/" + endYear;
    }

    // REQUIRES: newDay, newMonth, and newYear are valid dates (start date is before or equal to end date)
    // MODIFIES: this
    // EFFECTS: Changes the start date to the new date and returns true, or returns false if the new date is invalid
    public boolean modifyStartDate(int newDay, int newMonth, int newYear) {
        if (endDay >= newDay && endMonth >= newMonth && endYear >= newYear) {
            this.startDay = newDay;
            this.startMonth = newMonth;
            this.startYear = newYear;
            EventLog.getInstance().logEvent(new Event("Start date was changed to: " + newDay + "/"
                    + newMonth + "/" + newYear + ", to the trip " + name));
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: newDay, newMonth, and newYear are valid dates (end date is after or equal to start date)
    // MODIFIES: this
    // EFFECTS: Changes the end date to the new date and returns true, or returns false if the new date is invalid
    public boolean modifyEndDate(int newDay, int newMonth, int newYear) {
        if (startDay <= newDay && startMonth <= newMonth && startYear <= newYear) {
            this.endDay = newDay;
            this.endMonth = newMonth;
            this.endYear = newYear;
            EventLog.getInstance().logEvent(new Event("End date was changed to: " + newDay + "/"
                    + newMonth + "/" + newYear + ", to the trip: " + name));
            return true;
        } else {
            return false;
        }
    }

    public int getStartDay() {
        return startDay;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getEndDay() {
        return endDay;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public int getEndYear() {
        return endYear;
    }

    // DESTINATION

    // REQUIRES: newDestinationName is not null
    // MODIFIES: this
    // EFFECTS: Changes the destination name to the new name
    public boolean changeDestination(String newDestinationName) {
        this.destination = newDestinationName;
        EventLog.getInstance().logEvent(new Event("Destination changed to: " + newDestinationName
                + ", to the trip: " + name));
        return true;
    }

    public String getDestination() {
        return destination;
    }

    // NAME

    // REQUIRES: newTripName is not null
    // MODIFIES: this
    // EFFECTS: Changes the trip name to the new name
    public boolean changeName(String newTripName) {
        this.name = newTripName;
        EventLog.getInstance().logEvent(new Event("Name of trip changed to: " + newTripName
                + ", to the trip: " + name));
        return true;
    }

    public String getName() {
        return name;
    }

    // TRIP STATUS

    public boolean getStatus() {
        return completed;
    }

    // MODIFIES: this
    // EFFECTS: Changes the trip status (completed or not completed) and returns true after it is successfully changed
    public boolean changeStatus() {
        this.completed = !completed;
        EventLog.getInstance().logEvent(new Event("Status of trip changed for trip: " + name));
        return true;
    }

    // METHOD OF ARRIVAL

    // REQUIRES: newArrivalMethod is not null
    // MODIFIES: this
    // EFFECTS: Changes the method of arrival and returns true after it is successfully changed
    public boolean changeArrivalMethod(String newArrivalMethod) {
        this.methodOfArrival = newArrivalMethod;
        EventLog.getInstance().logEvent(new Event("Arrival method was changed to: " + newArrivalMethod
                + ", for the trip: " + name));
        return true;
    }

    public String getMethodOfArrival() {
        return methodOfArrival;
    }

    // REQUIRES: transportationBookingCode is not null
    // MODIFIES: this
    // EFFECTS: Sets the transportation booking code to the providednew code
    public boolean setTransportationBookingCode(String transportationBookingCode) {
        if (!transportationBookingCode.equals(this.transportationBookingCode)) {
            this.transportationBookingCode = transportationBookingCode;
            EventLog.getInstance().logEvent(new Event("Transportation booking code set to : "
                    + transportationBookingCode + ", for the trip: " + name));
            return true;
        }
        return false;
    }

    public String getTransportationBookingCode() {
        return transportationBookingCode;
    }

    // ACCOMMODATION

    // REQUIRES: newAccommodation is not null
    // MODIFIES: this
    // EFFECTS: Changes the accommodation to the new accommodation
    public boolean changeAccommodation(String newAccommodation) {
        this.accommodation = newAccommodation;
        EventLog.getInstance().logEvent(new Event("Accommodation was changed to : " + newAccommodation
                + ", for the trip: " + name));
        return true;
    }

    public String getAccommodation() {
        return accommodation;
    }

    // REQUIRES: bookingNumberAccommodation is not null
    // MODIFIES: this
    // EFFECTS: Sets the booking number for accommodation to the provided new number
    public boolean setAccommodationBookingCode(String bookingNumberAccommodation) {
        if (!bookingNumberAccommodation.equals(this.bookingNumberAccommodation)) {
            this.bookingNumberAccommodation = bookingNumberAccommodation;
            EventLog.getInstance().logEvent(new Event("Accommodation booking code set to : "
                    + bookingNumberAccommodation + ", for the trip: " + name));
            return true;
        }
        return false;
    }

    public String getBookingNumberAccommodation() {
        return bookingNumberAccommodation;
    }

    public List<String> getAttractions() {
        return attractions;
    }

    public List<String> getPackingList() {
        return packingList;
    }

    public List<String> getRestaurants() {
        return restaurants;
    }

    // EFFECTS: create a JSON representation of a Trip object. It creates a JSONObject and adds various fields from the
    // Trip object to the JSON.
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("completed", this.completed);
        jsonObject.put("name", this.name);
        jsonObject.put("destination", this.destination);
        jsonObject.put("startDay", this.startDay);
        jsonObject.put("startMonth", this.startMonth);
        jsonObject.put("startYear", this.startYear);
        jsonObject.put("endDay", this.endDay);
        jsonObject.put("endMonth", this.endMonth);
        jsonObject.put("endYear", this.endYear);
        jsonObject.put("initialBudget", this.initialBudget);
        jsonObject.put("balance", this.balance);
        jsonObject.put("totalAmountSpent", this.totalAmountSpent);
        jsonObject.put("packingList", new JSONArray(this.packingList));
        jsonObject.put("attractions", new JSONArray(this.attractions));
        jsonObject.put("restaurants", new JSONArray(this.restaurants));
        jsonObject.put("methodOfArrival", this.methodOfArrival);
        jsonObject.put("accommodation", this.accommodation);
        jsonObject.put("bookingNumberAccommodation", this.bookingNumberAccommodation);
        jsonObject.put("transportationBookingCode", this.transportationBookingCode);
        return jsonObject;
    }
}

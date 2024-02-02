package ui;

import model.Trip;
import model.TripList;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

// Represents a run of the app that contains a tripList
public class EscapateApp {
    private static final String JSON_STORE = "./data/tripData.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private TripList tripList;
    private final Scanner input;


    //CONSTRUCTOR
    //EFFECT: initializes the escapateApp using the tripList and initializes the scanner
    public EscapateApp() {
        input = new Scanner(System.in);
        tripList = new TripList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runEscapate();
    }

    // EFFECT: Asks for user input for option. If option is valid, calls the method for the option selected by the user
    // for the specified trip.Otherwise, displays error message
    private void runEscapate() {
        boolean keepGoing = true;
        while (keepGoing) {
            displayMainMenu();
            String mainCommand = input.nextLine().toLowerCase();
            keepGoing = processMainOptions(mainCommand);
        }
    }

    //while (editing) {
    //                displayModifyMenu();
    //                String option = input.nextLine().toLowerCase();
    //                editing = processModifyOption(trip, option);
    //            }
//System.out.println("\te -> make an expense");
//        System.out.println("\tc -> create new trip");
//        System.out.println("\tm -> modify existing trip");
//        System.out.println("\tv -> view details of existing trips");
//        System.out.println("\td -> delete existing trip");
//        System.out.println("\ts -> see names of existing trips");
//        System.out.println("\tsave -> save work room to file");
//        System.out.println("\tload -> load work room from file");
    private boolean processMainOptions(String option) {
        switch (option) {
            case "c": createTrip();
                return true;
            case "m": modifyTrip();
                return true;
            case "v": viewTrips();
                return true;
            case "d": deleteTrip();
                return true;
            case "s": seeTripNames();
                return true;
            case "save": saveTripList();
                return true;
            case "load": loadTripList();
                return true;
            case "q": return false;
            default: System.out.println("Invalid option.");
                return true;
        }
    }

    // REQUIRES: user input not null
    // MODIFIES: this, trip
    // EFFECTS: Adds a new expense to the specified trip if the trip is not null. Otherwise, error message is displayed
    private void makeExpense() {
        System.out.println("Enter the name of the trip to modify:");
        String tripName = input.nextLine();
        Trip trip = tripList.getTrip(tripName);
        if (trip != null) {
            System.out.println("Enter your new expense");
            double expense = input.nextDouble();
            trip.newExpense(expense);
        } else {
            System.out.println("Trip not found");
        }
    }

    // REQUIRES: valid user input (not null), new trip name
    // MODIFIES: this, tripList
    // EFFECTS: Creates a new trip and adds it to the trip list
    private void createTrip() {
        System.out.println("Name of your trip:");
        String name = input.nextLine();
        boolean status = getStatusUser();
        System.out.println("What is your destination");
        String destination = input.nextLine();
        List<Integer> startDate = settingInitialStartDate();
        List<Integer> endDate = settingInitialEndDate();
        System.out.println("What is your budget?");
        int initialBudget = input.nextInt();
        input.nextLine();
        System.out.println("What is your method of arrival");
        String arrivalMethod = input.nextLine();
        System.out.println("What is your accommodation");
        String accommodation = input.nextLine();
        Trip trip = new Trip(status,name,destination,startDate.get(0),startDate.get(1),startDate.get(2),endDate.get(0),
                endDate.get(1),endDate.get(2),
                initialBudget,arrivalMethod,accommodation);
        tripList.addTrip(trip);
        setOptionalDetails(trip);
        System.out.print("Trip created successfully");
    }

    // REQUIRES: valid user input (either false or true)
    // EFFECTS: Returns status of the trip as completed or not completed based on user input
    private boolean getStatusUser() {
        System.out.println("Have you completed this trip in the past? (true/false)");
        String status = input.nextLine();
        return status.equalsIgnoreCase("true");
    }

    // REQUIRES: endDay >= startday, endMonth >= startMonth, endYear >= startYear. 1 =< endMonth =< 12,
    // 1 =< endDay =< valid day limit for each month. Valid input int
    // MODIFIES: this
    // EFFECTS: Sets the initial end date for the trip based on user input
    private List<Integer> settingInitialEndDate() {
        List<Integer> endDate = new ArrayList<>();
        System.out.println("What day of the month does it end?");
        endDate.add(input.nextInt());
        System.out.println("What month does it end?");
        endDate.add(input.nextInt());
        System.out.println("What year does it end?");
        endDate.add(input.nextInt());
        return endDate;
    }

    // REQUIRES: 1 =< endMonth =< 12, 1 =< endDay =< valid day limit for each month. Valid input int
    // MODIFIES: this
    // EFFECTS: Sets the initial start date for the trip based on user input
    private List<Integer> settingInitialStartDate() {
        List<Integer> endDate = new ArrayList<>();
        System.out.println("What day of the month does it start?");
        endDate.add(input.nextInt());
        System.out.println("What month does it start? (number)");
        endDate.add(input.nextInt());
        System.out.println("What year does it start?");
        endDate.add(input.nextInt());
        return endDate;
    }

    // REQUIRES: valid user input, not null
    // MODIFIES: this, trip
    // EFFECTS: Asks user for name of trip, if it exists displays the displayModifyMenu, asks the user for an option
    // and calls the processModifyOptionMethod. Otherwise, displays error message
    private void modifyTrip() {
        System.out.println("Enter the name of the trip to modify:");
        String tripName = input.nextLine();
        Trip trip = tripList.getTrip(tripName);
        if (trip != null) {
            boolean editing = true;
            while (editing) {
                displayModifyMenu();
                String option = input.nextLine().toLowerCase();
                editing = processModifyOption(trip, option);
            }
        } else {
            System.out.println("Trip not found.");
        }
    }

    // REQUIRES: valid user input, trip exists
    // MODIFIES: this, trip
    // EFFECTS: If option is valid, calls the method for the option selected by the user for the specified trip.
    // Otherwise, displays error message
    private boolean processModifyOption(Trip trip, String option) {
        switch (option) {
            case "dn": return modifyDestinationOrName(trip);
            case "d": modifyDate(trip);
                return true;
            case "m": modifyMethodOfArrival(trip);
                return true;
            case "a": modifyAccommodation(trip);
                return true;
            case "b": modifyInitialBudget(trip);
                return true;
            case "c": modifyTripStatus(trip);
                return true;
            case "l": modifyLists(trip);
                return true;
            case "x": return false;
            default: System.out.println("Invalid option.");
                return true;
        }
    }

    // REQUIRES: valid user input, trip exists
    // MODIFIES: this, trip
    // EFFECTS: Asks user if they want to change name or destination. If option valid, calls method based on option.
    // Otherwise, displays error message
    private boolean modifyDestinationOrName(Trip trip) {
        System.out.println("Select what you want to modify for Destination or Name: (d: destination, n: name)");
        System.out.println("\td -> Destination");
        System.out.println("\tn -> Name");
        String option = input.nextLine().toLowerCase();
        switch (option) {
            case "d":
                modifyDestination(trip);
                return true;
            case "n":
                modifyName(trip);
                return true;
            default:
                System.out.println("Invalid option.");
                return true;
        }
    }

    // REQUIRES: valid user input, trip exists
    // MODIFIES: this, trip
    // EFFECTS: Asks user for which date they waht to edit. If option valid, calls method based on option. Otherwise,
    // displays error message
    private void modifyDate(Trip trip) {
        System.out.println("Select which date to modify (s: start date, e: end date):");
        System.out.println("\ts -> Start Date");
        System.out.println("\te -> End Date");
        String option = input.nextLine().toLowerCase();
        if ("s".equals(option)) {
            modifyStartDate(trip);
        } else if ("e".equals(option)) {
            modifyEndDate(trip);
        } else {
            System.out.println("Invalid option.");
        }
    }

    // REQUIRES: valid user input, trip exists
    // MODIFIES: this, trip
    // EFFECTS: Asks user input for the option of list they want to edit. If option valid, calls method based on option.
    // Otherwise, displays error message
    private void modifyLists(Trip trip) {
        System.out.println("Select which list to modify");
        System.out.println("\tr -> Restaurant list");
        System.out.println("\tp -> Packing list");
        System.out.println("\ta -> Attractions list");
        String option = input.nextLine().toLowerCase();
        switch (option) {
            case "r":
                modifyRestaurants(trip);
                break;
            case "p":
                modifyPackingList(trip);
                break;
            case "at":
                modifyAttractions(trip);
                break;
            default:
                System.out.println("Invalid option.");
                break;
        }
    }

    // REQUIRES: valid user input, trip exists
    // MODIFIES: this, trip
    // EFFECTS: Asks the user for new name of trip, calls the changeName method, if name changed successful message
    // displayed. Otherwise, displays unsuccessful change
    private void modifyName(Trip trip) {
        System.out.println("Enter the new name for the trip:");
        String newName = input.nextLine();
        if (trip.changeName(newName)) {
            System.out.println("Trip name updated successfully.");
        } else {
            System.out.println("Trip name updated unsuccessfully");
        }
    }

    // REQUIRES: valid user input, trip exists
    // MODIFIES: this, trip
    // EFFECTS: Asks the user for new destination of trip, calls the changeDestination method, if destination changed
    // successful message displayed. Otherwise, displays unsuccessful change
    private void modifyDestination(Trip trip) {
        System.out.println("Enter the new destination for the trip:");
        String newDestination = input.nextLine();
        if (trip.changeDestination(newDestination)) {
            System.out.println("Destination updated successfully.");
        } else {
            System.out.println("Destination updated unsuccessfully");
        }
    }

    // REQUIRES: valid user input int, trip exists, 1 =< startMonth =< 12, 1 =< startDay =< valid day limit for
    // each month. endDay >= newStartday, endMonth >= newStartMonth, endYear >= newStartYear.
    // MODIFIES: this, trip
    // EFFECTS: Asks the user for new startDay, startMonth and startYear. Calls the modifyStartDate method, if date
    // changed successful message displayed. Otherwise, displays unsuccessful change
    private void modifyStartDate(Trip trip) {
        System.out.println("Enter the new start date for the trip:");
        System.out.println("Enter day of the month: ");
        int newDay = input.nextInt();
        System.out.println("Enter number of the month: (number)");
        int newMonth = input.nextInt();
        System.out.println("Enter year: ");
        int newYear = input.nextInt();
        if (trip.modifyStartDate(newDay,newMonth, newYear)) {
            System.out.println("Start date updated successfully.");
        } else {
            System.out.println("Start date updated unsuccessfully. Invalid date.");
        }
    }

    // REQUIRES: valid user input int, trip exists, 1 =< endMonth =< 12, 1 =< endDay =< valid day limit for each month.
    // endDay >= newStartday, endMonth >= newStartMonth, endYear >= newStartYear.
    // MODIFIES: this, trip
    // EFFECTS: Asks the user for new endDay, endMonth and endYear. Calls the modifyEndDate method, if date
    // changed successful message displayed. Otherwise, displays unsuccessful change
    private void modifyEndDate(Trip trip) {
        System.out.println("Enter the new end date for the trip:");
        System.out.println("Enter day of the month: ");
        int newDay = input.nextInt();
        System.out.println("Enter number of the month: (number)");
        int newMonth = input.nextInt();
        System.out.println("Enter year: ");
        int newYear = input.nextInt();
        if (trip.modifyEndDate(newDay,newMonth, newYear)) {
            System.out.println("Start date updated successfully.");
        } else {
            System.out.println("Start date updated unsuccessfully. Invalid date.");
        }
    }

    // REQUIRES: valid user input, trip exists
    // MODIFIES: this, trip
    // EFFECTS: Asks the user for new method of arrival of trip, calls the changeArrivalMethod method, if method changed
    // successful message displayed. Otherwise, displays unsuccessful change
    private void modifyMethodOfArrival(Trip trip) {
        System.out.println("Enter new method of arrival");
        String newMethod = input.nextLine();
        trip.changeArrivalMethod(newMethod);
        System.out.println("Arrival method updated successfully.");
    }

    // REQUIRES: valid user input, trip exists
    // MODIFIES: this, trip
    // EFFECTS: Asks the user for new accommodation of trip, calls the changeAccommodation method, if accommodation
    // changed successful message displayed. Otherwise, displays unsuccessful change
    private void modifyAccommodation(Trip trip) {
        System.out.println("Enter new accommodation");
        String newAccommodation = input.nextLine();
        trip.changeAccommodation(newAccommodation);
        System.out.println("Accommodation updated successfully.");
    }

    // REQUIRES: valid user input int, trip exists. newBudget > 0
    // MODIFIES: this, trip
    // EFFECTS: Asks the user for new budget of trip, calls the changeInitialBudget method, displays successful change
    // after method is run
    private void modifyInitialBudget(Trip trip) {
        System.out.println("Enter new initial budget");
        double newBudget = input.nextDouble();
        trip.changeInitialBudget(newBudget);
        System.out.println("Budget updated successfully.");
    }

    // REQUIRES: trip exists.
    // MODIFIES: this, trip
    // EFFECTS: calls the changeStatus method, displays successful change after method is run
    private void modifyTripStatus(Trip trip) {
        trip.changeStatus();
        System.out.println("Status updated successfully.");
    }

    // REQUIRES: valid user input not null, trip exists.
    // MODIFIES: this
    // EFFECT: asks the user which restaurant edit they want to do. If option valid,
    // calls method based on option. Otherwise, displays error message
    private void modifyRestaurants(Trip trip) {
        boolean editingRestaurants = true;
        while (editingRestaurants) {
            displayRestaurantModifyOptions();
            String option = input.nextLine().toLowerCase();
            switch (option) {
                case "a": addRestaurant(trip);
                    break;
                case "d": deleteRestaurant(trip);
                    break;
                case "e": editNameRestaurant(trip);
                    break;
                case "q": editingRestaurants = false;
                    break;
                default:
                    System.out.println("Invalid option.");
                    editingRestaurants = false;
                    break;
            }
        }
    }

    // REQUIRES: valid user input not null, trip exists.
    // MODIFIES: this
    // EFFECT: asks the user which attraction edit they want to do. If option valid,
    // calls method based on option. Otherwise, displays error message
    private void modifyAttractions(Trip trip) {
        boolean editingAttractions = true;
        while (editingAttractions) {
            displayAttractionModifyOptions();
            String option = input.nextLine().toLowerCase();
            switch (option) {
                case "a": addAttractions(trip);
                    break;
                case "d": deleteAttraction(trip);
                    break;
                case "e": editNameAttraction(trip);
                    break;
                case "q": editingAttractions = false;
                    break;
                default:
                    System.out.println("Invalid option.");
                    editingAttractions = false;
                    break;
            }
        }
    }

    // REQUIRES: valid user input not null, trip exists.
    // MODIFIES: this
    // EFFECT: asks the user which packingList edit they want to do. If option valid,
    // calls method based on option. Otherwise, displays error message
    private void modifyPackingList(Trip trip) {
        boolean editingPackingList = true;
        while (editingPackingList) {
            displayPackingListOptions();
            String option = input.nextLine().toLowerCase();
            switch (option) {
                case "a": addItemPackingList(trip);
                    break;
                case "d": deleteItemPackingList(trip);
                    break;
                case "e": editNameItem(trip);
                    break;
                case "q":
                    editingPackingList = false;
                    break;
                default:
                    System.out.println("Invalid option.");
                    editingPackingList = false;
                    break;
            }
        }
    }

    // EFFECT: displays the packingList modify menu
    private void displayPackingListOptions() {
        System.out.println("\nWhat would you like to do with the packing list:");
        System.out.println("\ta -> Add item to packing list");
        System.out.println("\td -> Delete item from packing list");
        System.out.println("\te -> Edit item in the packing list");
        System.out.println("\tq -> Go back");
    }

    // EFFECT: displays the restaurantList modify menu
    private void displayRestaurantModifyOptions() {
        System.out.println("\nWhat would you like to do with restaurants:");
        System.out.println("\ta -> Add restaurant");
        System.out.println("\td -> Delete restaurant");
        System.out.println("\te -> Edit restaurant name");
        System.out.println("\tq -> Go back");
    }

    // EFFECT: displays the attractionList modify menu
    private void displayAttractionModifyOptions() {
        System.out.println("\nWhat would you like to do with attractions:");
        System.out.println("\ta -> Add attraction");
        System.out.println("\td -> Delete attraction");
        System.out.println("\te -> Edit attraction name");
        System.out.println("\tq -> Go back");
    }

    // EFFECT: displays the main modify menu
    private void displayModifyMenu() {
        System.out.println("\nSelect what you want to modify:");
        System.out.println("\tdn -> Destination or name");
        System.out.println("\td -> Date (Start or End)");
        System.out.println("\tm -> Method of Arrival");
        System.out.println("\ta -> Accommodation");
        System.out.println("\tb -> Initial Budget");
        System.out.println("\tc -> Trip Status (Completed/Not Completed)");
        System.out.println("\tl -> Lists (Restaurants, Packing List, or Activities/Attractions)");
        System.out.println("\tx -> Go back");
    }

    // EFFECT: displays the main menu
    private void displayMainMenu() {
        System.out.println("\nWhat do you want to do?");
        System.out.println("\tsave -> save work room to file");
        System.out.println("\tload -> load work room from file");
        System.out.println("\te -> make an expense");
        System.out.println("\tc -> create new trip");
        System.out.println("\tm -> modify existing trip");
        System.out.println("\tv -> view details of existing trips");
        System.out.println("\td -> delete existing trip");
        System.out.println("\ts -> see names of existing trips");
        System.out.println("\tq -> Quit app");
    }

    // EFFECT: displays the optional characteristics menu for trip
    private void optionalMenu() {
        System.out.println("\nWhat would you like to add:");
        System.out.println("\tr -> add restaurant");
        System.out.println("\ta -> add activity");
        System.out.println("\tp -> add item to packing list");
        System.out.println("\tbn -> add booking number for accommodation");
        System.out.println("\tbc -> add booking code for transportation");
        System.out.println("\tq -> Go back");
    }

    // REQUIRES: valid user input not null, trip exists.
    // MODIFIES: this, trip
    // EFFECT: asks the user name of restaurant they want to add.
    // Calls method addRestaurant. if added displays successful message. Otherwise, displays it already exists
    private void addRestaurant(Trip trip) {
        System.out.println("Enter the name of the restaurant to add:");
        String restaurantToAdd = input.nextLine();
        boolean added = trip.addRestaurant(restaurantToAdd);
        if (added) {
            System.out.println("Restaurant added successfully.");
        } else {
            System.out.println("Restaurant already exists");
        }
    }

    // REQUIRES: valid user input not null, trip exists.
    // MODIFIES: this, trip
    // EFFECT: asks the user name of restaurant they want to delete.
    // Calls method deleteRestaurant. if deleted displays successful message. Otherwise, displays it does not exist
    private void deleteRestaurant(Trip trip) {
        System.out.println("Enter the name of the restaurant to delete:");
        String restaurantToDelete = input.nextLine();
        boolean added = trip.deleteRestaurant(restaurantToDelete);
        if (added) {
            System.out.println("Restaurant deleted successfully.");
        } else {
            System.out.println("Restaurant not found");
        }
    }


    // REQUIRES: valid user input not null, trip exists.
    // MODIFIES: this, trip
    // EFFECT: asks the user name of restaurant they want to edit and new name.
    // Calls method editNameRestaurant. if updated displays successful message. Otherwise, displays it does not exist
    private void editNameRestaurant(Trip trip) {
        System.out.println("Enter the name of the restaurant to edit:");
        String oldRestaurant = input.nextLine();
        System.out.println("Enter the new name for the restaurant:");
        String newRestaurantName = input.nextLine();
        boolean edited = trip.editNameRestaurant(oldRestaurant, newRestaurantName);
        if (edited) {
            System.out.println("Restaurant name updated successfully.");
        } else {
            System.out.println("Restaurant not found.");
        }
    }

    // REQUIRES: valid user input not null, trip exists.
    // MODIFIES: this, trip
    // EFFECT: asks the user name of attraction they want to add.
    // Calls method addAttraction. if added displays successful message. Otherwise, displays it already exists
    private void addAttractions(Trip trip) {
        System.out.println("Enter the name of the attraction to add:");
        String attractionToAdd = input.nextLine();
        boolean added = trip.addAttraction(attractionToAdd);
        if (added) {
            System.out.println("Attraction added successfully.");
        } else {
            System.out.println("Attraction already exists.");
        }
    }

    // REQUIRES: valid user input not null, trip exists.
    // MODIFIES: this, trip
    // EFFECT: asks the user name of attraction they want to delete.
    // Calls method deleteAttraction. if deleted displays successful message. Otherwise, displays it does not exist
    private void deleteAttraction(Trip trip) {
        System.out.println("Enter the name of the attraction to delete:");
        String attractionToDelete = input.nextLine();
        boolean deleted = trip.deleteAttraction(attractionToDelete);
        if (deleted) {
            System.out.println("Attraction deleted successfully.");
        } else {
            System.out.println("Attraction not found.");
        }
    }

    // REQUIRES: valid user input not null, trip exists.
    // MODIFIES: this, trip
    // EFFECT: asks the user name of attraction they want to edit and new name.
    // Calls method editNameAttraction. if updated displays successful message. Otherwise, displays it does not exist
    private void editNameAttraction(Trip trip) {
        System.out.println("Enter the name of the attraction to edit:");
        String oldAttraction = input.nextLine();
        System.out.println("Enter the new name for the attraction:");
        String newAttractionName = input.nextLine();
        boolean edited = trip.editNameAttraction(oldAttraction, newAttractionName);
        if (edited) {
            System.out.println("Attraction name updated successfully.");
        } else {
            System.out.println("Attraction not found.");
        }
    }

    // REQUIRES: valid user input not null, trip exists.
    // MODIFIES: this, trip
    // EFFECT: asks the user name of item they want to add.
    // Calls method addItem. if added displays successful message. Otherwise, displays it already exists
    private void addItemPackingList(Trip trip) {
        System.out.println("Enter the name of the item to add to the packing list:");
        String itemToAdd = input.nextLine();
        boolean added = trip.addItem(itemToAdd);
        if (added) {
            System.out.println("Item added to the packing list successfully.");
        } else {
            System.out.println("Item already exists in the packing list.");
        }
    }

    // REQUIRES: valid user input not null, trip exists.
    // MODIFIES: this, trip
    // EFFECT: asks the user name of item they want to delete.
    // Calls method deleteItem. if deleted displays successful message. Otherwise, displays it does not exist
    private void deleteItemPackingList(Trip trip) {
        System.out.println("Enter the name of the item to delete from the packing list:");
        String itemToDelete = input.nextLine();
        boolean deleted = trip.deleteItem(itemToDelete);
        if (deleted) {
            System.out.println("Item deleted from the packing list successfully.");
        } else {
            System.out.println("Item not found in the packing list.");
        }
    }

    // REQUIRES: valid user input not null, trip exists.
    // MODIFIES: this, trip
    // EFFECT: asks the user name of item they want to edit and new name.
    // Calls method editNameItem. if updated displays successful message. Otherwise, displays it does not exist
    private void editNameItem(Trip trip) {
        System.out.println("Enter the name of the item to edit in the packing list:");
        String oldItem = input.nextLine();
        System.out.println("Enter the new name for the item:");
        String newItemName = input.nextLine();
        boolean edited = trip.editNameItem(oldItem, newItemName);
        if (edited) {
            System.out.println("Item in the packing list updated successfully.");
        } else {
            System.out.println("Item not found in the packing list.");
        }
    }

    // REQUIRES: input not null
    // MODIFIES: this, trip
    // EFFECT: asks the user for the accommodation booking code they want to set and then sets it
    private void setBookingAccommodation(Trip trip) {
        System.out.println("Enter the booking number for accommodation:");
        String accommodationBookingNumber = input.nextLine();
        trip.setAccommodationBookingCode(accommodationBookingNumber);
    }

    // REQUIRES: input not null
    // MODIFIES: this, trip
    // EFFECT: asks the user for the booking code of transportation they want to set and then sets it
    private void setCodeTransportation(Trip trip) {
        System.out.println("Enter the booking code for transportation:");
        String transportationBookingCode = input.nextLine();
        trip.setTransportationBookingCode(transportationBookingCode);
    }

    // EFFECT: asks the user which optional characteristic for their trip they want to add and runs the method called by
    // that option if valid, otherwise displays error message
    private void setOptionalDetails(Trip trip) {
        boolean addingDetails = true;
        while (addingDetails) {
            optionalMenu();
            String option = input.nextLine().toLowerCase();
            switch (option) {
                case "r": addRestaurant(trip);
                    break;
                case "a": addAttractions(trip);
                    break;
                case "p": addItemPackingList(trip);
                    break;
                case "bn": setBookingAccommodation(trip);
                    break;
                case "bc": setCodeTransportation(trip);
                    break;
                case "q": addingDetails = false;
                    break;
                default:
                    System.out.println("Command not valid.");
                    break;
            }
        }
    }

    // EFFECT: asks the user for the option of the view trip menu and then calls the method called by the user option
    // if valid, otherwise displays error message
    private void viewTrips() {
        boolean viewingTrips = true;
        while (viewingTrips) {
            viewTripsMenu();
            String option = input.nextLine().toLowerCase();
            switch (option) {
                case "u": viewTripsByStatus(false);
                    break;
                case "n": viewTripsByName();
                    break;
                case "c": viewTripsByStatus(true);
                    break;
                case "a": viewAllTrips();
                    break;
                case "q": viewingTrips = false;
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    // EFFECT: displays the menu for displaying the trips
    private void viewTripsMenu() {
        System.out.println("\nView trips by: ");
        System.out.println("\tn -> By name");
        System.out.println("\tu -> Uncompleted trips");
        System.out.println("\tc -> Completed trips");
        System.out.println("\ta -> View all trips");
        System.out.println("\tq -> Go back");
    }

    // EFFECTS: asks the user for the status of trips they want to see displaayed. If trips with such status don't
    // exist, message of error is displayed. Otherwise, the trips with the chosen boolean for
    // the status are displayed.
    public void viewTripsByStatus(boolean completed) {
        List<Trip> tripsByStatus = tripList.getTripsByStatus(completed);
        if (tripsByStatus.isEmpty()) {
            System.out.println("No trips with the selected status found.");
        } else {
            System.out.println("\nTrips with the selected status:");
            for (Trip trip : tripsByStatus) {
                System.out.println("Name: " + trip.getName());
                System.out.println("Destination: " + trip.getDestination());
                System.out.println("Start Date: " + trip.startDateString());
                System.out.println("End Date: " + trip.endDateString());
                System.out.println("Initial Budget: " + trip.getInitialBudget());
                System.out.println("Balance: " + trip.getBalance());
                System.out.println("Total Amount Spent: " + trip.getTotalAmountSpent());
                System.out.println("Restaurants: " + trip.getAllRestaurants());
                System.out.println("Attractions: " + trip.getAllAttractions());
                System.out.println("Packing List: " + trip.getAllItems());
                System.out.println("Method of arrival: " + trip.getMethodOfArrival());
                System.out.println("Accommodation: " + trip.getAccommodation());
                System.out.println("Booking Number Accommodation: " + trip.getBookingNumberAccommodation());
                System.out.println("Booking Code Transportation: " + trip.getTransportationBookingCode());
                System.out.println("---------------------------------------------------------------");
            }
        }
    }

    //EFFECT: If there aren't any trips in the tripList, error message is displayed. Otherwise, displays all the trips
    // in the tripList and their characteristics no matter their status
    private void viewAllTrips() {
        List<Trip> allTrips = tripList.getAllTrips();
        if (allTrips.isEmpty()) {
            System.out.println("No trips found.");
        } else {
            for (Trip trip : allTrips) {
                System.out.println("Completed: " + (trip.getStatus() ? "Yes" : "No"));
                System.out.println("Name: " + trip.getName());
                System.out.println("Destination: " + trip.getDestination());
                System.out.println("Start Date: " + trip.startDateString());
                System.out.println("End Date: " + trip.endDateString());
                System.out.println("Initial Budget: " + trip.getInitialBudget());
                System.out.println("Balance: " + trip.getBalance());
                System.out.println("Total Amount Spent: " + trip.getTotalAmountSpent());
                System.out.println("Restaurants: " + trip.getAllRestaurants());
                System.out.println("Attractions: " + trip.getAllAttractions());
                System.out.println("Packing List: " + trip.getAllItems());
                System.out.println("Method of arrival: " + trip.getMethodOfArrival());
                System.out.println("Accommodation: " + trip.getAccommodation());
                System.out.println("Booking Number Accommodation: " + trip.getBookingNumberAccommodation());
                System.out.println("Booking Code Transportation: " + trip.getTransportationBookingCode());
                System.out.println("---------------------------------------------------------------");
            }
        }
    }

    // EFFECT: asks the user the name of the trip they want to see and displays the selected trip's characteristics
    // if the trip is not null, otherwise displays error message
    private void viewTripsByName() {
        System.out.println("Enter name of desired trip");
        String tripName = input.nextLine();
        Trip trip = tripList.getTrip(tripName);
        if (trip != null) {
            System.out.println("Completed: " + (trip.getStatus() ? "Yes" : "No"));
            System.out.println("Name: " + trip.getName());
            System.out.println("Destination: " + trip.getDestination());
            System.out.println("Start Date: " + trip.startDateString());
            System.out.println("End Date: " + trip.endDateString());
            System.out.println("Initial Budget: " + trip.getInitialBudget());
            System.out.println("Balance: " + trip.getBalance());
            System.out.println("Total Amount Spent: " + trip.getTotalAmountSpent());
            System.out.println("Restaurants: " + trip.getAllRestaurants());
            System.out.println("Attractions: " + trip.getAllAttractions());
            System.out.println("Packing List: " + trip.getAllItems());
            System.out.println("Method of arrival: " + trip.getMethodOfArrival());
            System.out.println("Accommodation: " + trip.getAccommodation());
            System.out.println("Booking Number Accommodation: " + trip.getBookingNumberAccommodation());
            System.out.println("Booking Code Transportation: " + trip.getTransportationBookingCode());
            System.out.println("---------------------------------------------------------------");
        } else {
            System.out.println("Trip not found.");
        }

    }

    // EFFECT: if the tripList is empty, displays error message. Otherwise, displays just the name of each trip in
    // the tripList
    private void seeTripNames() {
        List<Trip> allTrips = tripList.getAllTrips();
        if (allTrips.isEmpty()) {
            System.out.println("No trips found.");
        } else {
            System.out.println("\nAll trips:");
            for (Trip trip : allTrips) {
                System.out.println("Name: " + trip.getName());
            }
        }
    }

    // EFFECT> asks the user the name of trip to be deleted and displays a message if the trip was deleted, otherwise
    // displays the trip was not found
    private void deleteTrip() {
        System.out.println("Enter the name of the trip to delete:");
        String tripNameToDelete = input.nextLine();
        boolean deleted = tripList.deleteTrip(tripNameToDelete);
        if (deleted) {
            System.out.println("Trip deleted successfully.");
        } else {
            System.out.println("Trip not found.");
        }
    }

    // EFFECTS: saves the workroom to file
    private void saveTripList() {
        try {
            jsonWriter.open();
            jsonWriter.writeTripList(tripList);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadTripList() {
        try {
            tripList = jsonReader.readTripList();
            System.out.println("Loaded tripList from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }



}


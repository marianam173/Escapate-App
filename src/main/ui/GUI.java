package ui;

import model.*;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import model.Event;
import model.Trip;
import model.TripList;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the Escapate application
public class GUI extends JFrame {
    private TripList tripList;
    private JTextField searchingName;
    private JTextField status;
    private JTextField nameField;
    private JTextField destination;
    private JTextField endDate;
    private JTextField startDate;
    private JTextField initialBudget;
    private JTextField arrivalMethod;
    private JTextField accommodation;
    private JTextArea events;
    private JFrame createTripFrame;
    private JFrame setOptionals;
    private static final String JSON_STORE = "./data/tripData.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: initializes fields and calls the splash screen to run the graphical interface
    public GUI() {
        super("Trip Manager");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        tripList = new TripList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        showSplashScreen();
        status = new JTextField();
        nameField = new JTextField();
        destination = new JTextField();
        endDate = new JTextField();
        startDate = new JTextField();
        initialBudget = new JTextField();
        arrivalMethod = new JTextField();
        accommodation = new JTextField();
        searchingName = new JTextField();
    }

    // EFFECTS: Displays a splash screen with a background image and a "Start" button. When the "Start" button is
    // clicked, it opens the main menu.
    private void showSplashScreen() {
        JFrame splashFrame = new JFrame();
        splashFrame.setUndecorated(true);
        splashFrame.setSize(500, 500);
        ImageIcon backgroundImage = new ImageIcon("src/main/images/ESCAPATESPLASHAPP.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        splashFrame.setContentPane(backgroundLabel);
        JButton startButton = new JButton("Start");

        ActionListener startAction = startActionListener(startButton, splashFrame);
        startButton.addActionListener(startAction);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(startButton);
        splashFrame.setLayout(new BorderLayout());
        splashFrame.add(buttonPanel, BorderLayout.SOUTH);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - splashFrame.getWidth()) / 2;
        int y = (screenSize.height - splashFrame.getHeight()) / 2;
        splashFrame.setLocation(x, y);
        splashFrame.setVisible(true);
    }

    // Effects: Defines an action listener for the "Start" button, which opens the main menu and disposes of the splash
    // frame.
    private ActionListener startActionListener(JButton start, JFrame splashFrame) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == start) {
                    openMainMenu();
                    splashFrame.dispose();
                }
            }
        };
    }

    //Modifies: this
    //Effects: Opens the main menu, containing buttons for various actions like creating a new trip, viewing trips,
    //loading, saving, deleting, and exiting the application.
    private void openMainMenu() {
        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new GridBagLayout());
        mainMenuPanel.setOpaque(false);

        JButton createTripButton = new JButton("Create New Trip");
        JButton deleteTrip = new JButton("Delete a trip");
        JButton viewTrips = new JButton("View all trips");
        JButton loadButton = new JButton("Load from File");
        JButton saveButton = new JButton("Save to File");
        JButton exitButton = new JButton("Exit app");
        JButton optional = new JButton("Add optional characteristics");

        ActionListener buttonListenerMain = mainActionsPerformed(viewTrips, createTripButton, loadButton, saveButton,
                exitButton, optional, deleteTrip);

        deleteTrip.addActionListener(buttonListenerMain);
        createTripButton.addActionListener(buttonListenerMain);
        viewTrips.addActionListener(buttonListenerMain);
        loadButton.addActionListener(buttonListenerMain);
        saveButton.addActionListener(buttonListenerMain);
        exitButton.addActionListener(buttonListenerMain);
        optional.addActionListener(buttonListenerMain);

        addButtons(mainMenuPanel, createTripButton,viewTrips,loadButton,saveButton,exitButton,optional,deleteTrip);
        add(mainMenuPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //modifies: panel
    //effects: Adds buttons to the main menu panel in a specific layout.
    private void addButtons(JPanel panel, JButton create, JButton view, JButton load, JButton save, JButton exit,
                            JButton optional, JButton delete) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;

        panel.add(create, gbc);
        gbc.gridy++;
        panel.add(view, gbc);
        gbc.gridy++;
        panel.add(load, gbc);
        gbc.gridy++;
        panel.add(save, gbc);
        gbc.gridy++;
        panel.add(optional, gbc);
        gbc.gridy++;
        panel.add(delete, gbc);
        gbc.gridy++;
        panel.add(exit, gbc);

    }

    // effects: Defines action listener for main menu buttons to perform corresponding actions when clicked.
    private ActionListener mainActionsPerformed(JButton view, JButton create, JButton load, JButton save,
                                                JButton exit, JButton optional, JButton delete) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == view) {
                    viewAll();
                } else if (ae.getSource() == create) {
                    openCreateTripWindow();
                } else if (ae.getSource() == load) {
                    loadButtonAction();
                } else if (ae.getSource() == save) {
                    saveButtonAction();
                } else if (ae.getSource() == exit) {
                    preExitAction();
                    System.exit(0);
                } else if (ae.getSource() == optional) {
                    setOptionalDetails();
                } else if (ae.getSource() == delete) {
                    deleteTrip();
                }
            }
        };
    }

    private void preExitAction() {
        for (Event next : EventLog.getInstance()) {
            System.out.print(next.toString() + "\n\n");
        }
    }

    //MODIFIES: deleteF
    //effects: Displays a frame to ask for a specific trip the user wants to delete.
    private void deleteTrip() {
        JFrame deleteF = new JFrame("Delete trip");
        deleteF.setSize(600, 800);
        deleteF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        deleteF.setLayout(new BoxLayout(deleteF.getContentPane(), BoxLayout.Y_AXIS));
        deleteF.setLocationRelativeTo(null);

        JButton goBackButton = new JButton("Go Back");
        JButton deleteB = new JButton("Delete");

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding

        addInputField(inputPanel, gbc, "Which trip do you want to delete", searchingName);

        ActionListener buttonListenerOptionals = deleteListener(deleteF, goBackButton,deleteB);

        goBackButton.addActionListener(buttonListenerOptionals);
        deleteB.addActionListener(buttonListenerOptionals);

        deleteF.add(inputPanel, BorderLayout.CENTER);
        deleteF.add(goBackButton);
        deleteF.add(deleteB);
        deleteF.setVisible(true);
    }

    //modifies: tripList
    // EFFECTS: Defines an action listener for the "Go Back" and "Delete" buttons in the delete frame. if trip with
    // input name exists, it deletes it from triplist
    private ActionListener deleteListener(JFrame frame, JButton goBack, JButton deleteB) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == goBack) {
                    frame.dispose();
                } else if (ae.getSource() == deleteB) {
                    Trip tripGone = tripList.getTrip(searchingName.getText());
                    if (tripGone != null) {
                        tripList.deleteTrip(tripGone.getName());
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(GUI.this, "Trip not found!");
                    }

                }
            }
        };
    }

    //MODIFIES: setOptionals
    //effects: Displays a frame to set additional characteristics for a specific trip.
    private void setOptionalDetails() {
        setOptionals = new JFrame("Set additional characteristics");
        setOptionals.setSize(600, 800);
        setOptionals.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setOptionals.setLayout(new BoxLayout(setOptionals.getContentPane(), BoxLayout.Y_AXIS));
        setOptionals.setLocationRelativeTo(null);

        JButton goBackButton = new JButton("Go Back");
        JButton save = new JButton("Next");

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding

        addInputField(inputPanel, gbc, "To which trip do you wish to add more information?", searchingName);

        ActionListener buttonListenerOptionals = optionalListener(goBackButton,save);

        goBackButton.addActionListener(buttonListenerOptionals);
        save.addActionListener(buttonListenerOptionals);

        setOptionals.add(inputPanel, BorderLayout.CENTER);
        setOptionals.add(goBackButton);
        setOptionals.add(save);
        setOptionals.setVisible(true);
    }

    // EFFECTS: Defines an action listener for the "Go Back" and "Next" buttons in the optional details frame.
    private ActionListener optionalListener(JButton goBack, JButton next) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == goBack) {
                    setOptionals.dispose();
                } else if (ae.getSource() == next) {
                    Trip tripAdd = tripList.getTrip(searchingName.getText());
                    if (tripAdd != null) {
                        optionalPanel(tripAdd);
                    } else {
                        JOptionPane.showMessageDialog(GUI.this, "Trip not found!");
                    }

                }
            }
        };
    }

    //requires: not null trip
    //modifies: addOptional
    //effects: displays a frame to choose what additional information to add to a trip.
    private void optionalPanel(Trip trip) {
        JFrame addOptional = new JFrame("choose what you want to add");
        addOptional.setSize(600, 800);
        addOptional.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addOptional.setLayout(new BoxLayout(addOptional.getContentPane(), BoxLayout.Y_AXIS));
        addOptional.setLocationRelativeTo(null);

        JButton goBackButton = new JButton("Go Back");
        JButton addItems = new JButton("add item to packing list");
        JButton addRestaurant = new JButton("add restaurant");
        JButton addActivity = new JButton("add activity");
        JButton addBookingNumber = new JButton("add booking number");
        JButton addTransportCode = new JButton("add transportation code");

        ActionListener buttonListenerOptionals = choosing(addOptional, trip, goBackButton, addItems, addRestaurant,
                addActivity, addBookingNumber, addTransportCode);

        goBackButton.addActionListener(buttonListenerOptionals);
        addItems.addActionListener(buttonListenerOptionals);
        addRestaurant.addActionListener(buttonListenerOptionals);
        addActivity.addActionListener(buttonListenerOptionals);
        addBookingNumber.addActionListener(buttonListenerOptionals);
        addTransportCode.addActionListener(buttonListenerOptionals);

        addButtonsOptional(addOptional, goBackButton, addItems, addRestaurant, addActivity, addBookingNumber,
                addTransportCode);

        addOptional.setVisible(true);
    }

    //modifies: addOptional frame
    //effects: Adds buttons to the adding optional frame
    private void addButtonsOptional(JFrame frame, JButton back, JButton items, JButton restaurant, JButton activity,
                                    JButton booking, JButton code) {
        frame.add(back);
        frame.add(items);
        frame.add(restaurant);
        frame.add(activity);
        frame.add(booking);
        frame.add(code);
    }

    // requires: not null trip
    // Effects: Defines an action listener for choosing different types of additional information to add to a trip.
    private ActionListener choosing(JFrame frame, Trip trip, JButton back, JButton item, JButton res, JButton act,
                                    JButton book, JButton code) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == back) {
                    frame.dispose();
                } else if (ae.getSource() == item) {
                    addItem(trip);
                } else if (ae.getSource() == res) {
                    addRestaurants(trip);
                } else if (ae.getSource() == act) {
                    addActivities(trip);
                } else if (ae.getSource() == book) {
                    addBooking(trip);
                } else if (ae.getSource() == code) {
                    addCodeTransport(trip);
                }
            }
        };
    }

    // requires: not null trip
    // effects: Displays a frame to add an item to the packing list of a trip.
    private void addItem(Trip trip) {
        JFrame frame = new JFrame("item to add");
        frame.setSize(600, 800);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField item = new JTextField();
        addInputField(inputPanel, gbc, "Add your item: ", item);

        JButton goBackButton = new JButton("Go Back");
        JButton save = new JButton("Save");

        ActionListener buttonListenerOptionals = itemAction(item, frame, trip, goBackButton, save);

        goBackButton.addActionListener(buttonListenerOptionals);
        save.addActionListener(buttonListenerOptionals);

        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(goBackButton);
        frame.add(save);
        frame.setVisible(true);
    }

    //requires: not null trip
    //modifies: trip
    // effects: Defines an action listener for adding an item to the packing list of a trip.
    private ActionListener itemAction(JTextField item, JFrame frame, Trip trip, JButton goBackButton, JButton save) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == goBackButton) {
                    frame.dispose();
                } else if (ae.getSource() == save) {
                    trip.addItem(item.getText());
                    frame.dispose();
                }
            }
        };
    }

    // requires: not null trip
    // effects: Displays a frame to add a restaurant to the list in the trip.
    private void addRestaurants(Trip trip) {
        JFrame frame = new JFrame("restaurant to add");
        frame.setSize(600, 800);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField restaurant = new JTextField();
        addInputField(inputPanel, gbc, "Add your restaurant: ", restaurant);

        JButton goBackButton = new JButton("Go Back");
        JButton save = new JButton("Save");

        ActionListener buttonListenerOptionals = restaurantAction(restaurant, frame, trip, goBackButton, save);
        goBackButton.addActionListener(buttonListenerOptionals);
        save.addActionListener(buttonListenerOptionals);
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(goBackButton);
        frame.add(save);
        frame.setVisible(true);
    }

    //requires: not null trip
    //modifies: trip
    // effects: Defines an action listener for adding a restaurant to the list in the trip.
    private ActionListener restaurantAction(JTextField restaurant, JFrame frame, Trip trip, JButton goBackButton,
                                            JButton save) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == goBackButton) {
                    frame.dispose();
                } else if (ae.getSource() == save) {
                    trip.addRestaurant(restaurant.getText());
                    frame.dispose();
                }
            }
        };
    }

    // requires: not null trip
    // effects: Displays a frame to add an attraction to the list in the trip.
    private void addActivities(Trip trip) {
        JFrame frame = new JFrame("activity to add");
        frame.setSize(600, 800);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField activity = new JTextField();
        addInputField(inputPanel, gbc, "Add your attraction: ", activity);

        JButton goBackButton = new JButton("Go Back");
        JButton save = new JButton("Save");

        ActionListener buttonListenerOptionals = activityAction(activity, frame, trip, goBackButton, save);

        goBackButton.addActionListener(buttonListenerOptionals);
        save.addActionListener(buttonListenerOptionals);
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(goBackButton);
        frame.add(save);
        frame.setVisible(true);
    }

    //requires: not null trip
    //modifies: trip
    // effects: Defines an action listener for adding an attraction to the list in the trip.
    private ActionListener activityAction(JTextField activity, JFrame frame, Trip trip, JButton goBackButton,
                                            JButton save) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == goBackButton) {
                    frame.dispose();
                } else if (ae.getSource() == save) {
                    trip.addAttraction(activity.getText());
                    frame.dispose();
                }
            }
        };
    }

    // requires: not null trip
    // effects: Displays a frame to add a booking number to the trip.
    private void addBooking(Trip trip) {
        JFrame frame = new JFrame("booking to add");
        frame.setSize(600, 800);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField booking = new JTextField();
        addInputField(inputPanel, gbc, "Write your booking number: ", booking);

        JButton goBackButton = new JButton("Go Back");
        JButton save = new JButton("Save");

        ActionListener buttonListenerOptionals = bookingAction(booking, frame, trip, goBackButton, save);
        goBackButton.addActionListener(buttonListenerOptionals);
        save.addActionListener(buttonListenerOptionals);
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(goBackButton);
        frame.add(save);
        frame.setVisible(true);
    }

    //requires: not null trip
    //modifies: trip
    // effects: Defines an action listener for adding a booking number trip.
    private ActionListener bookingAction(JTextField booking, JFrame frame, Trip trip, JButton goBackButton,
                                          JButton save) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == goBackButton) {
                    frame.dispose();
                } else if (ae.getSource() == save) {
                    trip.setAccommodationBookingCode(booking.getText());
                    frame.dispose();
                }
            }
        };
    }

    // requires: not null trip
    // effects: Displays a frame to add a transportation code to the trip.
    private void addCodeTransport(Trip trip) {
        JFrame frame = new JFrame("transport code to add");
        frame.setSize(600, 800);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField code = new JTextField();
        addInputField(inputPanel, gbc, "Write your transportation code: ", code);

        JButton goBackButton = new JButton("Go Back");
        JButton save = new JButton("Save");

        ActionListener buttonListenerOptionals = codeAction(code,frame,trip,goBackButton,save);
        goBackButton.addActionListener(buttonListenerOptionals);
        save.addActionListener(buttonListenerOptionals);
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(goBackButton);
        frame.add(save);
        frame.setVisible(true);
    }

    //requires: not null trip
    //modifies: trip
    // effects: Defines an action listener for adding a transportation code to the trip.
    private ActionListener codeAction(JTextField code, JFrame frame, Trip trip, JButton goBackButton,
                                         JButton save) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == goBackButton) {
                    frame.dispose();
                } else if (ae.getSource() == save) {
                    trip.setTransportationBookingCode(code.getText());
                    frame.dispose();
                }
            }
        };
    }

    //Modifies: jsonWriter
    //Effects: Saves the current list of trips to a JSON file and displays a confirmation window by calling the method.
    private void saveButtonAction() {
        try {
            jsonWriter = new JsonWriter(JSON_STORE);
            jsonWriter.open();
            jsonWriter.writeTripList(tripList);
            jsonWriter.close();
            showSaveConfirmationWindow();
        } catch (FileNotFoundException e2) {
            JOptionPane.showMessageDialog(GUI.this, "unable to write");
        }
    }

    //Modifies: tripList
    //Effects: Loads the list of trips from a JSON file and displays a confirmation window by calling the method.
    private void loadButtonAction() {
        try {
            tripList = jsonReader.readTripList();
            showLoadConfirmationWindow();
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(GUI.this, "unable to read");
        }
    }

    //Effects: Displays a confirmation window for successfully loading trips from a file.
    private void showLoadConfirmationWindow() {

        JFrame confirmationFrame = new JFrame("Load Confirmation");
        confirmationFrame.setSize(400, 400);
        confirmationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Load image for the background
        ImageIcon savedImage = new ImageIcon("src/main/images/load.png");
        JLabel imageLabel = new JLabel(savedImage);

        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmationFrame.dispose();
            }
        });

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(400, 400));

        imageLabel.setBounds(0, 0, 400, 400);
        goBackButton.setBounds(150, 150, 200, 30);

        layeredPane.add(imageLabel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(goBackButton, JLayeredPane.PALETTE_LAYER);

        confirmationFrame.add(layeredPane);
        confirmationFrame.setLocationRelativeTo(null);
        confirmationFrame.setVisible(true);
    }

    // Effects: Displays a confirmation window for successfully saving trips to a file.
    private void showSaveConfirmationWindow() {

        JFrame confirmationFrame = new JFrame("Saved Confirmation");
        confirmationFrame.setSize(400, 400);
        confirmationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Load image for the background
        ImageIcon savedImage = new ImageIcon("src/main/images/TRIPSSAVED.png");
        JLabel imageLabel = new JLabel(savedImage);

        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmationFrame.dispose(); // Close the confirmation window
            }
        });

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(400, 400));

        imageLabel.setBounds(0, 0, 400, 400);
        goBackButton.setBounds(150, 150, 200, 30);

        layeredPane.add(imageLabel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(goBackButton, JLayeredPane.PALETTE_LAYER);

        confirmationFrame.add(layeredPane);
        confirmationFrame.setLocationRelativeTo(null);
        confirmationFrame.setVisible(true);
    }

    // Effect: Displays a window with information about all trips in the trip list
    private void viewAll() {
        JFrame viewAllTrips  = new JFrame("Load Window");
        viewAllTrips.setSize(600, 800);
        viewAllTrips.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        viewAllTrips.setLocationRelativeTo(null);

        viewAllTrips.setLayout(new FlowLayout());
        List<Trip> allTrips = tripList.getAllTrips();
        if (allTrips.isEmpty()) {
            JLabel message = new JLabel("No trips");
            viewAllTrips.add(message);
        } else {
            displayTripsInformation(allTrips, viewAllTrips);
        }
        JButton goBackButton = new JButton("Go Back to Main Menu");
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllTrips.dispose(); // Close the loadFrame
            }
        });
        viewAllTrips.add(goBackButton);
        viewAllTrips.setVisible(true);
    }

    // Modifies: frame
    // Effect: Displays trip information in a JTextArea and adds it to the given JFrame
    private void displayTripsInformation(List<Trip> allTrips, JFrame frame) {
        JTextArea tripsTextArea = new JTextArea();
        tripsTextArea.setEditable(false);
        for (Trip trip : allTrips) {
            tripsTextArea.append("Completed: " + (trip.getStatus() ? "Yes" : "No") + "\n");
            tripsTextArea.append("Name: " + trip.getName() + "\n");
            tripsTextArea.append("Destination: " + trip.getDestination() + "\n");
            tripsTextArea.append("Start Date: " + trip.startDateString() + "\n");
            tripsTextArea.append("End Date: " + trip.endDateString() + "\n");
            tripsTextArea.append("Initial Budget: " + trip.getInitialBudget() + "\n");
            tripsTextArea.append("Balance: " + trip.getBalance() + "\n");
            tripsTextArea.append("Total Amount Spent: " + trip.getTotalAmountSpent() + "\n");
            tripsTextArea.append("Restaurants: " + trip.getAllRestaurants() + "\n");
            tripsTextArea.append("Attractions: " + trip.getAllAttractions() + "\n");
            tripsTextArea.append("Packing List: " + trip.getAllItems() + "\n");
            tripsTextArea.append("Method of arrival: " + trip.getMethodOfArrival() + "\n");
            tripsTextArea.append("Accommodation: " + trip.getAccommodation() + "\n");
            tripsTextArea.append("Booking Number Accommodation: " + trip.getBookingNumberAccommodation() + "\n");
            tripsTextArea.append("Booking Code Transportation: " + trip.getTransportationBookingCode() + "\n");
            tripsTextArea.append("---------------------------------------------------------------\n");
        }
        frame.add(tripsTextArea);
    }

    //modifies: createTripFrame
    // Effect: Opens a window to create a new trip with input fields
    private void openCreateTripWindow() {
        createTripFrame = new JFrame("Create New Trip");
        createTripFrame.setSize(600, 800);
        createTripFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createTripFrame.setLocationRelativeTo(null);
        createTripFrame.setLayout(new BoxLayout(createTripFrame.getContentPane(), BoxLayout.Y_AXIS));
        JButton goBackButton = new JButton("Go Back");
        JButton save = new JButton("Save");

        gettingInformation();

        ActionListener buttonListenerMain = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == goBackButton) {
                    createTripFrame.dispose();
                } else if (ae.getSource() == save) {
                    saveTrip();
                }
            }
        };

        goBackButton.addActionListener(buttonListenerMain);
        save.addActionListener(buttonListenerMain);
        createTripFrame.add(goBackButton);
        createTripFrame.add(save);
        createTripFrame.setVisible(true);
    }

    //modifies> createTripFrame
    // Effect: Adds input fields to createTripFrame for user to input trip information
    private void gettingInformation() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding

        addInputField(inputPanel, gbc, "Have you completed this trip before (yes/no)", status);
        addInputField(inputPanel, gbc, "Name of your trip:", nameField);
        addInputField(inputPanel, gbc, "Start date in integer format day/month/year", startDate);
        addInputField(inputPanel, gbc, "End date in integer format day/month/year", endDate);
        addInputField(inputPanel, gbc, "What is your destination:", destination);
        addInputField(inputPanel, gbc, "What is your initial budget:", initialBudget);
        addInputField(inputPanel, gbc, "What is your accommodation:", accommodation);
        addInputField(inputPanel, gbc, "What is your method of arrival:", arrivalMethod);
        createTripFrame.add(inputPanel, BorderLayout.CENTER);
    }

    // Modifies: panel
    // Effect: Adds a JLabel and a JTextField to the given JPanel with specific layout settings
    private void addInputField(JPanel panel, GridBagConstraints gbc, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(textField, gbc);

        Dimension textFieldSize = new Dimension(200, 25);
        textField.setPreferredSize(textFieldSize);
    }

    //modifies: tripList
    // Effect: Retrieves user input, creates a new Trip, adds it to the trip list, and closes the createTripFrame
    private void saveTrip() {

        String name = nameField.getText();
        String start = startDate.getText();
        String end = endDate.getText();
        String completed = status.getText();
        String place = destination.getText();
        String budget = initialBudget.getText();
        String arrival = arrivalMethod.getText();
        String staying = accommodation.getText();

        try {
            List<Integer> starts = getDateFormat(start);
            List<Integer> ends = getDateFormat(end);
            boolean complete = completed.equalsIgnoreCase("yes");
            double budgetInitial = Double.parseDouble(budget);
            Trip trip = new Trip(complete,name,place,starts.get(0),starts.get(1),starts.get(2),ends.get(0),
                    ends.get(1),ends.get(2),budgetInitial,arrival,staying);
            tripList.addTrip(trip);
            createTripFrame.dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid format");
        }
    }

    // Effect: Parses date input and returns a list of integers representing day, month, and year
    private List<Integer> getDateFormat(String input) {
        ArrayList<Integer> dateList = new ArrayList<>();
        String[] parts = input.split("/");

        for (String part : parts) {
            try {
                int value = Integer.parseInt(part);
                dateList.add(value);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(GUI.this, "Invalid number format");
            }
        }

        return dateList;
    }

}


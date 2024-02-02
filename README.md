# My Personal Project

## Vacation Planner: Escapate
My application *Escapate* is a travel app that will be designed to help travellers
around the world organize their trips in a easy way  This application lets the user 
input trip details like: a packing list, method of arriving to destination, destination, 
expense tracker, dates (start and end), budget, accommodation, attractions, and restaurants for their destination. This app is intended for 
any type of user. From families going on vacations, to a couple or backpackers. Since
the whole purpose of this app is to make travelling much more organized and easy to
follow through, it will be useful for every kind of traveller that wants an ejoyable, 
and stress-free trip. It caters to people that value efficiency and clarity. *Escapate*
offers the opportunity to have a clear interface with an all-in-one experience instead
of having to use several apps to see their flight number, or their budget and expense control. 

This project is of my interest because since I was little my family and I have traveled a lot, 
and every time we did, my mom always plans the trip through different applications and platforms 
which works to some extent. Because of this use of various applications, she was always stressed 
because there was never one place where all the travel information was available. This is why I 
always wanted to create an easy solution for this and that is creating an app with everything available within itself.
Furthermore, *Escapate* aligns with the growing trend of travel enthusiasts seeking digital solutions 
to streamline their travel plans, and I find the prospect of creating a practical and enjoyable 
tool for other travelers quite exciting and fulfilling.

# User stories:
- **As a user**, I want to be able to see and access my past and future trips along with their specifications
- **As a user**, I want to be able to add a new trip to my trip list
- **As a user**, I want to be able to remove past or future trips from my trip list
- **As a user**, I want to be able to modify any variable of my trip even after being created 
- **As a user**, I want to be able to see my list of trips and mark them as already completed or not 
- **As a user**, when I look at the main menu, I want to have the options to save my trips if I want to
- **As a user**, when I look at the main menu, I want there to have an option to load my trips from file if I want to
- **As a user**, I want to be able to add multiple trips to the tripList. I want to see a frame in which all the trips 
that have already been added to my list of trips are displayed. 
- **As a user**, I want to still be able to add optional fields/characteristics to my trips, like restaurants and items.
- **As a user**, I want to be able to load and save the state of the application whenever I want from the main menu
- **As a user**, I want to be able to delete a trip of my choice from the name

# Phase 4: Task 2

Tue Nov 28 21:24:16 PST 2023
Trip with name "new trip 1" added

Tue Nov 28 21:24:33 PST 2023
Item: item 1, added to packing list to the trip: new trip 1

Tue Nov 28 21:24:38 PST 2023
Restaurant: restaurant 1, added to restaurant list to the trip: new trip 1

Tue Nov 28 21:24:48 PST 2023
Accommodation booking code set to : 123booking number, for the trip: new trip 1

Tue Nov 28 21:24:52 PST 2023
Attraction: activity 1, added to attraction list to the trip: new trip 1

Tue Nov 28 21:24:58 PST 2023
Transportation booking code set to : 123 code trip 1, for the trip: new trip 1

Tue Nov 28 21:25:26 PST 2023
Trip with name "new trip 2" added

Tue Nov 28 21:25:39 PST 2023
Attraction: activity 1, added to attraction list to the trip: new trip 2

Tue Nov 28 21:25:43 PST 2023
Attraction: activity 2, added to attraction list to the trip: new trip 2

Tue Nov 28 21:25:48 PST 2023
Transportation booking code set to : code2, for the trip: new trip 2

Tue Nov 28 21:25:55 PST 2023
Accommodation booking code set to : booking2, for the trip: new trip 2

Tue Nov 28 21:26:04 PST 2023
Item: item2, added to packing list to the trip: new trip 2

Tue Nov 28 21:26:23 PST 2023
Trip with name "new trip 2" was deleted

# Phase 4: Task 3

If I had more time, I would have created more classes to maintain as much as possible single
responsibility. For example,extracting an expense logic class.
The expense-related logic in the Trip class could be quite extensive. If this logic grows, 
consider extracting it into a separate class (e.g., ExpenseTracker) to maintain a single responsibility 
for each class and improve readability.
Similarly, I would have created another class as a date object. For example, LocalDate object for an object 
from the java.time package, encapsulating all date-related information in a more standardized manner.

Another potential improvement could be handling the collections (e.g., restaurants, attractions, and packingList) within the Trip class. Currently, these 
collections are represented as lists of strings. If the complexity of these entities increases, or if there 
is a need for more structured information that is more readable, another separate classes for each type of 
item (e.g., Restaurant, Attraction, and PackingListItem) could be better. This refactoring would enhance maintainability and 
provide a more extensible design.

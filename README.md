# Casey Conley

> cdc214@lehigh.edu

> CSE 241

> Semester Project - "Conley Airlines"

Note!*** This program will not run because the database I used does not exist anymore. One could theoretically create a database and enter all the tables and create randomly generated data to run it, but that's more trouble than it's worth.

## Description

### ConleyAirlines.java

This is the main class from which you can open the various interfaces I have implemented. There are 3 options: Database Administrator, Customer, and Manager.

### DatabaseAdmin.java

This is the class for the Database Administrator interface. The only options within this interface are to run queries using generic "select ..." 
statements, or to perform an "update", "insert", or "delete" statement. This was for me to use when I did not have access to SQL Developer. 

I would advise you not to perform any update/insert/delete queries, especially involving the TRIP, LEG, LEG_TO, LEG_FROM, AIRPORT, etc. to ensure that
flights do not disappear from reservations, making my code (possibly) break.

### Customer.java

This is the class for the customer interface. The first thing I did was create a log-in system using the customer ID. Passwords were an idea I was
toying with, but I ultimately decided it was unnecessary, as I am not a real airline protecting real information. You can simply log-in with a customer
ID you used beforehand, or create a brand new one, which is randomly generated after inputting a first and last name. In the "Usage" section of this
README, I have given a customer ID to use if you want, but it is completely optional.

My aim was to simulate some very commonly performed actions that customers of airlines would like to know. This includes management of billing
information, such as adding a credit card, deleting a credit card, or viewing all of your credit cards. 

The customer can also make a reservation, view their reservations, or cancel a reservation.

### Manager.java

This is the class for the manager interface. I plan on creating different kinds of managers which access priveleges to different functions, with these functions being visible to all. If they have access to the option, the function executes normally. If not, they are given an "Access Denied: Insufficient Priveleges" message and returned to the main menu for the manager interface. Implementation of this idea involves static boolean arrays for each manager type, with true/false values for the index of the array corresponding to the number option in the interface. For example, if the employee is just a gate attendant, I only want them to be able to access the seats available, who is assigned to those seats, and the ability to add and delete people from those seats. Any other administrative powers should be denied to them. So the array for a Gate Attendant should look like [true, false, false, false, ..., false] where the one true value's index is the option they can access. The different kinds of managers and their correspoding access arrays are as follows:

* Ticket Clerk - [true, false, false, false, false, false]
* General Manager - [true, true, true, true, true, true]
* Engineer - [false, false, false, false, true, false]
* Pilot Manager - [false, true, false, true, true, false]
* Customer Support - [true, false, false, false, false, true]

The array values indicate what they have access to:

1. Manage Customer Reservations
2. Manage Legs
3. Manage Flights
4. Manage Pilots
5. Manage Planes
6. Manage Customer credit card information


## Usage

### Conley Airlines Interface

Enter the number corresponding to the option you want to choose. Each option takes you to an interface. 
* 1 --> Database Administrator Interface
* 2 --> Manager
* 3 --> Customer
* 4 --> Exits the program

### Database Administrator Interface

Enter the number corresponding to the option you want to choose. 

* 1 --> Execute a standard SQL query (SELECT statements)
* 2 --> Execute a standard SQL update (UPDATE/INSERT/DELETE statements)
* 3 --> Close the DB admin interface and return to the main interface.

### Customer Interface

Log in with your customer ID or create your own. The ID I have created for you to use if you wish is 783523. 

Enter the number corresponding to the option you want to choose.

* 1 --> Manage Credit Card Information
* 2 --> Make a reservation
* 3 --> View reservations
* 4 --> Cancel a reservation
* 5 --> Close the customer interface and return to the main interface.


#### Manage Credit Card Information
* 1 --> Add a credit card: Allows you to add a credit card to your account to pay for trips with. Credit card numbers are 16 digits long.
* 2 --> View my credit card info: Lists all the credit cards currently owned by the customer.
* 3 --> Delete credit card: Gives the option of removing a credit card.
* 4 --> Go back
	
#### Make a reservation
Enter an airport as a source and destination location. Then select a flight available, as well as your desired seat class. If seats are available, you are prompted for which credit card to pay for the reservation. After selecting your credit card, a confirmation message will appear. When you are done placing a reservation, you are returned to the main menu.

#### View reservations
Lists all the reservations currently held by the customer.

#### Cancel a reservation
Gives the option of canceling a reservation.

#### Close the Customer Interface and return to the main interface.

### Manager interface



## Citation of Code Usage

### Seth Denburg 
> is_type_Casey_from_Seth.java 

### Steve Leonhardt

> LegDataPopulator_Casey_from_Steve.java

********************************************
* Casey Conley                             *
* cdc214  							   								 *
* CSE 241								   								 *
* Semester Project - "Conley Airlines"     *
********************************************

Table of Contents
	1. Description
		a. ConleyAirlines.java
		b. DatabaseAdmin.java
		c. Customer.java
		d. Manager.java
	2. Usage
		a. ConleyAirlines.java
		b. DatabaseAdmin.java
		c. Customer.java
		d. Manager.java
	3. Citation of Code Usage
	
*********************
* 1. Description		*
*********************

a. ConleyAirlines.java

This is the main class from which you can open the various interfaces I have implemented. There are 3 options: Database Administrator, Customer, and Manager.

b. DatabaseAdmin.java

This is the class for the Database Administrator interface. The only options within this interface are to run queries using generic "select ..." statements, or to perform an "update", "insert", or "delete" statement. This was for me to use when I did not have access to SQL Developer. 

I would advise you not to perform any update/insert/delete queries, especially involving the TRIP, LEG, LEG_TO, LEG_FROM, AIRPORT, etc. to ensure that flights do not disappear from reservations, making my code (possibly) break.

c. Customer.java

This is the class for the customer interface. The first thing I did was create a log-in system using the customer ID. Passwords were an idea I was toying with, but I ultimately decided it was unnecessary, as I am not a real airline protecting real information. You can simply log-in with a customer ID you used beforehand, or create a brand new one, which is randomly generated after inputting a first and last name. In the "Usage" section of this README, I have given a customer ID to use if you want, but it is completely optional.

My aim was to simulate some very commonly performed actions that customers of airlines would like to know. This includes management of billing information, such as adding a credit card, deleting a credit card, or viewing all of your credit cards. 

The customer can also make a reservation, view their reservations, or 

d. Manager.java

This is the class for the manager interface. I plan on creating different kinds of managers which access priveleges to different functions, with these functions being visible to all. If they have access to the option, the function executes normally. If not, they are given an "Access Denied: Insufficient Priveleges" message and returned to the main menu for the manager interface. Implementation of this idea involves static boolean arrays for each manager type, with true/false values for the index of the array corresponding to the number option in the interface. For example, if the employee is just a gate attendant, I only want them to be able to access the seats available, who is assigned to those seats, and the ability to add and delete people from those seats. Any other administrative powers should be denied to them. So the array for a Gate Attendant should look like [true, false, false, false, ..., false] where the one true value's index is the option they can access. The different kinds of managers and their correspoding access arrays are as follows:

Gate Attendant - [true, false, false, ...]

*************
* 2. Usage	*
*************

a. ConleyAirlines.java

Enter the number corresponding to the option you want to choose. Each option takes you to an interface. 
1 --> Database Administrator Interface
2 --> Manager
3 --> Customer
4 --> Exits the program

b. DatabaseAdmin.java

Enter the number corresponding to the option you want to choose. 

1 --> Execute a standard SQL query (SELECT statements)
2 --> Execute a standard SQL update (UPDATE/INSERT/DELETE statements)
3 --> Close the DB admin interface and return to the main interface.

c. Customer.java

Log in with your customer ID or create your own. The ID I have created for you to use if you wish is 783523. 

Enter the number corresponding to the option you want to choose.

1 --> Manage Credit Card Information
2 --> Make a reservation
3 --> View reservations
4 --> Cancel a reservation
5 --> Close the customer interface and return to the main interface.

d. Manager.java

*****************************
* 3. Citation of Code Usage	*
*****************************


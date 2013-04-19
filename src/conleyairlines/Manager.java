
package conleyairlines;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Casey Conley
 * Email: cdc214@lehigh.edu
 * Course: CSE 241
 * Title: Airline Semester Project
 * Purpose: The user interface for a manager of a fictional 
 *          airline company. Uses JDBC with a database housing the information 
 *          for the airline.
 * 
 */
public class Manager {
    Connection con;
    Scanner in;
    
    public Manager(Connection con, Scanner in){
        this.con = con;
        this.in = in;
    }
    
    public void openInterface(){
        System.out.println("Hello, please specify who you are.");
        boolean[] managerType = new boolean[6];
        managerType = selectManagerType();
        boolean quit = true;
        for(int i = 0; i<6; i++){
            quit = quit ^ managerType[i];
        }
        if(!quit){
            System.out.println("What action would you like to perform?");
            printOptions();
            boolean done = false;
            while(!done){
                int userChoice;
                try{
                    userChoice = in.nextInt();
                    in.nextLine();
                    if (userChoice != 7 && managerType[userChoice-1] == false){
                        System.out.println("Access Denied: Insufficient "
                                + "priveleges to access this interface.");
                        done = false;
                    }
                    else{
                        int customerID;
                        switch(userChoice){
                            case 1:
                                System.out.println("Add/View/Remove customer reservations chosen\n");
                                System.out.println("Please enter the customer's ID number.");
                                in.nextLine();
                                customerID = in.nextInt();
                                Customer cTick = new Customer(con, in);
                                cTick.makeAReservation(customerID, true);
                                done = false;
                                break;
                            case 2:
                                System.out.println("Add/View/Remove legs chosen\n");
                                in.nextLine();
                                manageLegs();
                                done = false;
                                break;
                            case 3:
                                System.out.println("Add/View/Remove flights\n");
                                manageFlights();
                                done = false;
                                break;
                            case 4:
                                System.out.println("Add/View/Remove pilots chosen\n");
                                managePilots();
                                done = false;
                                break;
                            case 5:
                                System.out.println("Add/View/Remove planes chosen\n");
                                managePlanes();
                                done = false;
                                break;
                            case 6:
                                System.out.println("Add/View/Remove customer credit cards chosen\n");
                                Customer cTech = new Customer(con,in);
                                System.out.println("Please enter the customer's ID number.");
                                in.nextLine();
                                customerID = in.nextInt();
                                cTech.manageCreditCardInfo(customerID, true);
                                done = false;
                                break;
                            case 7:
                                System.out.println("Exiting Manager Interface.\n");
                                done = true;
                                break;
                            default:
                                System.out.println("Error: Please enter a valid number choice.");
                                in.nextLine();
                                done = false;
                                break;
                        }
                    }
                } catch (InputMismatchException | IndexOutOfBoundsException ex) {
                    System.out.println("Error: Please enter a valid number choice.");

                    done = false;
                }
                catch (IllegalStateException ex) {
                    in = new Scanner(System.in);
                    System.out.println("Error: Internal Error, scanner closed, "
                            + "reinitialized. \nPlease try again.");

                    done = false;
                }
                if(!done){
                    System.out.println("What action would you like to perform?");
                    printOptions();
                }
            }
        }
        
    }
    
    private boolean[] selectManagerType(){
        printManagerOptions();
        boolean[] managerType = new boolean[6];
        boolean done = false;
        while(!done){
            int userChoice;
            try{
                userChoice = in.nextInt();
                in.nextLine();
                switch(userChoice){
                    case 1: //ticket clerk
                        managerType[0] = true;
                        managerType[1] = false;
                        managerType[2] = false;
                        managerType[3] = false;
                        managerType[4] = false;
                        managerType[5] = false;
                        System.out.println("Ticket Clerk selected\n");
                        done = true;
                        break;
                    case 2: //general manager
                        managerType[0] = true;
                        managerType[1] = true;
                        managerType[2] = true;
                        managerType[3] = true;
                        managerType[4] = true;
                        managerType[5] = true;
                        System.out.println("General Manager selected\n");
                        done = true;
                        break;
                    case 3: //Engineer
                        managerType[0] = false;
                        managerType[1] = false;
                        managerType[2] = false;
                        managerType[3] = false;
                        managerType[4] = true;
                        managerType[5] = false;
                        System.out.println("Engineer selected\n");
                        done = true;
                        break;
                    case 4: //Pilot manager
                        managerType[0] = false;
                        managerType[1] = true;
                        managerType[2] = false;
                        managerType[3] = true;
                        managerType[4] = true;
                        managerType[5] = false;
                        System.out.println("Pilot Manager selected\n");
                        done = true;
                        break;
                    case 5: //customer support
                        managerType[0] = true;
                        managerType[1] = false;
                        managerType[2] = false;
                        managerType[3] = false;
                        managerType[4] = false;
                        managerType[5] = true;
                        System.out.println("Customer support selected\n");
                        done = true;
                        break;
                    case 6: //exit
                        managerType[0] = false;
                        managerType[1] = false;
                        managerType[2] = false;
                        managerType[3] = false;
                        managerType[4] = false;
                        managerType[5] = false;
                        System.out.println("Close manager selected\n");
                        done = true;
                        break;
                    default:
                        System.out.println("Error: Please enter a valid number choice.");
                        done = false;
                        break;
                }
            } catch (InputMismatchException ex) {
                System.out.println("Error: Please enter a valid number choice.");
                done = false;
            }
            catch (IllegalStateException ex) {
                in = new Scanner(System.in);
                System.out.println("Error: Internal Error, scanner closed, "
                        + "reinitialized. \nPlease try again.");
                
                done = false;
            }
            if(!done){
                System.out.println("What action would you like to perform?");
                printOptions();
            }
        
        }
        return managerType;
    }
    
    private void printOptions(){
        System.out.println("1: Add/View/Remove customer reservations");
        System.out.println("2: Add/View/Remove legs");
        System.out.println("3: Add/View/Remove flights");
        System.out.println("4: Add/View/Remove pilots");
        System.out.println("5: Add/View/Remove planes");
        System.out.println("6: Add/View/Remove customer credit cards");
        System.out.println("7: Go back");
    }
    
    private void printManagerOptions(){
        System.out.println("1: Ticket Clerk"); //add customer reservations
        System.out.println("2: General Manager"); //add customer reservations, add flights/legs, add pilots, add planes
        System.out.println("3: Engineer"); //add planes
        System.out.println("4: Pilot Manager"); //add pilots, add planes, add legs
        System.out.println("5: Customer Support"); //add customer reservations, add credit cards
        System.out.println("6: Exit Manager Interface");
    }
    
    //implement these with the model of manageCreditCardInfo()
    
    //manage pilots
    private void managePilots(){
        boolean done = false;
        while(!done){
            System.out.println("What would you like to do?");
            System.out.println("1: Add a pilot to roster");
            System.out.println("2: View current pilots");
            System.out.println("3: Remove pilot from roster");
            System.out.println("4: Go back");
            System.out.print("-->");
            int userChoice;
            try{
                userChoice = in.nextInt();
                switch(userChoice){
                    case 1:
                        System.out.println("Add a pilot option...\n");
                        addPilot();
                        done = false;
                        break;
                    case 2:
                        System.out.println("View pilots option...\n");
                        viewPilots();
                        done = false;
                        break;
                    case 3:
                        System.out.println("Remove a pilot option...\n");
                        removePilot();
                        done = false;
                        break;
                    case 4:
                        System.out.println("Exiting pilot management...\n");
                        done = true;
                        break;
                    default:
                        System.out.println("Error: Please enter a valid number choice.");
                        in.nextLine();
                        done = false;
                        break;
                }
            } catch (InputMismatchException ex) {
                System.out.println("Error: Please enter a valid number choice.");
                
                done = false;
            }
            catch (IllegalStateException ex) {
                in = new Scanner(System.in);
                System.out.println("Error: Internal Error, scanner closed, "
                        + "reinitialized. \nPlease try again.");
                
                done = false;
            }
//            catch (SQLException ex){
//                System.out.println("General database Error");
//            }
            if(!done){
                System.out.println("What action would you like to perform?");
            }
        }
    }
    
    private void addPilot(){
        boolean valid = false;
        System.out.println("Please enter the pilot's first name.");
        System.out.print("-->");
        String fname = in.nextLine();
        System.out.println("Please enter the pilot's first name.");
        System.out.print("-->");
        String lname = in.nextLine();
        while(valid){
            try {
                String u;
                Random rnd = new Random();
                int randomID = rnd.nextInt(1000000);
                u = "insert into pilot values(" + randomID + ", '" + fname + "', '" + lname + "')";
                Statement stmt;
                
                stmt = con.createStatement();
                int result;
                result = stmt.executeUpdate(u);
                System.out.println( result + " pilot added: " + randomID + " - " + fname + " " + lname + "");
                valid = true;
                stmt.close();
                System.out.println("");
            }  catch (SQLException ex) {
                    valid = false;
            } 
        }
        
    }
    
    private void removePilot(){
        boolean valid = false;
        while(!valid){
            System.out.println("Please enter the ID of the pilot you wish to remove.");
            System.out.print("-->");
            int pilotID = in.nextInt();
            in.nextLine();
            try {
                String u;
                u = "delete from pilot where id = " + pilotID;
                Statement stmt;
                stmt = con.createStatement();
                int result;
                result = stmt.executeUpdate(u);
                System.out.println( result + " pilot removed");
                valid = true;
                stmt.close();
                System.out.println("");
            }  catch (SQLException ex) {
                    valid = false;
            } 
        }
    }
    
    private void viewPilots() {
        try{
            Statement viewStmt;
            viewStmt = con.createStatement();
            ResultSet viewResult = viewStmt.executeQuery("select * from pilot");
            ResultSetMetaData viewMetaData = viewResult.getMetaData();
            if(!viewResult.next()) {
                System.out.println("No pilots exist in the system.");
            }
            else {
                int numColumns = viewMetaData.getColumnCount();
                int width;
                System.out.printf("%-10s\t%-20s\t%-20s", "Pilot ID", "First Name", "Last Name");
                System.out.println("");
                for (int h = 1; h<=numColumns; h++){
                    width = viewMetaData.getPrecision(h);
                    for (int g = 1; g <= width; g++){
                        System.out.print("-");
                    }
                    System.out.print("\t");
                }
                System.out.println("");
                do{
                    for (int i = 1; i<=numColumns; i++){
                        width = viewMetaData.getPrecision(i);
                        System.out.printf("%-" + width + "s\t", viewResult.getString(i));
                    }
                    System.out.println("");
                } while (viewResult.next());

            }
            viewStmt.close();
        } catch (SQLException ex){
                System.out.println("General database Error");
        }
    }
    
    //manage planes
    private void managePlanes(){
        boolean done = false;
        while(!done){
            System.out.println("What would you like to do?");
            System.out.println("1: Add a plane to roster");
            System.out.println("2: View planes currently in service");
            System.out.println("3: Remove plane from roster");
            System.out.println("4: Go back");
            System.out.print("-->");
            int userChoice;
            try{
                userChoice = in.nextInt();
                switch(userChoice){
                    case 1:
                        System.out.println("Add a plane option...\n");
                        addPlane();
                        done = false;
                        break;
                    case 2:
                        System.out.println("View plane option...\n");
                        viewPlanes();
                        done = false;
                        break;
                    case 3:
                        System.out.println("Remove a plane option...\n");
                        removePlane();
                        done = false;
                        break;
                    case 4:
                        System.out.println("Exiting plane management...\n");
                        done = true;
                        break;
                    default:
                        System.out.println("Error: Please enter a valid number choice.");
                        in.nextLine();
                        done = false;
                        break;
                }
            } catch (InputMismatchException ex) {
                System.out.println("Error: Please enter a valid number choice.");
                
                done = false;
            }
            catch (IllegalStateException ex) {
                in = new Scanner(System.in);
                System.out.println("Error: Internal Error, scanner closed, "
                        + "reinitialized. \nPlease try again.");
                
                done = false;
            }
//            catch (SQLException ex){
//                System.out.println("General database Error");
//            }
            if(!done){
                System.out.println("What action would you like to perform?");
            }
        }
    }
    
    private void addPlane(){
        boolean valid = false;
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex1) {
            System.out.println("Database error. Exiting program");
            System.exit(1);
        }
        
        int planeType = 0;
        while(!valid){
            try {
                boolean typeValid = false;
                while(!typeValid){
                    System.out.print("-->");
                    planeType = in.nextInt();
                    switch(planeType){
                        case 747:
                            typeValid = true;
                            break;
                        case 737:
                            typeValid = true;
                            break;
                        case 787:
                            typeValid = true;
                            break;
                        case 350:
                            typeValid = true;
                            break;
                        case 380:
                            typeValid = true;
                            break;
                        default:
                            System.out.println("Please enter a valid plane type.");
                            typeValid = false;
                            break;
                    }
                }
                
                String u;
                Random rnd = new Random();
                int randomID = rnd.nextInt(1000000);
                int randomMiles = rnd.nextInt(1000000);
                u = "insert into plane values(" + randomID + ", (select (to_char"
                        + "(sysdate, 'DD-MON-YYYY')) from dual), '"
                        + randomMiles +"')";
                Statement stmt;
                
                stmt = con.createStatement();
                int result;
                result = stmt.executeUpdate(u);
                if (result > 0){
                    
                    String v = "insert into is_type values (" + randomID + ", " + planeType + ")";
                    Statement stmtIsType;
                    stmtIsType = con.createStatement();
                    int resultIsType = stmtIsType.executeUpdate(v);
                    if (resultIsType > 0){
                        System.out.println( result + " plane of type" + planeType + "added: " + randomID + "");
                        con.commit();
                        valid = true;
                    }
                    else {
                        con.rollback();
                        valid = false;
                    }
                    stmtIsType.close();
                }
                else{
                    valid = false;
                }
                stmt.close();
                
                System.out.println("");
            }  catch (SQLException ex) {
                    System.out.println("Please enter a valid plane type.");
                try {
                    con.rollback();
                } catch (SQLException ex1) {
                    System.out.println("Database error. Exiting program");
                    System.exit(1);
                }
                    valid = false;
            } 
        }
        try {
            con.setAutoCommit(true);
        } catch (SQLException ex1) {
            System.out.println("Database error. Exiting program");
            System.exit(1);
        }
    }
    
    private void viewPlanes(){
        try{
            Statement viewStmt;
            viewStmt = con.createStatement();
            ResultSet viewResult = viewStmt.executeQuery("select * from planes");
            ResultSetMetaData viewMetaData = viewResult.getMetaData();
            if(!viewResult.next()) {
                System.out.println("No planes exist in the system.");
            }
            else {
                int numColumns = viewMetaData.getColumnCount();
                int width;
                System.out.printf("%-6s\t%-19s\t%-8s", "Plane ID", "Date Put In Service", "Last Name");
                System.out.println("");
                for (int h = 1; h<=numColumns; h++){
                    width = viewMetaData.getPrecision(h);
                    for (int g = 1; g <= width; g++){
                        if (g==2){
                            System.out.println("----------");
                        }
                        System.out.print("-");
                    }
                    System.out.print("\t");
                }
                System.out.println("");
                do{
                    for (int i = 1; i<=numColumns; i++){
                        width = viewMetaData.getPrecision(i);
                        if (i==2){
                            width+=10;
                        }
                        System.out.printf("%-" + width + "s\t", viewResult.getString(i));
                    }
                    System.out.println("");
                } while (viewResult.next());

            }
            viewStmt.close();
        } catch (SQLException ex){
                System.out.println("General database Error");
        }
    }
    
    private void removePlane(){
        boolean valid = false;
        while(!valid){
            System.out.println("Please enter the ID of the plane you wish to remove.");
            System.out.print("-->");
            int planeID = in.nextInt();
            in.nextLine();
            try {
                String u;
                u = "delete from plane where id = " + planeID;
                Statement stmt;
                stmt = con.createStatement();
                int result;
                result = stmt.executeUpdate(u);
                System.out.println( result + " plane removed");
                valid = true;
                stmt.close();
                System.out.println("");
            }  catch (SQLException ex) {
                    valid = false;
            } 
        }
    }
    
    //manage flights
    private void manageFlights(){
        boolean done = false;
        while(!done){
            System.out.println("What would you like to do?");
            System.out.println("1: Add a flight");
            System.out.println("2: View flights");
            System.out.println("3: Remove a flight");
            System.out.println("4: Go back");
            System.out.print("-->");
            int userChoice;
            try{
                userChoice = in.nextInt();
                switch(userChoice){
                    case 1:
                        System.out.println("Add a flight option...\n");
                        addFlight();
                        done = false;
                        break;
                    case 2:
                        System.out.println("View flights option...\n");
                        viewFlights();
                        done = false;
                        break;
                    case 3:
                        System.out.println("Remove a flight option...\n");
                        removeFlight();
                        done = false;
                        break;
                    case 4:
                        System.out.println("Exiting flight management...\n");
                        done = true;
                        break;
                    default:
                        System.out.println("Error: Please enter a valid number choice.");
                        in.nextLine();
                        done = false;
                        break;
                }
            } catch (InputMismatchException ex) {
                System.out.println("Error: Please enter a valid number choice.");
                
                done = false;
            }
            catch (IllegalStateException ex) {
                in = new Scanner(System.in);
                System.out.println("Error: Internal Error, scanner closed, "
                        + "reinitialized. \nPlease try again.");
                
                done = false;
            }
//            catch (SQLException ex){
//                System.out.println("General database Error");
//            }
            if(!done){
                System.out.println("What action would you like to perform?");
            }
        }
    }
    
    private void addFlight(){
        boolean done = false;
        ArrayList<AbstractMap.SimpleEntry<Integer,String>> trips = new ArrayList();
        String promptAirportTo, promptAirportFrom;
        promptAirportTo = "Please indicate the starting airport.";
        promptAirportFrom = "Please indicate the destination airport.";
        Customer c = new Customer(con,in);
        //find trips
        while (!done){
            //pick airports
            System.out.println(promptAirportTo);
            
            String destination = c.pickAirportFromOptions();
            if("".equals(destination)){
                System.out.println("Airport not chosen. Please try again.");
                done = false;
            }

            System.out.println(promptAirportFrom);
            String source = c.pickAirportFromOptions();
            if("".equals(source)){
                System.out.println("Airport not chosen. Please try again.");
                done = false;
            }
            
            
            if(done){
                //enter price
                int price = 0;
                System.out.println("Please enter the desired price of the flight.");
                System.out.print("-->");
                boolean inputValid = false;
                while(!inputValid){
                    try{
                        price = in.nextInt();
                        in.nextLine();
                        inputValid = true;
                    } catch(InputMismatchException e){
                        System.out.println("Please enter a valid price.");
                        System.out.print("-->");
                        inputValid = false;
                    }
                }
               
                boolean randomValid = false;
                while(!randomValid){
                    try{
                        Random rnd = new Random();
                        int randomID = rnd.nextInt(1000000);
                        String q = "insert into trip values (" + randomID +", (select (to_char"
                            + "(sysdate, 'DD-MON-YYYY')) from dual), " + source 
                                + ", " + destination + ", " + price +")";
                        Statement stmtReservation = con.createStatement();
                        int resultReservation = stmtReservation.executeUpdate(q);
                        if (resultReservation > 1){
                            randomValid = true;
                        }
                        else {
                            randomValid = false;
                        }
                    } catch (SQLException | InputMismatchException e){
                        randomValid = false;
                    }
                }
                //select the legs to add
                
                
            }
            
        }
        
    }
    
    public void viewFlights(){
        boolean tripsFound = false;
        ArrayList<AbstractMap.SimpleEntry<Integer,String>> trips = new ArrayList();
        String promptAirportTo, promptAirportFrom;
        promptAirportTo = "Please indicate the starting airport.";
        promptAirportFrom = "Please indicate the destination airport.";
        Customer c = new Customer(con,in);
        //find trips
        while (!tripsFound){
            System.out.println(promptAirportTo);
            
            String destination = c.pickAirportFromOptions();
            if("".equals(destination)){
                System.out.println("Airport not chosen. Please try again.");
                try {
                    con.rollback();
                    con.setAutoCommit(true);
                } catch (SQLException ex) {
                    System.out.println("Database error enabling auto-commit. "
                            + "Check internet connection.");
                    System.exit(1);
                }
                return;
            }

            System.out.println(promptAirportFrom);
            String source = c.pickAirportFromOptions();
            if("".equals(source)){
                System.out.println("Airport not chosen. Please try again.");
                try {
                    con.rollback();
                    con.setAutoCommit(true);
                } catch (SQLException ex) {
                    System.out.println("Database error enabling auto-commit. "
                            + "Check internet connection.");
                    System.exit(1);
                }
                return;
            }
            
            trips = c.findTrips(source, destination);
            if (trips.isEmpty()){
                System.out.println("Trips not found.");
                try{
                    System.out.println("Would you like to search for a different"
                            + " trip or quit?");
                    System.out.println("1: Yes");
                    System.out.println("2: No");
                    boolean valid = false;
                    while(!valid){
                        System.out.print("-->");
                        int userChoice = in.nextInt();
                        in.nextLine();
                        switch(userChoice){
                            case 1:
                                valid = true;
                                break;
                            case 2:
                                try {
                                    con.rollback();
                                    con.setAutoCommit(true);
                                } catch (SQLException ex) {
                                    System.out.println("Database error enabling auto-commit. "
                                            + "Check internet connection.");
                                    System.exit(1);
                                }
                                return;
                            default:
                                System.out.println("Please enter a either 1 (yes) or 2 (no).");
                                valid = false;
                                break;
                        }
                    }
                } catch (InputMismatchException e){
                    System.out.println("Please enter a either 1 (yes) or 2 (no).");
                }
                tripsFound = false;
            }
            else {
                System.out.println("Trips found!");
                tripsFound = true;
            }
        }
        //found trips, listing them
        try{
            Statement listTrips = con.createStatement();
            ResultSet listTripsResult;
            int trip_num;
            String trip_date;
            for(int i = 1; i<=trips.size(); i++){
                trip_num = ((trips.get(i-1)).getKey()).intValue();
                trip_date = ((trips.get(i-1)).getValue());
                String listTripsQuery = "select price from "
                    + "trip where trip_number = " + trip_num 
                        + " and trip_date = '" + trip_date + "'";
                listTripsResult = listTrips.executeQuery(listTripsQuery);
                listTripsResult.next();
                System.out.println("Trip Option " + i);
                System.out.println("Trip Number:" + trip_num);
                System.out.println("Trip Date:" + trip_date);
                System.out.println("Price:" + listTripsResult.getInt(1));
                viewLegs(trip_num);
            }
        } catch (SQLException e){
            System.out.println("Error: Database error while listing trips "
                    + "found. Going back to main menu.");
            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                System.out.println("Database error enabling auto-commit. "
                        + "Check internet connection.");
                System.exit(1);
            }
        }
    }
    
    private void removeFlight(){
        
    }
    
    //manage legs
    private void manageLegs(){
        boolean done = false;
        while(!done){
            System.out.println("What would you like to do?");
            System.out.println("1: Add a leg");
            System.out.println("2: View leg");
            System.out.println("3: Remove a leg");
            System.out.println("4: Go back");
            System.out.print("-->");
            int userChoice;
            try{
                userChoice = in.nextInt();
                switch(userChoice){
                    case 1:
                        System.out.println("Add a leg option...\n");
                        addLeg();
                        done = false;
                        break;
                    case 2:
                        boolean tripValid = false;
                        System.out.println("Please enter the trip number you "
                                + "would like to view the legs of, or enter 0 "
                                + "to view all legs in the database.");
                        int tripNumber = 0;
                        while (!tripValid){
                            try{
                                System.out.print("-->");
                                tripNumber = in.nextInt();
                                tripValid = true;
                            } catch (InputMismatchException e){
                                System.out.println("Please enter a valid number choice.");
                                tripValid = false;
                            }catch (IllegalStateException ex) {
                                in = new Scanner(System.in);
                                System.out.println("Error: Internal Error, scanner closed, "
                                        + "reinitialized. \nPlease try again.");

                                tripValid = false;
                            }
                        }
                        System.out.println("View legs option with flight number " 
                                + tripNumber + "...\n");
                        viewLegs(tripNumber);
                        done = false;
                        break;
                    case 3:
                        System.out.println("Remove a leg option...\n");
                        removeLeg();
                        done = false;
                        break;
                    case 4:
                        System.out.println("Exiting leg management...\n");
                        done = true;
                        break;
                    default:
                        System.out.println("Error: Please enter a valid number choice.");
                        in.nextLine();
                        done = false;
                        break;
                }
            } catch (InputMismatchException ex) {
                System.out.println("Error: Please enter a valid number choice.");
                
                done = false;
            }
            catch (IllegalStateException ex) {
                in = new Scanner(System.in);
                System.out.println("Error: Internal Error, scanner closed, "
                        + "reinitialized. \nPlease try again.");
                
                done = false;
            }
//            catch (SQLException ex){
//                System.out.println("General database Error");
//            }
            if(!done){
                System.out.println("What action would you like to perform?");
            }
        }
    }
    
    private void addLeg(){
        
    }
    
    private void viewLegs(int tripNumber){ 
        in.nextLine();
        String q;
        if (tripNumber == 0){
            q = "select l.leg_id as LegID, plane_id as PlaneID, pilot_id as PilotID, "
                    + "F.airport as StartingPoint,"
                    + "to_char(time_departure, 'DD-MON-YYYY') as Time_Departure, "
                    + "T.airport as Destination,"
                    + "to_char(time_arrival, 'DD-MON-YYYY') as TimeArrival, "
                    + "price from leg L join leg_to T on t.leg_id = l.leg_id "
                    + "join leg_from F on f.leg_id = l.leg_id;";
        }
        else {
            q = "select l.leg_id as LegID, plane_id as PlaneID, pilot_id as PilotID, "
                    + "F.airport as StartingPoint, "
                    + "to_char(time_departure, 'DD-MON-YYYY') as Time_Departure, "
                    + "T.airport as Destination, "
                    + "to_char(time_arrival, 'DD-MON-YYYY') as TimeArrival, price "
                    + "from leg L join leg_to T on t.leg_id = l.leg_id "
                    + "join leg_from F on f.leg_id = l.leg_id "
                    + "where exists "
                    + "(select leg_id "
                    + "from leg_of_trip "
                    + "where trip_number = " + tripNumber
                    + "and leg_id = l.leg_id);";
        }
        Statement stmt;
        try {
            stmt = con.createStatement();
            ResultSet result;
            result = stmt.executeQuery(q);
            ResultSetMetaData metadata = result.getMetaData();
            if(!result.next()) {
                System.out.println("No legs to display.");
            }
            else {
                int numColumns = metadata.getColumnCount();
                int width;
                for (int j = 1; j<=numColumns; j++){
                    width = metadata.getPrecision(j);
                    System.out.printf("%-" + width + "s\t", metadata.getColumnName(j));
                }
                System.out.println("");
                for (int h = 1; h<=numColumns; h++){
                    width = metadata.getPrecision(h);
                    for (int g = 1; g <= width; g++){
                        System.out.print("-");
                    }
                    System.out.print("\t");
                }
                System.out.println("");
                do{
                    for (int i = 1; i<=numColumns; i++){
                        width = metadata.getPrecision(i);
                        System.out.printf("%-" + width + "s\t", result.getString(i));
                    }
                    System.out.println("");
                } while (result.next());
                
            }
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Error: That trip does not exist.");
        }
        System.out.println("");
    }
    
    private void removeLeg(){
        
    }
}


package conleyairlines;
import java.sql.*;
import java.util.*;
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
        for(int i = 0; i<6 && quit; i++){
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
                                
                                manageReservations();
                                done = false;
                                break;
                            case 2:
                                System.out.println("Add/View/Remove legs chosen\n");
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
    
    private void manageReservations(){
        boolean done = false;
        while(!done){
            System.out.println("What would you like to do?");
            System.out.println("1: Add a customer resrevation");
            System.out.println("2: View customer reservations");
            System.out.println("3: Cancel customer reservation");
            System.out.println("4: Go back");
            System.out.print("-->");
            int userChoice;
            try{
                userChoice = in.nextInt();
                in.nextLine();
                int customerID;
                switch(userChoice){
                    case 1:
                        System.out.println("Add a reservation option...\n");
                        System.out.println("Please enter the customer's ID number.");
                        customerID = in.nextInt();
                        Customer cAdd = new Customer(con, in);
                        cAdd.makeAReservation(customerID, true);
                        done = false;
                        break;
                    case 2:
                        System.out.println("View reservations option...\n");
                        System.out.println("Please enter the customer's ID number.");
                        customerID = in.nextInt();
                        Customer cView = new Customer(con, in);
                        cView.viewReservedFlights(customerID);
                        done = false;
                        break;
                    case 3:
                        System.out.println("Remove a reservation option...\n");
                        System.out.println("Please enter the customer's ID number.");
                        customerID = in.nextInt();
                        Customer cCancel = new Customer(con, in);
                        cCancel.cancelReservedFlight(customerID);
                        done = false;
                        break;
                    case 4:
                        System.out.println("Exiting reservation management...\n");
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
        System.out.print("-->");
    }
    
    private void printManagerOptions(){
        System.out.println("1: Ticket Clerk"); //add customer reservations
        System.out.println("2: General Manager"); //add customer reservations, add flights/legs, add pilots, add planes
        System.out.println("3: Engineer"); //add planes
        System.out.println("4: Pilot Manager"); //add pilots, add planes, add legs
        System.out.println("5: Customer Support"); //add customer reservations, add credit cards
        System.out.println("6: Exit Manager Interface");
        System.out.print("-->");
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
                        boolean tripValid = false;
                        System.out.println("Please enter the trip number you "
                                + "would like to remove.");
                        int tripNumber = 0;
                        String tripDate = "";
                        while (!tripValid){
                            try{
                                System.out.print("-->");
                                tripNumber = in.nextInt();
                                in.nextLine();
                                tripValid = true;
                            } catch (InputMismatchException e){
                                System.out.println("Please enter a valid trip number, or 0 to go back.");
                                in.nextLine();
                                tripValid = false;
                            }catch (IllegalStateException ex) {
                                in = new Scanner(System.in);
                                System.out.println("Error: Internal Error, scanner closed, "
                                        + "reinitialized. \nPlease try again.");
                                tripValid = false;
                            }
                        }
                        boolean dateValid = false;
                        System.out.println("Please specify the date of this trip (ex. 01-JAN-2013).");
                        while (!dateValid){
                            try{
                                System.out.print("-->");
                                tripDate = in.nextLine();
                                dateValid = true;
                            } catch (InputMismatchException e){
                                System.out.println("Please enter a valid trip date, or 0 to go back.");
                                dateValid = false;
                            }catch (IllegalStateException ex) {
                                in = new Scanner(System.in);
                                System.out.println("Error: Internal Error, scanner closed, "
                                        + "reinitialized. \nPlease try again.");
                                dateValid = false;
                            }
                        }
                        removeFlight(tripNumber, tripDate);
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
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            System.out.println("Database Error, check internet connection.");
            System.exit(1);
        }
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
                int randomID = 0;
                String currentTime = "";
                while(!randomValid){
                    try{
                        Random rnd = new Random();
                        randomID = rnd.nextInt(1000000);
                        String time = "select (to_char"
                            + "(sysdate, 'DD-MON-YYYY')) from dual";
                        Statement stmtTime = con.createStatement();
                        ResultSet timeResult = stmtTime.executeQuery(time);
                        currentTime = timeResult.getString(1);
                        String q = "insert into trip values (" + randomID +", '" + currentTime +  "', '" + source 
                                + "', '" + destination + "', " + price +")";
                        Statement stmtReservation = con.createStatement();
                        int resultReservation = stmtReservation.executeUpdate(q);
                        if (resultReservation > 1){
                            randomValid = true;
                        }
                        else {
                            randomValid = false;
                        }
                    } catch (SQLException e){
                        randomValid = false;
                    }
                }
                //obtain the date just inserted
                String tripDate = "";
                
                //select the legs to add
                System.out.println("Please select the leg IDs you wish to add to this flight. Type 0 to finish.");
                boolean selectAnother = true;
                viewLegs(0);
                while (selectAnother){
                    int legID = in.nextInt();
                    in.nextLine();
                    if(legID == 0){
                        selectAnother = false;
                        try {
                            con.commit();
                        } catch (SQLException ex) {
                            System.out.println("Database Error, check internet connection.");
                            System.exit(1);
                        }
                    }
                    else{
                        try {
                            //add leg to leg_of_trip
                            String insert = "insert into leg_of_trip values (" + randomID + ", '" + currentTime + ", " + legID + ")";
                            Statement stmtLegOfTrip = con.createStatement();
                            int resultLegOfTrip = stmtLegOfTrip.executeUpdate(insert);
                            if(resultLegOfTrip > 0){
                                System.out.println("Leg " + legID +"successfully added as part of trip" + randomID);
                            }
                            else {
                                System.out.println("Leg not added to trip.");
                            }
                            selectAnother = true;
                        } catch(SQLException e){
                            System.out.println("Leg ID invalid. Please select another.");
                            selectAnother = true;
                        }
                    }
                }
            }
            
        }
        try {
            con.setAutoCommit(true);
        } catch (SQLException ex) {
            System.out.println("Database Error, check internet connection.");
            System.exit(1);
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
    
    private void removeFlight(int tripNumber, String tripDate){
        if(tripNumber > 0){
            if(!"0".equals(tripDate)){
                //if trip exists
                
                try {
                    con.setAutoCommit(false);
                    Statement stmtCheck;
                    stmtCheck = con.createStatement();
                    String q = "select * from trip where trip_number = " + tripNumber + "";
                    ResultSet resultTest = stmtCheck.executeQuery(q);
                    if(resultTest.next()){
                        String d = "delete from leg_of_trip where trip_number = " 
                                + tripNumber + " and trip_date = '"+ tripDate + "'";
                        Statement stmtLegs;
                        stmtLegs = con.createStatement();
                        int resultLegs = stmtLegs.executeUpdate(d);
                        if (resultLegs > 0){
                            String t = "delete from trip where trip_number = " 
                                + tripNumber + " and trip_date = '"+ tripDate + "'";
                            Statement stmtTrip;
                            stmtTrip = con.createStatement();
                            int resultTrip = stmtTrip.executeUpdate(t);
                            if (resultTrip == 1){
                                System.out.println("Flight" + tripNumber +" successfully removed.");
                                con.commit();
                            }
                            else {
                                System.out.println("Error: Flight not deleted.");
                                con.rollback();
                            }
                            stmtTrip.close();
                        }
                        else{
                            System.out.println("Error: Flight not deleted.");
                            con.rollback();
                        }
                        stmtLegs.close();
                    }
                    else{
                        System.out.println("Error: Flight not deleted.");
                        con.rollback();
                    }
                    stmtCheck.close();
                } catch (SQLException e){
                    System.out.println("Error: Database error. Going back to main menu");
                    try {
                        con.rollback();
                        con.setAutoCommit(true);
                    } catch (SQLException ex) {
                        System.out.println("Error: Database connection error. Please check your internet connection.");
                        System.exit(1);
                    }
                    
                }
                try {
                    con.setAutoCommit(true);
                        //delete from trip
                        //delete from trip
                } catch (SQLException ex) {
                    System.out.println("Error: Database connection error. Please check your internet connection.");
                    System.exit(1);
                }
            }
        }
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
                        boolean legValid = false;
                        System.out.println("Please enter the leg number of the "
                                + "leg you wish you remove, or enter 0 to go back.");
                        int legID = 0;
                        while (!legValid){
                            try{
                                System.out.print("-->");
                                legID = in.nextInt();
                                legValid = true;
                            } catch (InputMismatchException e){
                                System.out.println("Please enter a valid number choice.");
                                legValid = false;
                            } catch (IllegalStateException ex) {
                                in = new Scanner(System.in);
                                System.out.println("Error: Internal Error, scanner closed, "
                                        + "reinitialized. \nPlease try again.");
                                legValid = false;
                            }
                        }
                        removeLeg(legID);
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
        //find plane
        //find pilot
        //randomly generate leg_id
        //input start airport
        //input end airport
        //insert into leg, leg_to, leg_from
        boolean cont = true;
        String source, destination;
        Customer c = new Customer(con, in);
        System.out.println("Please select the airport you wish to use as the starting point.");
        source = c.pickAirportFromOptions();
        System.out.println("Please select the airport you wish to use as the destination.");
        destination = c.pickAirportFromOptions();
        int price = 0;
        System.out.println("Please enter the desired price of the leg.");
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
        int planeID = 0, pilotID = 0;
        boolean valid = false;
        try { 
            con.setAutoCommit(false);
        } catch (SQLException e){
            System.out.println("Database Error. Check your internet connection.");
            System.exit(1);
        }
        while (!valid){
            try {
                String planeCheck = "select * from plane P where not exists "
                        + "(select * from leg_id where plane_id = P.plane_id)";
                Statement stmtPlaneCheck;
                stmtPlaneCheck = con.createStatement();
                ResultSet resultPlaneCheck = stmtPlaneCheck.executeQuery(planeCheck);
                if (resultPlaneCheck.next()){
                    String pilotCheck = "select * from pilot P where not exists "
                        + "(select * from leg_id where pilot_id = P.pilot_id)";
                    Statement stmtPilotCheck;
                    stmtPilotCheck = con.createStatement();
                    ResultSet resultPilotCheck = stmtPilotCheck.executeQuery(pilotCheck);
                    if (resultPilotCheck.next()){
                        valid = true;
                    }
                    else {
                        valid = false;
                    }
                    stmtPilotCheck.close();
                }
                else {
                    valid = false;
                }
                stmtPlaneCheck.close();
            } catch (SQLException e){
                System.out.println("Error selecting next available plane and pilot.");
                valid = true;
                cont = false;
            }
        }
        if (cont){
            boolean randomValid = false;
            int randomID = 0;
            while(!randomValid){
                try{
                    Random rnd = new Random();
                    randomID = rnd.nextInt(1000000);
                    String q = "insert into leg values (" + randomID + ", "
                            + planeID + ", " + pilotID + ", " 
                            + "to_char(sysdate, 'DD-MON-YYYY'), "
                            + "to_char(sysdate, 'DD-MON-YYYY'), "
                            + price + ")";
                    Statement stmtLegInsert;
                    stmtLegInsert = con.createStatement();
                    int resultLegInsert = stmtLegInsert.executeUpdate(q);
                    if (resultLegInsert > 0){
                        randomValid = true;
                    }
                    else {
                        randomValid = false;
                    }
                    stmtLegInsert.close();
                } catch (SQLException e){
                    
                    randomValid = false;
                }
            }
            try {
                String to = "insert into leg_to values (" + randomID + ", " + destination + ")";
                Statement stmtTo;
                stmtTo = con.createStatement();
                int resultTo = stmtTo.executeUpdate(to);
                if (resultTo > 0){
                    String from = "insert into leg_from values (" + randomID + ", " + destination + ")";
                    Statement stmtFrom;
                    stmtFrom = con.createStatement();
                    int resultFrom = stmtFrom.executeUpdate(from);
                    if (resultFrom > 0){
                        System.out.println("Leg successfully added.");
                        System.out.println("Database Error. Leg not added.");
                        try {
                            con.commit();
                        } catch (SQLException ex) {
                            System.out.println("Database Error. Please check your internet connection.");
                            System.exit(1);
                        }
                    }
                    else {
                        System.out.println("Database Error. Leg not added.");
                        try {
                            con.rollback();
                        } catch (SQLException ex) {
                            System.out.println("Database Error. Please check your internet connection.");
                            System.exit(1);
                        }
                    }
                    stmtFrom.close();
                }
                else {
                    System.out.println("Database Error. Leg not added.");
                    try {
                        con.rollback();
                    } catch (SQLException ex) {
                        System.out.println("Database Error. Please check your internet connection.");
                        System.exit(1);
                    }
                }
                stmtTo.close();
            } catch (SQLException e){
                System.out.println("Database Error. Leg not added.");
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    System.out.println("Database Error. Please check your internet connection.");
                    System.exit(1);
                }
            }
            
        }
        try {
            con.setAutoCommit(true);
        } catch (SQLException ex) {
            System.out.println("Database Error. Please check your internet connection.");
            System.exit(1);
        }
        
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
    
    private void removeLeg(int legID){
        //select * from leg
        //input legID
        //if (result.next()) on (select * from leg)
            //delete from leg_of_trip where leg_id = legID
            //delete from leg_to
            //delete from leg_from
            //delete from leg where leg_id = legID
        //else redo input legID
        if(legID > 0){
            
            //if leg exists
                    
            try {
                con.setAutoCommit(false);
                Statement stmtCheck;
                stmtCheck = con.createStatement();
                String q = "select * from leg where leg_id = " + legID + "";
                ResultSet resultTest = stmtCheck.executeQuery(q);
                if(resultTest.next()){
                    String d = "delete from leg_of_trip where leg_id = " 
                            + legID;
                    Statement stmtLegOfTrip;
                    stmtLegOfTrip = con.createStatement();
                    int resultLegOfTrip = stmtLegOfTrip.executeUpdate(d);
                    if (resultLegOfTrip > 0){
                        String t = "delete from leg_to where leg_id = " 
                            + legID;
                        Statement stmtLegTo;
                        stmtLegTo = con.createStatement();
                        int resultLegTo = stmtLegTo.executeUpdate(t);
                        if (resultLegTo == 1){
                            Statement stmtLegFrom;
                            stmtLegFrom = con.createStatement();
                            String f = "delete from leg_from where leg_id = " + legID;
                            int resultLegFrom = stmtLegFrom.executeUpdate(f);
                            if (resultLegFrom == 1){
                                String l = "delete from leg where leg_id = " + legID;
                                Statement stmtLeg;
                                stmtLeg = con.createStatement();
                                int resultLeg = stmtLeg.executeUpdate(l);
                                if (resultLeg == 1){
                                    System.out.println("Leg " + legID + " successfully removed.");
                                    con.commit();
                                }
                                else {
                                    System.out.println("Error: Flight not deleted.");
                                con.rollback();
                                }
                                stmtLeg.close();
                            }
                            
                            else {
                                System.out.println("Error: Flight not deleted.");
                                con.rollback();
                            }
                            stmtLegFrom.close();
                        }
                        else {
                            System.out.println("Error: Flight not deleted.");
                            con.rollback();
                        }
                        stmtLegTo.close();
                    }
                    else{
                        System.out.println("Error: Flight not deleted.");
                        con.rollback();
                    }
                    stmtLegOfTrip.close();
                }
                else{
                    System.out.println("Error: Flight not deleted.");
                    con.rollback();
                }
                stmtCheck.close();
            } catch (SQLException e){
                System.out.println("Error: Database error. Going back to main menu");
                try {
                    con.rollback();
                    con.setAutoCommit(true);
                } catch (SQLException ex) {
                    System.out.println("Error: Database connection error. Please check your internet connection.");
                    System.exit(1);
                }

            }
            try {
                con.setAutoCommit(true);
                    //delete from trip
                    //delete from trip
            } catch (SQLException ex) {
                System.out.println("Error: Database connection error. Please check your internet connection.");
                System.exit(1);
            }
        }
        
    }
}

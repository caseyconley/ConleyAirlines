
package conleyairlines;

import java.sql.*;
import java.util.*;
/**
 *
 * @author Casey Conley
 * Email: cdc214@lehigh.edu
 * Course: CSE 241
 * Title: Airline Semester Project
 * Purpose: A user interface for a fictional airline company. Uses JDBC with a
 *          database housing the information for the airline.
 * 
 */
public class ConleyAirlines {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //connect to Edgar2 with JDBC v6
        Connection con = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
            //THIS SHOULD NEVER HAPPEN BUUUUUUT....
            System.out.println("Error: Internal error, driver not found, please "
                    + "include the JDBC driver and restart the Conley Airlines "
                    + "interface.");
            System.exit(1);
        }
        try {
            con = DriverManager.getConnection
                ("jdbc:oracle:thin:@edgar2.cse.lehigh.edu:1521:cse241","cdc214",
                 "P884028629");
        } catch (SQLException ex) {
            System.out.println("Error: Internal error, Could not connect to "
                    + "Edgar2. Check internet connection and try again.");
            System.exit(1);
        }
        
        //prepare for user input
        Scanner in = new Scanner(System.in);
        
        
        System.out.println("Hello! Welcome to Conley Airlines User Interface.");
        System.out.println("Please tell us which interface you would like to access.");
        System.out.println("Enter the number of the option below.");
        printOptions();
        while(true){
            boolean validInput = false;
            while(!validInput){
                int userChoice;
                try{
                    userChoice = in.nextInt();
                    switch(userChoice){
                        case 1:
                            //Database Administrator interface open
                            System.out.println("Initializing Admin interface...\n");
                            DatabaseAdmin d = new DatabaseAdmin(con, in);
                            d.openInterface();
                            validInput = true;
                            break;
                        case 2:
                            //Manager interface open
                            System.out.println("Initializing Manager interface...\n");
                            validInput = true;
                            break;
                        case 3:
                            //Customer interface open
                            System.out.println("Initializing Customer interface...\n");
                            Customer c = new Customer(con, in);
                            c.openInterface();
                            validInput = true;
                            break;
                        case 4:
                            //close the program
                            System.out.println("Thank you for choosing Conley Airlines.\n");
                            validInput = true;
                            in.close();
                            try {
                                con.close();
                                System.exit(0);
                            } catch (SQLException ex) {
                                System.out.println("Error: Database error. "
                                        + "Oh well, you were exiting anyway.\n");
                                System.exit(1);
                            }
                            break;
                        default:
                            System.out.println("Error: Please enter a valid number choice.");
                            printOptions();
                            validInput = false;
                            break;
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("Error: Please enter a valid number choice.");
                    printOptions();
                    validInput = false;
                } catch (IllegalStateException ex) {
                    in = new Scanner(System.in);
                    System.out.println("Error: Internal Error, scanner closed, "
                            + "reinitialized. \nPlease try again.");
                    System.out.print("-->");
                    validInput = false;
                }
                System.out.println("What would you like to do?");
                printOptions();
                in.nextLine();
            }
        }
    }
    
    public static void printOptions(){
        System.out.println("1: Database Administrator");
        System.out.println("2: Manager");
        System.out.println("3: Customer");
        System.out.println("4: Exit the program");
        System.out.print("-->");
    }
}

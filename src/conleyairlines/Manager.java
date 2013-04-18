
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
        System.out.println("What action would you like to perform?");
        printOptions();
        boolean done = false;
        while(!done){
            int userChoice;
            try{
                userChoice = in.nextInt();
                switch(userChoice){
                    case 1:
                        System.out.println("Option 1 chosen\n");
                        done = false;
                        break;
                    case 2:
                        System.out.println("Option 2 chosen\n");
                        in.nextLine();
                        done = false;
                        break;
                    case 3:
                        System.out.println("Option 3 chosen\n");
                        done = false;
                        break;
                    case 4:
                        System.out.println("Option 2 chosen\n");
                        done = false;
                        break;
                    case 5:
                        System.out.println("Exiting Customer Interface.\n");
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
            if(!done){
                System.out.println("What action would you like to perform?");
                printOptions();
            }
        }
        
    }
    
    private void printOptions(){
        System.out.println("1: ");
        System.out.println("2: ");
        System.out.println("3: ");
        System.out.println("4: ");
        System.out.println("5: ");
    }
}

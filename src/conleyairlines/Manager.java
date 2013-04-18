
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
        System.out.println("What action would you like to perform?");
        printOptions();
        boolean done = false;
        while(!done){
            int userChoice;
            try{
                userChoice = in.nextInt();
                in.nextLine();
                if (managerType[userChoice-1] == false){
                    System.out.println("Access Denied: Insufficient "
                            + "priveleges to access this interface.");
                    done = false;
                }
                else{
                    switch(userChoice){
                        case 1:
                            System.out.println("Add/View/Remove customer reservations chosen\n");
                            System.out.println("Please enter the customer's ID number.");
                            in.nextLine();
                            int customerID = in.nextInt();
                            Customer c = new Customer(con, in);
                            c.manageCreditCardInfo(customerID, true);
                            done = false;
                            break;
                        case 2:
                            System.out.println("Add/View/Remove legs chosen\n");
                            in.nextLine();
                            done = false;
                            break;
                        case 3:
                            System.out.println("Add/View/Remove flights\n");
                            done = false;
                            break;
                        case 4:
                            System.out.println("Add/View/Remove pilots chosen\n");
                            done = false;
                            break;
                        case 5:
                            System.out.println("Add/View/Remove planes chosen\n");
                            done = false;
                            break;
                        case 6:
                            System.out.println("Add/View/Remove customer credit cards chosen\n");
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
}

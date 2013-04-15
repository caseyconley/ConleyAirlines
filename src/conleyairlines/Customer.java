/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conleyairlines;
import java.sql.*;
import java.util.*;
/**
 *
 * @author Casey
 */
public class Customer {
    Connection con;
    Scanner in;
    
    public Customer(Connection con, Scanner in){
        this.con = con;
        this.in = in;
    }
    
    public void openInterface(){
        int customerID = logIn();
        if (customerID < 0){
            //DON'T HANDLE IT LIKE THIS, FIX LATER
            System.out.println("Exiting Customer Interface.");
            return;
        }
        System.out.println("What action would you like to perform?");
        printOptions();
        boolean done = false;
        while(!done){
            int userChoice;
            try{
                userChoice = in.nextInt();
                switch(userChoice){
                    case 1:
                        System.out.println("Manage credit card info option chosen\n");
                        done = false;
                        break;
                    case 2:
                        System.out.println("View reserved flights option chosen\n");
                        done = false;
                        break;
                    case 3:
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
            }
        }
        //done == true
        //break out of customer interface
    }
    
    private int logIn(){
        System.out.println("Welcome to Conley Airlines! Are you a returning customer?");
        
        boolean done = false;
        int customerID = -1;
        String fname, lname;
        while(!done){
            System.out.println("1: Yes, I have a customer ID.");
            System.out.println("2: No, I would like to create an account."); 
            System.out.println("3: I would like to close the Customer Interface.");
            System.out.print("-->");
            try{
                int userChoice = in.nextInt();
                
                
                switch(userChoice){
                    case 1:
                        System.out.println("Please enter your customer ID below.");
                        System.out.print("-->");
                        in.nextLine();
                        customerID = in.nextInt();
                        in.nextLine();
                        //validate customerID
                        try{
                            Statement idValidation = con.createStatement();
                            ResultSet validationResult;
                            validationResult = idValidation.executeQuery("select * from customer where id = " + customerID);
                            if (!validationResult.next()){
                                //id does not exist in database
                                System.out.println("That id does not exist in our database. "
                                        + "Please try again or create a new account.");
                                System.out.println("Are you a returning customer?");
                                done = false;
                            }
                            else {
                                fname = validationResult.getString(2);
                                lname = validationResult.getString(3);
                                done = true;
                                System.out.println("Welcome back, " + fname + " " + lname+ "!");
                            }
                        } catch (SQLException e){
                            System.out.println("Error: Database error, please try again.");
                        }
                        break;
                    case 2:
                        System.out.println("Please enter your first name.");
                        System.out.print("-->");
                        fname = in.nextLine();
                        System.out.println("Please enter your last name.");
                        System.out.print("-->");
                        lname = in.nextLine();
                        boolean valid = false;
                        while (!valid){
                            Random rnd = new Random();
                            customerID = 100000 + rnd.nextInt(900000);
                            try{
                                Statement stmt;
                                stmt = con.createStatement();
                                int result = stmt.executeUpdate("insert into customer values (" 
                                        + customerID + ", '" + fname + "', '" + lname + "')");
                                System.out.println(result + " customer added"); //comment this out in final submission
                                stmt.close();
                                valid = true;
                            } catch (SQLException e){
                                valid = false; //random number generator failed to create a unique customer ID number
                            }
                        }
                        System.out.println("Congratulations! Your account has been created. "
                                + "Your customer ID is " + customerID);
                        System.out.println("Use your customer ID to log into this interface "
                                + "in subsequent visits.");
                        done = true;
                        break;
                    case 3:
                        //get me out of here
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
            } catch (IllegalStateException ex) {
                    in = new Scanner(System.in);
                    System.out.println("Error: Internal Error, scanner closed, "
                            + "reinitialized. \nPlease try again.");
                    System.out.print("-->");
                    done = false;
            }
        }
        return customerID;
    }
    
    private void printOptions(){
        System.out.println("1: Manage Credit Card Information");
        System.out.println("2: View Reserved Flights");
        System.out.println("3: Close Customer Interface");
        System.out.print("-->");
    }
}

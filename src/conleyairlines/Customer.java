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
                        manageCreditCardInfo(customerID);
                        done = false;
                        break;
                    case 2:
                        System.out.println("Make a reservation option chosen\n");
                        makeAReservation(customerID);
                        done = false;
                        break;
                    case 3:
                        System.out.println("View reserved flights option chosen\n");
                        viewReservedFlights(customerID);
                        done = false;
                        break;
                    case 4:
                        System.out.println("Cancel reserved flights option chosen\n");
                        cancelReservedFlight(customerID);
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
                        in.nextLine();
                        fname = in.next();
                        System.out.println("Please enter your last name.");
                        System.out.print("-->");
                        lname = in.next();
                        in.nextLine();
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
    
    private void manageCreditCardInfo(int customerID){
        
        boolean done = false;
        while(!done){
            System.out.println("What would you like to do?");
            System.out.println("1: Add a credit card");
            System.out.println("2: View my credit card info");
            System.out.println("3: Delete a credit card");
            System.out.println("4: Go back");
            System.out.print("-->");
            int userChoice;
            try{
                userChoice = in.nextInt();
                switch(userChoice){
                    case 1:
                        System.out.println("Calling createCreditCard()...\n");
                        addCreditCard(customerID);
                        done = false;
                        break;
                    case 2:
                        System.out.println("Calling viewCreditCards()...\n");
                        viewCreditCards(customerID);
                        done = false;
                        break;
                    case 3:
                        System.out.println("Calling deleteCreditCard()...\n");
                        deleteCreditCard(customerID);
                        done = false;
                        break;
                    case 4:
                        System.out.println("Exiting credit card management.\n");
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
    }
    
    private void addCreditCard(int customerID){
        try{
            System.out.print("Please enter your 16-digit credit card number.\n-->");
            long card_num = in.nextLong();
            in.nextLine();
            System.out.print("Please enter your 3-digit security number.\n-->");
            int sec_code = in.nextInt();
            in.nextLine();
            System.out.print("Please enter the expiration date in the format MMYY "
                    + "(example if it is June 2013, enter 0613.\n-->");
            int exp_date = in.nextInt();
            in.nextLine();
            System.out.print("Please enter your street address (example: 1600 "
                    + "Pennsylvania Avenue).\n-->");
            String address = in.nextLine();
            System.out.print("Please enter your city.\n-->");
            String city = in.nextLine();
            System.out.print("Please enter your 2-letter state (example: "
                    + "CA, TX, NC, etc.).\n-->");
            String state = in.nextLine();
            System.out.print("Please enter your 5-digit zip code.\n-->");
            int zip = in.nextInt();
            in.nextLine();
            String insertCreditCard = "insert into credit_card values (" + card_num + ", " 
                    + sec_code + ", " + exp_date + ", '"+ address + "', '" + city + "', '" + state + "', " + zip + ")";
            Statement stmtCreditCard;
            stmtCreditCard = con.createStatement();
            int resultCreditCard;
            resultCreditCard = stmtCreditCard.executeUpdate(insertCreditCard);
            if (resultCreditCard == 1){
                //insert into credit_card successful, now insert into billing_info
                String insertBillingInfo = "insert into billing_info values (" 
                        + card_num + ", " + customerID + ")";
                Statement stmtBillingInfo;
                stmtBillingInfo = con.createStatement();
                int resultBillingInfo;
                resultBillingInfo = stmtBillingInfo.executeUpdate(insertBillingInfo);
                if(resultBillingInfo == 1){
                    System.out.println("Credit card successfully added to your account.");
                }
                else {
                    System.out.println("A credit card with that card number already exists.");
                }
                stmtBillingInfo.close();
            }
            else {
                System.out.println("A credit card with that card number already exists.");
            }
            stmtCreditCard.close();
            
        } catch (InputMismatchException ex) {
                System.out.println("Error: Invalid input, please try again.");
        } catch (IllegalStateException ex) {
            in = new Scanner(System.in);
            System.out.println("Error: Internal Error, scanner closed, "
                    + "reinitialized. \nPlease try again.");
        } catch (SQLException ex){
            System.out.println("Please follow the correct format the fields and try again.");
        }
    }
    
    private boolean hasCreditCards(int customerID){
        String q = "select card_num from billing_info where customer_id = " 
                + customerID + "";
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(q);
            if (result.next()){
                stmt.close();
                return true;
            }
            else{
                stmt.close();
                return false;
            }
            
        } catch (SQLException ex){
            System.out.println("Database Error, delete aborted.");
            return false;
        }
    }
    
    private void deleteCreditCard(int customerID){
        System.out.println("Listing all of your credit cards on file...");
        if (!hasCreditCards(customerID)){
            System.out.println("You have no credit cards on file. Please add one"
                    + " before trying to delete one.");
        }
        else{
            ArrayList<Long> cards = viewCreditCards(customerID);
            System.out.println("Please enter the number of the credit card you would like to remove.");
            
            boolean valid = false;
            int userChoice;
            while(!valid){
                System.out.print("-->");
                userChoice = in.nextInt();
                in.nextLine();
                long card_num = -1;
                try {
                    card_num = (cards.get(userChoice-1)).longValue();
                    con.setAutoCommit(false);
                    Statement stmtBilling;
                    stmtBilling = con.createStatement();
                    
                    String deleteBilling = "delete from billing_info where card_num = " 
                                + card_num + " and customer_id = " + customerID;
                    int resultBilling = stmtBilling.executeUpdate(deleteBilling);
                    if (resultBilling == 1){
                        //delete from credit_card successful, now delete from billing_info
                        String deleteCredit = "delete from credit_card where card_num = " + card_num + "";
                        Statement stmtCredit;
                        stmtCredit = con.createStatement();
                        int resultCredit;
                        resultCredit = stmtCredit.executeUpdate(deleteCredit);
                        if(resultCredit == 1){
                            System.out.println("Credit card successfully deleted from your account.");
                            valid = true;
                            con.commit();
                            con.setAutoCommit(true);
                        }
                        else {
                            System.out.println("Please enter a valid number choice.");
                            valid = false;
                            con.rollback();
                            con.setAutoCommit(true);
                        }
                        stmtCredit.close();
                    }
                    else {
                        System.out.println("Please enter a valid number choice.");
                        con.rollback();
                        con.setAutoCommit(true);
                        valid = false;
                    }
                    stmtBilling.close();
                } catch (SQLException ex){
                    System.out.println("Error: Database error. Please try again later.");
                    try {
                        con.rollback();
                    } catch (SQLException ex1) { //please don't ever get to here
                        System.out.println("Database rollback failed. Check internet connection. Exiting program.");
                        System.exit(1);
                    }
                    valid = true;
                } catch (IndexOutOfBoundsException ex){
                    System.out.println("Please enter a valid number choice.");
                    try {
                        con.rollback();
                    } catch (SQLException ex1) { //please don't ever get to here
                        System.out.println("Database rollback failed. Check internet connection. Exiting program.");
                        System.exit(1);
                    }
                    valid = false;
                }
            }
        }
    }
    
    private ArrayList<Long> viewCreditCards(int customerID){
        ArrayList<Long> cards = new ArrayList<>();
        try{
            Statement billing_list = con.createStatement();
            ResultSet result;
            result = billing_list.executeQuery("select card_num, security_code, "
                    + "expiration_date, street_address, city, state, zip from "
                    + "billing_info natural join credit_card where "
                    + "customer_id = " + customerID);
            if (!result.next()){
                System.out.println("You have no credit cards on file.");
                
            }
            else {
                
                int i = 1;
                do{
                    int security_code, zip;
                    long card_num;
                    String exp_date, street_add, city, state;
                    card_num = result.getLong(1);
                    cards.add(card_num);
                    security_code = result.getInt(2);
                    exp_date = result.getString(3);
                    street_add = result.getString(4);
                    city = result.getString(5);
                    state = result.getString(6);
                    zip = result.getInt(7);
                    System.out.println("Card " + i + ":");
                    System.out.println("Card Number: " + card_num);
                    System.out.println("Security Code: " + security_code);
                    System.out.println("Expiration Date: " + exp_date);
                    System.out.println("Street: " + street_add);
                    System.out.println("City: " + city);
                    System.out.println("State: " + state);
                    System.out.println("Zip Code: " + zip);
                    System.out.println("");
                    i++;
                } while (result.next());
            }
        } catch (SQLException e){
                System.out.println("Error: Database error, please try again.");
        }
        return cards;
    }
    
    private void makeAReservation(int customerID){
        
    }
    
    private void viewReservedFlights(int customerID){
        
    }
    
    private void cancelReservedFlight(int customerID){
        
    }
    
    private void printOptions(){
        System.out.println("1: Manage Credit Card Information");
        System.out.println("2: Make a Reservation");
        System.out.println("3: View Reserved Flights");
        System.out.println("4: Cancel Reserved Flights");
        System.out.println("5: Close Customer Interface");
        System.out.print("-->");
    }
    
    
}

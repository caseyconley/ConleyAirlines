/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conleyairlines;

import java.sql.*;
import java.util.*;
/**
 *
 * @author Casey Conley
 * Email: cdc214@lehigh.edu
 * Course: CSE 241
 * Title: Airline Semester Project
 * Purpose: The user interface for the database administrator of a fictional 
 *          airline company. Uses JDBC with a database housing the information 
 *          for the airline.
 * 
 */
public class DatabaseAdmin {
    Connection con;
    Scanner in;
    
    public DatabaseAdmin(Connection con, Scanner in){
        this.con = con;
        this.in = in;
    }
    
    public void openInterface(){
        System.out.println("What action would you like to perform?");
        
        boolean done = false;
        while(!done){
            printOptions();
            int userChoice;
            try{
                userChoice = in.nextInt();
                switch(userChoice){
                    case 1:
                        //Database Administrator interface open
                        System.out.println("Calling queryOption()...\n");
                        queryOption();
                        done = false;
                        break;
                    case 2:
                        //Manager interface open
                        System.out.println("Calling updateOption()...\n");
                        done = false;
                        break;
                    case 3:
                        //close the program
                        System.out.println("Exiting Database Administrator Interface.\n");
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
    
    private static void printOptions(){
        System.out.println("1: Standard SQL query");
        System.out.println("2: Update/Insert/Delete statement");
        System.out.println("3: Close Database Administrator Interface");
        System.out.print("-->");
    }
    
    private void queryOption(){
        in.nextLine();
        System.out.println("Please enter your SQL statement.");
        String q;
        
        q = in.nextLine();
        Statement stmt;
        try {
            stmt = con.createStatement();
            ResultSet result;
            result = stmt.executeQuery(q);
            ResultSetMetaData metadata = result.getMetaData();
            if(!result.next()) {
                System.out.println("Empty result.");
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
        } catch (SQLException ex) {
            System.out.println("Error: Query invalid. Please try again.");
        }
        System.out.println("");
    }
}

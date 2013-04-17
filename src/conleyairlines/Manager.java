
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
}

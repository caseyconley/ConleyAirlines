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
public class Manager {
    Connection con;
    Scanner in;
    
    public Manager(Connection con, Scanner in){
        this.con = con;
        this.in = in;
    }
}

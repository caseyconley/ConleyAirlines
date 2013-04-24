import java.util.*;
import java.sql.*;

class PilotCertification {
  public static void main(String[] args){
    Connection con;
    try{       
      Class.forName("oracle.jdbc.driver.OracleDriver");
      con = DriverManager.getConnection("jdbc:oracle:thin:@edgar2.cse.lehigh.edu:1521:cse241","cdc214","P884028629");
      Statement stmt = con.createStatement();
      ResultSet pilots = stmt.executeQuery("select id from pilot");
      if (pilots.next()){
        do {
          String s;
          long pilotID = pilots.getLong(1);
          s = "insert into pilot_certification values (" + pilotID + ", 747);";
          System.out.println(s);
          s = "insert into pilot_certification values (" + pilotID + ", 737);";
          System.out.println(s);
          s = "insert into pilot_certification values (" + pilotID + ", 787);";
          System.out.println(s);
          s = "insert into pilot_certification values (" + pilotID + ", 350);";
          System.out.println(s);
          s = "insert into pilot_certification values (" + pilotID + ", 380);";
          System.out.println(s);
        } while (pilots.next());
      }
      else {
        System.out.println("Pilots not found");
      }
      stmt.close();
    } catch (Exception e){
      System.out.println("Error");
    }
  }
}
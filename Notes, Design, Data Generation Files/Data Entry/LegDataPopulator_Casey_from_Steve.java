import java.sql.*;
import java.util.*;
import java.lang.Math;
import java.lang.StringBuilder;


public class LegDataPopulator_Casey_from_Steve {   
    public static void main(String args[]) {
        
      Scanner in = new Scanner(System.in);
      String startdate;
      String date, time;
      String arrivetime = "08:00";
      String departtime = "03:00";
      
      
      try{       
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@edgar2.cse.lehigh.edu:1521:cse241","cdc214","P884028629");
        Statement s = con.createStatement();
        
        ResultSet result;
        
        System.out.println("Legs leaving at 3am, 8am, 1pm, 6pm will be generated for each day for each airport-airport pair (10 airports)...");
        System.out.println("What is the date of generation? (ex. 04/06/13)");
        startdate = in.next();
        
        //String[] citycodes = new String[15];
        String citycodes[] = {"JFK", "SFO", "PHL","PHX","LAX","BDL","MIA","ATL","ORD","RDU"};
        
        StringBuilder str, str2, str3, str4;
        
        int p = 0, i=0;
        String q, u, lid, lid2;
        //String origin, destination;
        q = "select count(*) from pilot P where not exists (select * from leg where pilot_id = P.id)";
        result = s.executeQuery(q);
        result.next();
        int pilotsize = result.getInt(1);
        q = "select count(*) from plane P where not exists (select * from leg where plane_id = P.plane_id)";
        result = s.executeQuery(q);
        result.next();
        int planesize = result.getInt(1);
        String[] pid = new String[pilotsize];
        String[] planeid = new String[planesize];
        
        
        
        //select * from plane P where not exists (select * from leg where plane_id = P.plane_id)
        q = "select * from pilot P where not exists (select * from leg where pilot_id = P.id)";
        System.out.println("Pilots: ");
        result = s.executeQuery(q);
        for (i = 0; i < pilotsize; i++){
          if (!result.next()) System.out.println ("pID Not Available");
          else{
            pid[i] = result.getString("id");
            System.out.println (pid[i]);
          }
        }
        
        System.out.println("Planes: ");
        q = "select * from plane P where not exists (select * from leg where plane_id = P.plane_id)";
        result = s.executeQuery(q);
        for (i = 0; i < planesize; i++){
          if (!result.next()) System.out.println ("planeID Not Available");
          else{
            planeid[i] = result.getString("plane_id");
            System.out.println (planeid[i]);
          }
        }
        
        
        for(int j = 0; j < 9; j++){
          for(int k = j+1; k < 10; k++){
            //origin = citycodes[j];
            //destination = citycodes[k];     
            if (p >= pilotsize){
              break;
            }
            
            
            System.out.println("" + p + " ");
            lid = randomInt(900000);
            String price = randomInt(9000);
            str = new StringBuilder("insert into leg (leg_id, plane_id, pilot_id, time_departure, time_arrival, price  values (");
            str.append(lid + ", " + planeid[p] + ", '" + pid[p] + ",  to_char('" + startdate 
                         + "', 'DD-MON-YYYY'), to_char('" + startdate + "', 'DD-MON-YYYY'), " + price + ");");
            
            str2 = new StringBuilder("insert into leg_from (leg_id, callsign) values (");
            str2.append(lid + ", '" + citycodes[j] + "');");
            
            str3 = new StringBuilder("insert into leg_to (leg_id, callsign) values ('");
            str3.append(lid + "', '" + citycodes[k] + "');");
            
            //str4 = new StringBuilder("insert into flies (pID, planeID) values ('");
            //str4.append(pid[p] + "', '" + planeid[p] + "');");
            p++;
            
            System.out.println(str.toString());
            System.out.println(str2.toString());
            System.out.println(str3.toString());
            //System.out.println(str4.toString());
            
            
          }
          if (p >= pilotsize){
              break;
          }
        }    
        s.close();
        con.close();
      } catch(Exception e){e.printStackTrace();}
      
    } 
    private static String randomInt(int length){
      //ex. if 6 digit number is required, pass 900000
      Random rnd = new Random();
      int result = (length/9) + rnd.nextInt(length);
      return "" + result;    
    }
}

    
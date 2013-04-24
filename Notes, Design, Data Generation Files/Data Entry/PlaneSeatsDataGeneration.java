import java.util.*;
import java.sql.*;
import java.lang.StringBuilder;

class PlaneSeatsDataGeneration {
  public static void main(String[] args){
    Connection con;
    try{       
      Class.forName("oracle.jdbc.driver.OracleDriver");
      con = DriverManager.getConnection("jdbc:oracle:thin:@edgar2.cse.lehigh.edu:1521:cse241","cdc214","P884028629");
      int num, start_num;
      Scanner in = new Scanner(System.in);
      
      int plane_id;
      String seat_class;
      String q = "select p.plane_id, is_type.type from plane P join is_type on p.plane_id=is_type.plane_id where p.plane_id not in (select plane_id from plane_seats where plane_id = P.plane_id)";
      try {
        Statement stmtPlanes = con.createStatement();
        ResultSet resultPlanes = stmtPlanes.executeQuery(q);
        
        int planeID, type;
        if ( resultPlanes.next() ){
          do {
            planeID = resultPlanes.getInt(1);
            type = resultPlanes.getInt(2);
            int economy=0, business=0, firstclass=0;
            switch(type){
              case 747:
                economy = 300;
                business = 50;
                firstclass = 50;
                break;
              case 737:
                economy = 150;
                business = 25;
                firstclass = 25;
                break;
              case 787:
                economy = 200;
                business = 50;
                firstclass = 50;
                break;
              case 350:
                economy = 300;
                business = 50;
                firstclass = 50;
                break;
              case 380:
                economy = 150;
                business = 25;
                firstclass = 25;
                break;
            }
            String s;
            System.out.println("Inserting seats for plane " + planeID + " with " + (economy + business + firstclass) + " seats");
            for(int i = 1; i <= economy; i++){
              s = "INSERT INTO PLANE_SEATS (PLANE_ID,SEAT_CLASS,SEAT_NUMBER) VALUES (" + planeID + ", 'economy', " + i + ");";
              System.out.println(s);
              Statement stmtEconomy = con.createStatement();
              int economyConfirm = stmtPlanes.executeUpdate(q);
            }
            for(int j = economy + 1; j <= business; j++){
              s = "INSERT INTO PLANE_SEATS (PLANE_ID,SEAT_CLASS,SEAT_NUMBER) VALUES (" + planeID + ", 'business', " + j + ")";
              System.out.println(s);
              Statement stmtBusiness = con.createStatement();
              int businessConfirm = stmtPlanes.executeUpdate(q);
              
            }
            for(int h = business + 1; h <= firstclass; h++){
              s = "INSERT INTO PLANE_SEATS (PLANE_ID,SEAT_CLASS,SEAT_NUMBER) VALUES (" + planeID + ", 'first', " + h + ")";
              System.out.println(s);
              Statement stmtFirst = con.createStatement();
              int firstConfirm = stmtFirst.executeUpdate(q);
              
            }
          } while (resultPlanes.next());
          System.out.println("Updates completed");
        }
        else { 
          System.out.println("Error finding planes");
        }
      } catch (SQLException e) { 
        System.out.println("Error"); 
      }
    } catch(Exception e){
      System.out.println("error");
      System.exit(1);
    }
    
  }
}
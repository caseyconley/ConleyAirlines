import java.io.*;
import java.sql.*;

class Is_type_generator_Casey_from_Seth {
  public static void main (String[] arg) throws SQLException, IOException, java.lang.ClassNotFoundException {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@edgar2.cse.lehigh.edu:1521:cse241","cdc214",
                 "P884028629");
    Statement s = con.createStatement();
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    String q;
    ResultSet result;
    int types[] = {747, 737, 787, 350, 380};
    int temp = 0;
    q = "select * from plane where plane_id not in (select plane_id from is_type where type is not null)";
    try {
      result = s.executeQuery(q);
      result.next();
      do {
        System.out.println("INSERT INTO is_type VALUES (" + result.getString(1)  + ", " + types[temp%5]+ ");");
        temp++;
      } while (result.next());
    } catch (SQLException e) { }
    
    s.close();
    con.close();
  }
}
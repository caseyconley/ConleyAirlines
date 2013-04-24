import java.util.*;
import java.lang.StringBuilder;

class PlaneDataGeneration {
  public static void main(String[] args){
    int num;
    Scanner in = new Scanner(System.in);
    System.out.println("How many planes to generate?: ");
    num = in.nextInt();
    StringBuilder str;
    int midpoint = num/2;
    int seats=0,model_det,id_num;
    String model = "";
    Random rnd;
    for (int i=1; i; i++){
      rnd = new Random();
      id_num = 100000 + rnd.nextInt(900000);
      str = new StringBuilder("INSERT INTO PLANE (PLANE_ID,DATE_PUT_IN_SERVICE,MILES_FLOWN) VALUES ('");
      str.append(id_num);
      
      
      str.append(model + "','" + seats + "','21-MAR-2013','10000');");
      System.out.println(str.toString());
    }
    
    for (int j=midpoint+1; j<=num; j++){
      rnd = new Random();
      id_num = 100000 + rnd.nextInt(900000);
      str = new StringBuilder("INSERT INTO PLANE (PLANE_ID,BRAND,MODEL,NUM_SEATS,DATE_PUT_IN_SERVICE,MILES_FLOWN) VALUES ('");
      str.append(id_num);
      str.append("','Airbus','");
      model_det = j%2;
      switch(model_det){
        case 0:
          model = "350";
          seats = 400;
          break;
        case 1:
          model = "380";
          seats = 200;
          break;
      }
      str.append(model + "','" + seats + "','21-MAR-2013','10000');");
      System.out.println(str.toString());
    }
  }
}
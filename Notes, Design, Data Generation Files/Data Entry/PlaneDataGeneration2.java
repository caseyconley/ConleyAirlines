import java.util.*;
import java.lang.StringBuilder;

class PlaneDataGeneration2 {
  public static void main(String[] args){
    int num;
    Scanner in = new Scanner(System.in);
    System.out.println("How many planes to generate?: ");
    num = in.nextInt();
    StringBuilder str;
    int seats=0,model_det,id_num;
    String model = "";
    Random rnd;
    for (int i=1; i<=num; i++){
      rnd = new Random();
      id_num = 100000 + rnd.nextInt(900000);
      str = new StringBuilder("INSERT INTO PLANE (PLANE_ID,DATE_PUT_IN_SERVICE,MILES_FLOWN) VALUES (");
      str.append(id_num);
      Random rndY = new Random();
      Random rndD = new Random();
      Random rndM = new Random();
      int rndYear = 2000 + rndY.nextInt(14);
      int rndDay = rndD.nextInt(28);
      String day = "" + rndDay + "";
      if(rndDay < 10 && rndDay > 1){
        day = "0" + rndDay + "";
      }
      if(rndDay < 1){
        day = "01";
      }
      int rndMonth = rndM.nextInt(12);
      String month;
      switch (rndMonth){
        case 1:
          
          month = "JAN";
          break;
        case 2:
          month = "FEB";
          break;
        case 3:
          month = "MAR";
          break;
        case 4:
          month = "APR";
          break;
        case 5:
          month = "MAY";
          break;
        case 6:
          month = "JUN";
          break;
        case 7:
          month = "JUL";
          break;
        case 8:
          month = "AUG";
          break;
        case 9:
          month = "SEP";
          break;
        case 10:
          month = "OCT";
          break;
        case 11:
          month = "NOV";
          break;
        case 12:
          month = "DEC";
          break;
        default:
          month = "JAN";
          break;
      }
      str.append(",'" + day + "-" + month + "-" + rndYear + "',10000);");
      System.out.println(str.toString());
    }
    
    
  }
}
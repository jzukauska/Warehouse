

import java.util.*;




import java.text.*;
import java.io.*;
public class Loginstate extends WarState{
  private static final int CLERK_LOGIN = 0;
  private static final int USER_LOGIN = 1;
  private static final int Manager_Login = 3;
  private static final int EXIT = 2;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));  
  private WarehouseContext context;
  private static Loginstate instance;
  
  
  private Loginstate() {
      super();
     // context = LibContext.instance();
  }

  public static Loginstate instance() {
    if (instance == null) {
      instance = new Loginstate();
    }
    return instance;
  }

  public int getCommand() {
    do {
      try {
        int value = Integer.parseInt(getToken("Enter command:" ));
        if (value <= Manager_Login && value >= CLERK_LOGIN) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
  }

  public String getToken(String prompt) {
    do {
      try {
        System.out.println(prompt);
        String line = reader.readLine();
        StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
        if (tokenizer.hasMoreTokens()) {
          return tokenizer.nextToken();
        }
      } catch (IOException ioe) {
        System.exit(0);
      }
    } while (true);
  }
 
  private boolean yesOrNo(String prompt) {
    String more = getToken(prompt + " (Y|y)[es] or anything else for no");
    if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
      return false;
    }
    return true;
  }

  private void clerk(){
    (WarehouseContext.instance()).setLogin(WarehouseContext.IsClerk);
    (WarehouseContext.instance()).changeState(0);
  }

  private void user(){
    String userID = getToken("Please input the user id: ");
    if (Warehouse.instance().searchMembership(userID) != null){
      (WarehouseContext.instance()).setLogin(WarehouseContext.IsClient);
      (WarehouseContext.instance()).setUser(userID);      
      (WarehouseContext.instance()).changeState(1);
    }
    else 
      System.out.println("Invalid user id.");
  } 
  private void manager(){
	  
	  (WarehouseContext.instance()).setLogin(WarehouseContext.IsManager);
	    (WarehouseContext.instance()).changeState(3);
  }

  public void process() {
    int command;
    System.out.println("Input: \n0 to login as Clerk\n"+ 
                        "1 to login as user\n" +
                        
                        "2 to exit the system\n" +
                        "3 to login as manager");     
    while ((command = getCommand()) != EXIT) {

      switch (command) {
        case CLERK_LOGIN:       clerk();
                                break;
        case USER_LOGIN:        user();
                                break;
        case Manager_Login:     manager();
        						break;
        default:                System.out.println("Invalid choice");
                                
      }
      System.out.println("Input: \n0 to login as Clerk\n"+ 
              "1 to login as user\n" +
              
              "2 to exit the system\n" +
              "3 to login as manager"); 
    }
    (WarehouseContext.instance()).changeState(2);
  }

  public void run() {
    process();
  }
}

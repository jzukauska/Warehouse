

import java.util.*;




import java.text.*;
import java.io.*;
public class Loginstate extends WarState{
  private static final int CLERK_LOGIN = 0;
  private static final int USER_LOGIN = 1;
  private static final int EXIT = 2;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));  
  private LibContext context;
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
        if (value <= EXIT && value >= CLERK_LOGIN) {
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
    (LibContext.instance()).setLogin(LibContext.IsClerk);
    (LibContext.instance()).changeState(0);
  }

  private void user(){
    String userID = getToken("Please input the user id: ");
    if (Library.instance().searchMembership(userID) != null){
      (LibContext.instance()).setLogin(LibContext.IsUser);
      (LibContext.instance()).setUser(userID);      
      (LibContext.instance()).changeState(1);
    }
    else 
      System.out.println("Invalid user id.");
  } 

  public void process() {
    int command;
    System.out.println("Please input 0 to login as Clerk\n"+ 
                        "input 1 to login as user\n" +
                        "input 2 to exit the system\n");     
    while ((command = getCommand()) != EXIT) {

      switch (command) {
        case CLERK_LOGIN:       clerk();
                                break;
        case USER_LOGIN:        user();
                                break;
        default:                System.out.println("Invalid choice");
                                
      }
      System.out.println("Please input 0 to login as Clerk\n"+ 
                        "input 1 to login as user\n" +
                        "input 2 to exit the system\n"); 
    }
    (LibContext.instance()).changeState(2);
  }

  public void run() {
    process();
  }
}

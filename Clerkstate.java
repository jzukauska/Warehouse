

import java.util.*;

import java.text.*;
import java.io.*;



public class Clerkstate extends WarState {
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  private WarehouseContext context;
  
  
  private static Clerkstate instance;
  private static final int EXIT = 0;
  private static final int ADD_MEMBER = 1;
  private static final int ADD_BOOKS = 2;
  private static final int ISSUE_BOOKS = 3;
  private static final int RETURN_BOOKS = 4;
  private static final int REMOVE_BOOKS = 6;
  private static final int PLACE_HOLD = 7;
  private static final int REMOVE_HOLD = 8;
  private static final int PROCESS_HOLD = 9;
  private static final int GET_TRANSACTIONS = 10;
  private static final int USERMENU = 11;
  private static final int HELP = 13;
  
  
  private Clerkstate() {
      super();
      warehouse = Warehouse.instance();
      //context = LibContext.instance();
  }

  public static Clerkstate instance() {
    if (instance == null) {
      instance = new Clerkstate();
    }
    return instance;
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
  public int getNumber(String prompt) {
    do {
      try {
        String item = getToken(prompt);
        Integer num = Integer.valueOf(item);
        return num.intValue();
      } catch (NumberFormatException nfe) {
        System.out.println("Please input a number ");
      }
    } while (true);
  }
  public Calendar getDate(String prompt) {
    do {
      try {
        Calendar date = new GregorianCalendar();
        String item = getToken(prompt);
        DateFormat df = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
        date.setTime(df.parse(item));
        return date;
      } catch (Exception fe) {
        System.out.println("Please input a date as mm/dd/yy");
      }
    } while (true);
  }
  public int getCommand() {
    do {
      try {
        int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
        if (value >= EXIT && value <= HELP) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
  }

  public void help() {
    System.out.println("Enter a number between 0 and 12 as explained below:");
    System.out.println(EXIT + " to Exit\n");
    System.out.println(ADD_MEMBER + " to add a member");
    System.out.println(ADD_BOOKS + " to  add books");
    System.out.println(RETURN_BOOKS + " to  return books ");
    System.out.println(REMOVE_BOOKS + " to  remove books");
    System.out.println(PROCESS_HOLD + " to  process holds");
    System.out.println(USERMENU + " to  switch to the user menu");
    System.out.println(HELP + " for help");
  }



  public void usermenu()
  {
    String userID = getToken("Please input the user id: ");
    if (Warehouse.instance().searchMembership(userID) != null){
      (WarehouseContext.instance()).setUser(userID);      
      (WarehouseContext.instance()).changeState(1);
    }
    else 
      System.out.println("Invalid user id."); 
  }

  public void logout()
  {
    (WarehouseContext.instance()).changeState(0); // exit with a code 0
  }
 

  public void process() {
    int command;
    help();
    while ((command = getCommand()) != EXIT) {
      switch (command) {
      
        case HELP:              help();
                                break;
      }
    }
    logout();
  }
  public void run() {
    process();
  }
}

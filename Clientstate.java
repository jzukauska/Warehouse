

import java.util.*;

import java.text.*;
import java.io.*;
public class Clientstate extends WarState {
  private static Clientstate clientstate;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  
  
  private static final int EXIT = 0;
  private static final int ISSUE_BOOKS = 3;
  private static final int RENEW_BOOKS = 5;
  private static final int PLACE_HOLD = 7;
  private static final int REMOVE_HOLD = 8;
  private static final int GET_TRANSACTIONS = 10;
  private static final int HELP = 13;
  
  
  private Clientstate() {
    warehouse = Warehouse.instance();
  }

  public static Clientstate instance() {
    if (clientstate == null) {
      return clientstate = new Clientstate();
    } else {
      return clientstate;
    }
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
    System.out.println(ISSUE_BOOKS + " to  issue books to a  member");
    System.out.println(RENEW_BOOKS + " to  renew books ");
    System.out.println(PLACE_HOLD + " to  place a hold on a book");
    System.out.println(REMOVE_HOLD + " to  remove a hold on a book");
    System.out.println(GET_TRANSACTIONS + " to  print transactions");
    System.out.println(HELP + " for help");
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

  public void logout()
  {
    if ((WarehouseContext.instance()).getLogin() == WarehouseContext.IsClerk)
       { //stem.out.println(" going to clerk \n ");
         (WarehouseContext.instance()).changeState(1); // exit with a code 1
        }
    else if (WarehouseContext.instance().getLogin() == WarehouseContext.IsManager)
       {  //stem.out.println(" going to login \n");
        (WarehouseContext.instance()).changeState(0); // exit with a code 2
       }
    else 
       (WarehouseContext.instance()).changeState(2); // exit code 2, indicates error
  }
 
}

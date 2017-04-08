

import java.util.*;

import java.text.*;
import java.io.*;
public class Managerstate extends WarState {
  private static Managerstate managerstate;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  
  
  private static final int EXIT = 0;
  private static final int addManufacturer = 3;
  private static final int associateProdandMan = 5;
  private static final int disassociateProdandMan = 7;
  private static final int changePrice = 8;
  private static final int makeClerk = 10;
  private static final int HELP = 13;

  
  private Managerstate() {
    warehouse = Warehouse.instance();
  }

  public static Managerstate instance() {
    if (managerstate == null) {
      return managerstate = new Managerstate();
    } else {
      return managerstate;
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
  public double getNumber(String prompt) {
    do {
      try {
        String item = getToken(prompt);
        double num = Double.parseDouble(item);
        return num;
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
	  System.out.println(addManufacturer + " to add a Manufacturer"); 
	  System.out.println(associateProdandMan + " to associate a product and manufacturer");  
	  System.out.println(disassociateProdandMan + " to dissassociate a product and manufacturer");  
	  System.out.println(changePrice + "modify a price of a product");  
	  System.out.println(makeClerk + " to change to clerk mode");  
	  System.out.println(HELP + " for help");
  }


  

  public void process() {
    int command;
    help();
    
    if !WarehouseContext.instance().
    
    while ((command = getCommand()) != EXIT) {
      switch (command) {

      case addManufacturer: addManufacturer(); break;
      case associateProdandMan: associateProdandMan(); break;
      case disassociateProdandMan: disassociateProdandMan(); break;
      case changePrice : changePrice(); break;
      case makeClerk : makeClerk(); break;
      
        case HELP:              help();
                                break;
      }
    }
    logout();
  }

  private void makeClerk() {
	  WarehouseContext.instance().changeState(0);
}

private void changePrice() {
	
	String productName = getToken("Enter product id to change price for");
	double newPrice = getNumber("Enter new price for the item");
	
	if (warehouse.changePrice(productName, newPrice)) {
		System.out.println("Change successful");
		
	}
	else{
		System.out.println("Failed to change price");
		
	}
	 
	
}

private void disassociateProdandMan() {
	String manufacturerId;
	Manufacturer manufacturer;
	String productId;
	Product product;

	do {
		manufacturerId = getToken("Enter manufacturer id");
		manufacturer = warehouse.findManufacturer(manufacturerId);

		if (manufacturer == null) {
			System.out.println("Invalid manufacturer id, please try again.");
		}
	} while (manufacturer == null);

	do {
		productId = getToken("Enter product id");
		product = warehouse.findProduct(productId);

		if (product == null) {
			System.out.println("Invalid product id, please try again.");
		}
	} while (product == null);

	product = warehouse.disassociateProductAndManufacturer(productId, manufacturerId);
	System.out.println("Disassociate complete between product:" + product + " and manufacturer:" + manufacturer);
}

private void associateProdandMan() {
	String manuId;
	Manufacturer manufacturer;
	String productId;
	Product product;

	do {
		manuId = getToken("Enter manufacturer id");
		manufacturer = warehouse.findManufacturer(manuId);

		if (manufacturer == null) {
			System.out.println("Invalid manufacturer id, enter new id.");
		}
	} while (manufacturer == null);

	do {
		productId = getToken("Enter product id");
		product = warehouse.findProduct(productId);

		if (product == null) {
			System.out.println("Invalid product id, please try again.");
		}
	} while (product == null);

	product = warehouse.associateProductAndManufacturer(productId, manuId);
	System.out.println("Associated: " + product + " and manufacturer:" + manufacturer);
}

private void addManufacturer() {
	String name = getToken("Enter manufacturer name");
	Manufacturer result;
	result = warehouse.addManufacturer(name);
	if (result == null) {
		System.out.println("Could not add manufacturer");
	}
	System.out.println("Added manufacturer [" + result + "]");
}

public void run() {
    process();
  }

  public void logout()
  {
	  WarehouseContext.instance().changeState(2); // exit with a code 0
  }
 
}



import java.util.*;

import java.text.*;
import java.io.*;



public class Clerkstate extends WarState {
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Warehouse warehouse;
	private WarehouseContext context;


	private static Clerkstate instance;

	private static final int EXIT = 0;
	private static final int addClient = 1;
	private static final int addProducts = 2;  

	private static final int showClients = 3;
	private static final int showProducts = 4;
	private static final int showManufacturers = 6;


	private static final int recieveOrder = 7;
	private static final int recievePayment = 8;
	private static final int showWaitlistedOrders = 9;

	private static final int makeOrderForClient= 11;
	private static final int makeClient = 12;
	private static final int loadDatabase = 13;




	private static final int HELP = 99;


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
		System.out.println("Enter a number between 0 and 99 as explained below:");
		System.out.println(EXIT + " to exit");
		System.out.println(addClient + " to add client");
		System.out.println(addProducts + " to add products");
		System.out.println();

		System.out.println(showClients + " to show clients");
		System.out.println(showProducts + " to show products");
		System.out.println(showManufacturers + " to show manufacturers");
		System.out.println();

		System.out.println(recieveOrder + " to recieve order");
		System.out.println(recievePayment + " to recieve payment");
		System.out.println(showWaitlistedOrders + " to show waitlisted orders");
		System.out.println();

		System.out.println(makeOrderForClient + " to create an order for a client");

		System.out.println(makeClient + " to change to client menu");
		System.out.println(loadDatabase + " to load database");
		System.out.println();

		System.out.println(HELP + " to display help menu");
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


			case addClient:
				addClient();
				break;
			case addProducts:
				addProducts();
				break;
			case showClients:
				showClients();
				break;
			case showProducts:
				showProducts();
				break;
			case showManufacturers:
				showManufacturers();
				break;
			case recieveOrder:
				recieveOrder();
				break;
			case recievePayment:
				recievePayment();
				break;
			case showWaitlistedOrders:
				showWaitlistedOrders();
				break;
			case makeOrderForClient:
				makeOrderForClient();
				break;
			case makeClient:
				makeClient();
				break;
			case loadDatabase:
				loadDatabase();
				break;
			case HELP:
				help();
				break;
			}
		}
		logout();
	}
	private void addClient() {
		String name = getToken("Enter client name");
		Client result;
		result = warehouse.addClient(name);
		if (result == null) {
			System.out.println("Failed to add client, canceling input");
		}
		System.out.println("Added client [" + result + "]");
	}

	private void addProducts() {
		String name = getToken("Enter product name");
		int quantity = Integer.parseInt(getToken("Enter Product quantity"));
		double cost = Double.parseDouble(getToken("Enter price for item"));
		Product result;

		result = warehouse.addProduct(name, quantity, cost);
		if (result == null) {
			System.out.println("Could not add product");
		}
		System.out.println("Added product [" + result + "]");
	}

	private void showClients() {
		warehouse.displayClients();
	}

	private void showProducts() {
		warehouse.displayProducts();
	}

	private void showManufacturers() {
		warehouse.displayManufacturers();
	}

	private void recieveOrder() {
		String productName;
		productName = getToken("Enter Product id to recieve");

		if (warehouse.findProduct(productName) != null) {
			String productQuantity = getToken("Enter amount to receive");
			warehouse.recieveProduct(productName, Integer.parseInt(productQuantity), reader);

		} else {
			System.out.println("Couldn't find product");

		}
	}

	private void recievePayment() {
		double payment;
		String clientName;

		clientName = getToken("Enter client id to make payment for");

		payment = Double.parseDouble(getToken("Enter amount to be payed"));

		warehouse.acceptClientPayment(clientName, payment);
	}

	private void showWaitlistedOrders() {
		String productId;

		productId = getToken("Enter product id to show waitlisted items for");
		Product product = warehouse.findProduct(productId);

		if (product != null)
			warehouse.showWaitlistForProduct(product);

		else
			System.out.println("Coudldn't find product with id: " + product);

	}



	private void makeOrderForClient() {
		String tempClient;
		tempClient = getToken("Enter client id to create order for ");

		if (warehouse.searchMembership(tempClient) != null) {
			processMatch(tempClient);
		}else{
			System.out.println("Couldn't find client to associate");
		}
	}

	private void processMatch(String tempClient2){
		String productStringId;
		Product tempProduct;
		int tempQuantity;
		boolean addItemsToOrder;
		String tempString;
		Order createdOrder = new Order();
		Order result;
		String tempClient = tempClient2;
		char cont;

		do {
			productStringId = getToken("Enter First id of product to be added to the list");
			tempProduct = warehouse.findProduct(productStringId);
			if (tempProduct != null) {

				tempQuantity = Integer.parseInt(getToken("Enter the quantity of that item: "));

				addItemsToOrder = createdOrder.insertlistedItem(tempProduct, tempQuantity);
				if (!addItemsToOrder) {
					System.out.println("Failed to add item to order");

				} else
					System.out.println("Added Item");

			} else {
				System.out.println("Could not find item");
			}

			tempString = getToken("Continue adding items? Y to continue");
			cont = tempString.charAt(0);
		} while (cont == 'y' || cont == 'Y');

		System.out.println("Processing order");
		result = warehouse.processOrder(createdOrder, tempClient);

		if (result == null) {
			System.out.println("Could not add order");
		} else {
			System.out.println("Added product [" + result + "]");
		}

	}

	private void makeClient() {
		String clientID = getToken("Please input the client id: ");
	    if (warehouse.searchMembership(clientID) != null) {
	      (WarehouseContext.instance()).setUser(clientID);
	      (WarehouseContext.instance()).changeState(1);
	    } else
	      System.out.println("Invalid client id.");
	}

	private void loadDatabase() {
		WarehouseContext.instance().load();
		
	}

	public void run() {
		process();
	}
}

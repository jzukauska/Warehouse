//Class by Jacob Zukauska, Josh Wilford, Ryan Shaughnessy 




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.StringTokenizer;

public class UserInterface {
	private static UserInterface userInterface;
	private static Warehouse warehouse;
	private static final int End = 0;

	private static final int addClient = 1;
	private static final int addManufacturer = 2;
	private static final int addproduct = 3;
	private static final int showClients = 4;
	private static final int showManufacturers = 5;
	private static final int showProducts = 6;
	private static final int associateProductandManufacturer = 7;
	private static final int dissaccociateProductandManufacturer = 8;
	private static final int save = 9;
	private static final int load = 10;
	private static final int HELP = 11;
	private static final int addOrder = 12;
	private static final int acceptClientPayment = 13;
	
	private static final int showWaitlistedOrders = 14;
	private static final int listunpaidBalance = 15;
	private static final int listOrdersAndwaitLists = 16;
	private static final int recieveOrder = 17;

	public static UserInterface instance() {
		if (userInterface == null) {
			return userInterface = new UserInterface();

		} else {

			return userInterface;
		}
	}

	public static void main(String[] s) {
		UserInterface.instance().process();
	}

	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	private UserInterface() {
		load();

	}

	/*
	 * private boolean yesOrNo(String prompt) { String more = getToken(prompt +
	 * " (Y|y)[es] or anything else for no"); if (more.charAt(0) != 'y' &&
	 * more.charAt(0) != 'Y') { return false; } return true; }
	 */

	private void acceptClientPayment() {
		double payment;
		String clientName;

		clientName = getToken("Enter client id to make payment for");

		payment = Double.parseDouble(getToken("Enter amount to be payed"));

		warehouse.acceptClientPayment(clientName, payment);
	}

	public void addClient() {
		String name = getToken("Enter client name");
		Client result;
		result = warehouse.addClient(name);
		if (result == null) {
			System.out.println("Failed to add client, canceling input");
		}
		System.out.println("Added client [" + result + "]");
	}

	public void addManufacturer() {
		String name = getToken("Enter manufacturer name");
		Manufacturer result;
		result = warehouse.addManufacturer(name);
		if (result == null) {
			System.out.println("Could not add manufacturer");
		}
		System.out.println("Added manufacturer [" + result + "]");
	}

	public void addProduct() {
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

	public void associateProductAndManufacturer() {
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

	public void createOrder() {
		Order createdOrder = new Order();
		char cont;

		String productStringId;
		String tempString;
		int tempQuantity;
		Product tempProduct;
		String tempClient;

		boolean match = false;

		boolean addItemsToOrder;
		Order result;

		tempClient = getToken("Enter client id to create order for ");
		Iterator i = warehouse.getClientIterator();

		while (i.hasNext()) {
			Client client = (Client) i.next();

			if (client.equal(tempClient)) {
				match = true;

			}

		}

		if (match) {

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
		} else
			System.out.println("Couldn't find client to associate");

	}

	public void disassociateProductAndManufacturer() {
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

	public int getCommand() {
		do {
			try {
				int value = Integer.parseInt(getToken("Enter command: " + HELP + " for help"));
				if (value >= End && value <= 20) {
					return value;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Enter a number");
			}
		} while (true);
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

	public String getToken(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
				if (tokenizer.hasMoreTokens()) {
					return tokenizer.nextToken();
				}
			} catch (IOException ioe) {

				System.exit(0);
			}
		} while (true);

	}

	public void help() {
		System.out.println("Enter a integer number between 0 and 11 as explained below:");
		System.out.println(End + " to exit\n");

		System.out.println(addClient + " to add a client");
		System.out.println(addManufacturer + " to add a manufacturer");
		System.out.println(addproduct + " to add a product");
		System.out.println(showClients + " to list all clients");
		System.out.println(showManufacturers + " to list all manufacturers");
		System.out.println(showProducts + " to list all products");
		System.out.println(associateProductandManufacturer + " to associate a product and manufacturer");
		System.out.println(dissaccociateProductandManufacturer + " to disassociate a product and manufacturer\n");

		System.out.println(addOrder + " to create an order");
		System.out.println(acceptClientPayment + " to accept payment\n");

		System.out.println(showWaitlistedOrders + " to show waitlisted orders for a product");
		System.out.println(listunpaidBalance + " to list clients with unpaid balances");
		System.out.println(listOrdersAndwaitLists + " to list all orders\n");

		System.out.println(recieveOrder + " to recieve an order\n");

		System.out.println(save + " to save data");
		System.out.println(load + " to load data\n");
		System.out.println(HELP + " for help");

	}

	private void listOrders() {

		warehouse.listOrders();
	}

	private void listunpaidBalance() {

		warehouse.listUnpaidBalance();

	}

	private void load() {
		try {
			Warehouse newWarehouse = Warehouse.load();

			if (newWarehouse != null) {
				System.out.println("Database loaded \n");
				warehouse = newWarehouse;
			} else {
				System.out.println("No database exists, creating new one");
				warehouse = Warehouse.instance();

				
			}
		} catch (Exception cnfe) {
			cnfe.printStackTrace();
		}
	}

	

	public void process() {
		int command;
		help();
		while ((command = getCommand()) != End) {
			switch (command) {

			case HELP:
				help();
				break;
			case save:
				save();
				break;
			case load:
				load();
				break;
			case addClient:
				addClient();
				break;
			case addManufacturer:
				addManufacturer();
				break;
			case addproduct:
				addProduct();
				break;
			case showClients:
				showClients();
				break;
			case showManufacturers:
				showManufacturers();
				break;
			case showProducts:
				showProducts();
				break;
			case associateProductandManufacturer:
				associateProductAndManufacturer();
				break;
			case dissaccociateProductandManufacturer:
				disassociateProductAndManufacturer();
				break;
			case addOrder:
				createOrder();
				break;
			case acceptClientPayment:
				acceptClientPayment();
				break;
			case showWaitlistedOrders:
				showWaitlistedOrdersforProduct();
				break;

			case listunpaidBalance:
				listunpaidBalance();
				break;
			case listOrdersAndwaitLists:
				listOrders();
				break;
			case recieveOrder:
				recieveOrder();
				break;
			default:
				break;

			}
		}
		save();

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

	private void save() {
		if (warehouse.save()) {
			System.out.println("The warehouse has been successfully saved Database\n");
		} else {
			System.out.println("There has been an error in saving\n");
		}
	}

	public void showClients() {
		Iterator allClient = warehouse.getClientIterator();
		while (allClient.hasNext()) {
			Client client = (Client) (allClient.next());
			System.out.println(client);
		}
	}

	public void showManufacturers() {
		Iterator allManu = warehouse.getManufacturerIterator();
		while (allManu.hasNext()) {
			Manufacturer manufacturer = (Manufacturer) (allManu.next());
			System.out.println(manufacturer);
		}
	}

	public void showProducts() {
		Iterator allProduct = warehouse.getProductIterator();
		while (allProduct.hasNext()) {
			Product product = (Product) (allProduct.next());
			System.out.println(product);
		}
	}

	public void showWaitlistedOrdersforProduct() {
		String productId;

		productId = getToken("Enter product id to show waitlisted items for");
		Product product = warehouse.findProduct(productId);

		if (product != null)
			warehouse.showWaitlistForProduct(product);

		else
			System.out.println("Coudldn't find product with id: " + product);

	}

}
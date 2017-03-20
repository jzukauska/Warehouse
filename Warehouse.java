//Class by Jacob Zukauska, Josh Wilford, Ryan Shaughnessy 



import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.StringTokenizer;



public class Warehouse implements Serializable {
	// private BufferedReader reader = new BufferedReader(new
	// InputStreamReader(System.in));
	private static final long serialVersionUID = 1L;
	private static Warehouse warehouse;

	public static Warehouse instance() {
		if (warehouse == null) {

			// instantiate all singletons
			ClientIdServer.instance();
			ProductIdServer.instance();
			ManufacturerIdServer.instance();
			OrderIdServer.instance();

			return (warehouse = new Warehouse());
		} else {
			return warehouse;
		}
	}

	public static Warehouse load() {

		try {
			FileInputStream file = new FileInputStream("Database");
			ObjectInputStream input = new ObjectInputStream(file);
			input.readObject();

			ClientIdServer.retrieve(input);
			ManufacturerIdServer.retrieve(input);
			ProductIdServer.retrieve(input);
			OrderIdServer.retrieve(input);

			return warehouse;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			return null;
		}
	}

	private ClientList clientList;
	private ManufacturerList manufacturerList;

	// Warehouse constructor

	private Inventory inventory;

	private OrderList orderlist;

	public Warehouse() {

		// Instantiate the items the warehouse manages
		clientList = ClientList.instance();
		manufacturerList = ManufacturerList.instance();
		inventory = Inventory.instance();
		orderlist = OrderList.instance();

	}

	public void acceptClientPayment(String clientName, double payment) {
		Client client;
		client = clientList.findParticularClient(clientName);

		if (client != null) {
			client.creditInvoice(payment);
			System.out.println("Payment successful");

		} else {
			System.out.println("Payment unsuccessful");
		}
	}

	public Client addClient(String name) {

		Client client = new Client(name);

		if (clientList.insertClientNode(client)) {

			return client;
		}

		return null;
	}

	public Manufacturer addManufacturer(String name) {

		Manufacturer manufacturer = new Manufacturer(name);

		if (manufacturerList.addManufacturer(manufacturer)) {

			return manufacturer;
		}

		return null;
	}

	public Product addProduct(String name, int quantity, double cost) {

		Product product = new Product(name, quantity, cost);

		if (inventory.insertProduct(product)) {

			return product;
		}

		return null;
	}

	public Product associateProductAndManufacturer(String productId, String manufacturerId) {
		Product product = inventory.find(productId);

		if (product != null) {
			Manufacturer manufacturer = manufacturerList.search(manufacturerId);

			product.addManufacturer(manufacturerId);
			manufacturer.addProduct(productId);
		}

		return product;
	}

	public Product disassociateProductAndManufacturer(String productId, String manufacturerId) {
		Product product = inventory.find(productId);

		if (product != null) {
			Manufacturer manufacturer = manufacturerList.search(manufacturerId);

			product.removeManufacturer(manufacturerId);
			manufacturer.removeProduct(productId);
		}

		return product;
	}

	public Manufacturer findManufacturer(String manufacturerId) {
		return manufacturerList.search(manufacturerId);
	}

	public Product findProduct(String productId) {
		return inventory.find(productId);
	}

	public Iterator getClientIterator() {
		return clientList.getClients();
	}

	public Iterator getManufacturerIterator() {
		return manufacturerList.getManufacturers();
	}

	public Iterator getOrderIterator() {

		return orderlist.getOrder();
	}

	public Iterator getProductIterator() {
		return inventory.getProducts();
	}

	public String getToken(String prompt, BufferedReader reader) {
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

	// phase 2

	public void listOrders() {
		System.out.println(orderlist);
	}

	public void listUnpaidBalance() {

		clientList.listOutstandingBalances();

	}

	public Order processOrder(Order order, String tempClient) {

		boolean test;

		double grandTotal;
		Order newishOrder = order;

		newishOrder.setClientReferenceId(tempClient);

		order.createWaitList();

		clientList.debitClient(tempClient, newishOrder.getTotals());

		if (orderlist.insertOrderNode(newishOrder)) {

			return newishOrder;
		} else
			return null;

	}

	private void readObject(java.io.ObjectInputStream input) {
		try {
			input.defaultReadObject();
			if (warehouse == null) {
				warehouse = (Warehouse) input.readObject();
			} else {
				input.readObject();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void recieveProduct(String productName, int i, BufferedReader reader) {
		Product actualProductToMatch = inventory.find(productName);
		Iterator allOrders = orderlist.getOrder();

		while (allOrders.hasNext()) {
			Order eachOrder = (Order) allOrders.next();

			Iterator waitListIterator = eachOrder.getWaitListIterator();

			while (waitListIterator.hasNext()) {
				OrderLineItem waitListForOrder = (OrderLineItem) waitListIterator.next();

				if (waitListForOrder.product.equals(actualProductToMatch) && i >= waitListForOrder.quantity
						&& waitListForOrder.quantity > 0) {

					System.out.println("Order: " + eachOrder.getId() + " has " + waitListForOrder.quantity
							+ " items waiting to be filled");
					String prompt = getToken(", continue?y/n", reader);

					prompt = prompt.toUpperCase();
					if (prompt.startsWith("Y")) {

						int amountToFill;

						amountToFill = waitListForOrder.quantity;

						waitListForOrder.quantity = 0;

						eachOrder.insertlistedItem(actualProductToMatch, amountToFill);

						i -= amountToFill;

						clientList.debitClient(eachOrder.getClientReferenceId(),
								amountToFill * actualProductToMatch.getCost());
					}

				}

			}

		}
		// if there is any product left put it in the product
		System.out.println("Depositing " + i + " " + actualProductToMatch.getName());
		actualProductToMatch.setQuantity(actualProductToMatch.getQuantity() + i);

	}

	public boolean save() {
		try {
			FileOutputStream file = new FileOutputStream("Database");
			ObjectOutputStream output = new ObjectOutputStream(file);
			output.writeObject(warehouse);

			output.writeObject(ClientIdServer.instance());
			output.writeObject(ManufacturerIdServer.instance());
			output.writeObject(ProductIdServer.instance());
			output.writeObject(OrderIdServer.instance());

			output.close();
			return true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
	}

	public void showWaitlistForProduct(Product product) {

		orderlist.outPutWaitListForProduct(product);
	}

	private void writeObject(java.io.ObjectOutputStream output) {
		try {
			output.defaultWriteObject();
			output.writeObject(warehouse);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	public Client searchMembership(String userID) {
		
		Client tempClient = clientList.findParticularClient(userID);
		return tempClient;
	}
	
	public void viewClientDetails(String userID) {
        Client tempClient;
        tempClient = clientList.findParticularClient(userID);
        System.out.println(tempClient);
    }
	
	public void priceCheck(String productID){
		Product tempProduct = inventory.find(productID);
		System.out.println(tempProduct.getCost());
	}

	public void displayClients() {
		System.out.println(clientList);
	}

	public void displayProducts() {
		System.out.println(inventory);
	}

	public void displayManufacturers() {
		System.out.println(manufacturerList);
	}
}
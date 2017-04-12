
import java.util.*;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.text.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.*;

public class Clientstate extends WarState {

	private static Clientstate clientstate;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Warehouse warehouse;

	private JFrame frame;

	//
	private AbstractButton

	VIEWACCOUNTDETAIL, PUTINORDER, CHECKPRICEOFPRODUCT, EXIT;

	/*
	 * private static final int EXIT = 0; private static final int
	 * VIEWACCOUNTDETAIL = 1; private static final int PUTINORDER = 2; private
	 * static final int CHECKPRICEOFPRODUCT = 3; private static final int HELP =
	 * 4;
	 */

	private Clientstate() {
		warehouse = Warehouse.instance();

		VIEWACCOUNTDETAIL = new JButton("View account detail");
		PUTINORDER = new JButton("Input order");
		CHECKPRICEOFPRODUCT = new JButton("Check product price");
		EXIT = new JButton("Exit");

		VIEWACCOUNTDETAIL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getClientAccount();
			}
		});
		PUTINORDER.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createOrder();
			}
		});
		CHECKPRICEOFPRODUCT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				priceCheck();
			}
		});
		EXIT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logout();
			}
		});

	}

	public static Clientstate instance() {
		if (clientstate == null) {
			return clientstate = new Clientstate();
		} else {
			return clientstate;
		}
	}

	/*public String Utility.getToken(String prompt) {
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
	}*/

	private boolean yesOrNo(String prompt) {
		String more = Utility.getToken(prompt + " (Y|y)[es] or anything else for no");
		if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
			return false;
		}
		return true;
	}

	public int getNumber(String prompt) {
		do {
			try {
				String item = Utility.getToken(prompt);
				Integer num = Integer.valueOf(item);
				return num.intValue();
			} catch (NumberFormatException nfe) {
				System.out.println("Please input a number ");
			}
		} while (true);
	}

	/*public int getCommand() {
		do {
			try {
				int value = Integer.parseInt(Utility.getToken("Enter command:" + HELP + " for help"));
				if (value >= EXIT && value <= HELP) {
					return value;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Enter a number");
			}
		} while (true);
	}*/

	/*public void help() {
		System.out.println("Enter a number between 0 and 4 as explained below:");
		System.out.println(EXIT + " to Exit\n");
		System.out.println(VIEWACCOUNTDETAIL + " to View Account Details\n");
		System.out.println(PUTINORDER + " to Place an Order\n");
		System.out.println(CHECKPRICEOFPRODUCT + " to Check Price of Product");
		System.out.println(HELP + " for help");
	}*/

	/*public void process() {
		int command;
		help();
		while ((command = getCommand()) != EXIT) {
			switch (command) {
			case HELP:
				help();
				break;
			case VIEWACCOUNTDETAIL:
				getClientAccount();
				break;
			case PUTINORDER:
				createOrder();
				break;
			case CHECKPRICEOFPRODUCT:
				priceCheck();
				break;
			default:
				break;

			}
		}
		logout();
	}*/

	public void run() {
		//process();
		
		frame = WarehouseContext.instance().getFrame();
		frame.getContentPane().removeAll();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(this.VIEWACCOUNTDETAIL);
		frame.getContentPane().add(this.PUTINORDER);
		frame.getContentPane().add(this.CHECKPRICEOFPRODUCT);
		frame.getContentPane().add(this.EXIT);

		frame.setVisible(true);
		frame.paint(frame.getGraphics());
		
		
	}

	public void logout() {
		if ((WarehouseContext.instance()).getLogin() == WarehouseContext.IsClerk) { // stem.out.println("
																					// going
																					// to
																					// clerk
																					// \n
																					// ");
			(WarehouseContext.instance()).changeState(1); // exit with a code 1
		} else if (WarehouseContext.instance().getLogin() == WarehouseContext.IsManager) { // stem.out.println("
																							// going
																							// to
																							// login
																							// \n");
			(WarehouseContext.instance()).changeState(3); // exit with a code 2
		} else
			(WarehouseContext.instance()).changeState(2); // exit code 2,
															// indicates error
	}

	public void getClientAccount() {
		Client tempUserAccount = warehouse.searchMembership(WarehouseContext.instance().getUser());
		System.out.println(tempUserAccount);
	}

	private void createOrder() {
		String tempClient;
		tempClient = WarehouseContext.instance().getUser();

		if (warehouse.searchMembership(tempClient) != null) {
			processMatch(tempClient);
		} else {
			System.out.println("Couldn't find client to associate");
		}
	}

	private void processMatch(String tempClient2) {
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
			productStringId = Utility.getToken("Enter First id of product to be added to the list");
			tempProduct = warehouse.findProduct(productStringId);
			if (tempProduct != null) {

				tempQuantity = Integer.parseInt(Utility.getToken("Enter the quantity of that item: "));

				addItemsToOrder = createdOrder.insertlistedItem(tempProduct, tempQuantity);
				if (!addItemsToOrder) {
					System.out.println("Failed to add item to order");

				} else
					System.out.println("Added Item");

			} else {
				System.out.println("Could not find item");
			}

			tempString = Utility.getToken("Continue adding items? Y to continue");
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

	private void priceCheck() {
		String productStringId;
		productStringId = Utility.getToken("Enter First id of product to search for");
		warehouse.priceCheck(productStringId);
	}

}

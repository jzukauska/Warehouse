import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
public class Clerkstate extends WarState {
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Warehouse warehouse;
	private WarehouseContext context;


	private static Clerkstate instance;
	
	//gui
	  private JFrame frame;
	
	  //
	  private AbstractButton	
	      addClient		
		, addProducts  		                          
		, showClients     
		, showProducts    
		, showManufacturers                          
		, recieveOrder   
		, recievePayment  
		, showWaitlistedOrders                         
		, makeOrderForClient
		, makeClient    
		, loadDatabase 
		, logout;
	 
	  
	 
	  
	
	/*private static final int EXIT = 0;
	 * private static final int addClient = 1;
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
	private static final int HELP = 99;*/




	


	private Clerkstate() {
		super();
		warehouse = Warehouse.instance();
		//context = LibContext.instance();
		
		
	    addClient        = new JButton("Add client");
		  addProducts         = new JButton("Add products");
		  showClients         = new JButton("Show Clients");
		  showProducts        = new JButton("Show Products");
		  showManufacturers   = new JButton("Show Manufacturers");
		  recieveOrder        = new JButton("Recieve Order");
		  recievePayment      = new JButton("Recieve Payment");
		  showWaitlistedOrders= new JButton(" Show waitlisted Orders");
		  makeOrderForClient  = new JButton("Make Order For Client");
		  makeClient          = new JButton("Make Client");
		  loadDatabase        = new JButton("Load");
		  logout              = new JButton("Logout");
		  
		  addClient           .addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {addClient();	 	} });
		  addProducts         .addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {addProducts();	 	} });
		  showClients         .addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {showClients();	 	} });
		  showProducts        .addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {showProducts();	 	} });
		  showManufacturers   .addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {showManufacturers();	 	} });
		  recieveOrder        .addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {WarehouseContext.instance().changeState(6);	 	} });
		  recievePayment      .addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {recievePayment();	 	} });
		  showWaitlistedOrders.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {showWaitlistedOrders();	 	} });
		  makeOrderForClient  .addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {WarehouseContext.instance().changeState(5);	 	} });
		  makeClient          .addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {makeClient();	 	} });
		  loadDatabase        .addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {loadDatabase();	 	} });
		  logout              .addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {logout();} });
		  
		  
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
				JOptionPane.showMessageDialog(frame,prompt);
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
				JOptionPane.showMessageDialog(frame,"Please input a number ");
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
				JOptionPane.showMessageDialog(frame,"Enter a number");
			}
		} while (true);
	}
*/
	public void help() {
		JOptionPane.showMessageDialog(frame,"Enter a number between 0 and 99 as explained below:");
		//JOptionPane.showMessageDialog(frame,EXIT + " to exit");
		JOptionPane.showMessageDialog(frame,addClient + " to add client");
		JOptionPane.showMessageDialog(frame,addProducts + " to add products");
		

		JOptionPane.showMessageDialog(frame,showClients + " to show clients");
		JOptionPane.showMessageDialog(frame,showProducts + " to show products");
		JOptionPane.showMessageDialog(frame,showManufacturers + " to show manufacturers");
		

		JOptionPane.showMessageDialog(frame,recieveOrder + " to recieve order");
		JOptionPane.showMessageDialog(frame,recievePayment + " to recieve payment");
		JOptionPane.showMessageDialog(frame,showWaitlistedOrders + " to show waitlisted orders");
		

		JOptionPane.showMessageDialog(frame,makeOrderForClient + " to create an order for a client");

		JOptionPane.showMessageDialog(frame,makeClient + " to change to client menu");
		JOptionPane.showMessageDialog(frame,loadDatabase + " to load database");
		

		//JOptionPane.showMessageDialog(frame,HELP + " to display help menu");
	}



	public void usermenu()
	{
		String userID = Utility.getToken("Please input the user id: ");
		if (Warehouse.instance().searchMembership(userID) != null){
			(WarehouseContext.instance()).setUser(userID);      
			(WarehouseContext.instance()).changeState(1);
		}
		else 
			JOptionPane.showMessageDialog(frame,"Invalid user id."); 
	}

	public void logout()
	{
		 if (WarehouseContext.instance().getLogin() == WarehouseContext.IsManager) {
		      WarehouseContext.instance().changeState(3);
		    } else if ((WarehouseContext.instance()).getLogin() == WarehouseContext.IsClerk) {
		      WarehouseContext.instance().changeState(0); // exit with a code 0
		    } else {
		      WarehouseContext.instance().changeState(2); // exit code 2, indicates error
		    }
	}


	/*public void process() {
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
				WarehouseContext.instance().changeState(6);
				break;
			case recievePayment:
				recievePayment();
				break;
			case showWaitlistedOrders:
				showWaitlistedOrders();
				break;
			case makeOrderForClient:
				WarehouseContext.instance().changeState(5);
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
	}*/
	private void addClient() {
		String name = Utility.getToken("Enter client name");
		Client result;
		result = warehouse.addClient(name);
		if (result == null) {
			JOptionPane.showMessageDialog(frame,"Failed to add client, canceling input");
		}
		JOptionPane.showMessageDialog(frame,"Added client [" + result + "]");
	}

	private void addProducts() {
		String name = Utility.getToken("Enter product name");
		int quantity = Integer.parseInt(Utility.getToken("Enter Product quantity"));
		double cost = Double.parseDouble(Utility.getToken("Enter price for item"));
		Product result;

		result = warehouse.addProduct(name, quantity, cost);
		if (result == null) {
			JOptionPane.showMessageDialog(frame,"Could not add product");
		}
		JOptionPane.showMessageDialog(frame,"Added product [" + result + "]");
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
		productName = Utility.getToken("Enter Product id to recieve");

		if (warehouse.findProduct(productName) != null) {
			String productQuantity = Utility.getToken("Enter amount to receive");
			warehouse.recieveProduct(productName, Integer.parseInt(productQuantity), reader);

		} else {
			JOptionPane.showMessageDialog(frame,"Couldn't find product");

		}
	}

	private void recievePayment() {
		double payment;
		String clientName;

		clientName = Utility.getToken("Enter client id to make payment for");

		payment = Double.parseDouble(Utility.getToken("Enter amount to be payed"));

		warehouse.acceptClientPayment(clientName, payment);
	}

	private void showWaitlistedOrders() {
		String productId;

		productId = Utility.getToken("Enter product id to show waitlisted items for");
		Product product = warehouse.findProduct(productId);

		if (product != null)
			warehouse.showWaitlistForProduct(product);

		else
			JOptionPane.showMessageDialog(frame,"Coudldn't find product with id: " + product);

	}


	private void makeClient() {
		String clientID = Utility.getToken("Please input the client id: ");
	    if (warehouse.searchMembership(clientID) != null) {
	      (WarehouseContext.instance()).setUser(clientID);
	      (WarehouseContext.instance()).changeState(1);
	    } else
	      JOptionPane.showMessageDialog(frame,"Invalid client id.");
	}

	private void loadDatabase() {
		WarehouseContext.instance().load();
		
	}

	public void run() {
		//process();
		
		while (!Securitystate.instance().isLocked()){
			System.out.println(!Securitystate.instance().isLocked());
			WarehouseContext.instance().changeState(4);
		}
		
		if(!Securitystate.instance().isLocked()){
		Securitystate.instance().reLock();
		}
		System.out.println(!Securitystate.instance().isLocked());
		frame = WarehouseContext.instance().getFrame();
		   frame.getContentPane().removeAll();
		   frame.getContentPane().setLayout(new FlowLayout());
		   frame.getContentPane().add(this.addClient           );
		   frame.getContentPane().add(this.addProducts         );
		   frame.getContentPane().add(this.showClients         );
		   frame.getContentPane().add(this.showProducts        );
		   frame.getContentPane().add(this.showManufacturers   );
		   frame.getContentPane().add(this.recieveOrder        );
		   frame.getContentPane().add(this.recievePayment      );
		   frame.getContentPane().add(this.showWaitlistedOrders);
		   frame.getContentPane().add(this.makeOrderForClient  );
		   frame.getContentPane().add(this.makeClient          );
		   frame.getContentPane().add(this.loadDatabase        );
		   frame.getContentPane().add(this.logout);
		   frame.setVisible(true);
		   frame.paint(frame.getGraphics()); 
		
	}
}

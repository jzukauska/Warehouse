import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/**
 * 
 * @author Joshua Wilford
 * @author Jacob Zukauska
 */
public class OrderState extends WarState{

	private static OrderState instance;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Warehouse warehouse;
	
	
	private OrderState(){
		super();
		warehouse = Warehouse.instance();
	}
	
	@Override
	public void run() {
		makeOrderForClient();
		WarehouseContext.instance().changeState(0);
	}
	
	public static OrderState instance(){
		if(instance == null){
			instance = new OrderState();
		}
		return instance;
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
	
	private void makeOrderForClient() {
		String tempClient;
		tempClient = getToken("Enter client id to create order for ");

		if (warehouse.searchMembership(tempClient) != null) {
			processMatch(tempClient);
		}else{
			System.out.println("Couldn't find client to associate");
		}
	}
	
}

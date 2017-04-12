import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author Joshua Wilford
 * @author Jacob Zukauska
 */


public class ReceiveOrderState extends WarState {

	private static ReceiveOrderState instance;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Warehouse warehouse;
	
	public ReceiveOrderState(){
		super();
		warehouse = Warehouse.instance();
	}
	
	@Override
	public void run() {
		recieveOrder();
		WarehouseContext.instance().changeState(0);
	}
	
	public static ReceiveOrderState instance(){
		if(instance == null){
			instance = new ReceiveOrderState();
		}
		return instance;
	}
	
	private void recieveOrder() {
		String productName;
		productName = Utility.getToken("Enter Product id to recieve");

		if (warehouse.findProduct(productName) != null) {
			String productQuantity = Utility.getToken("Enter amount to receive");
			warehouse.recieveProduct(productName, Integer.parseInt(productQuantity), reader);

		} else {
			System.out.println("Couldn't find product");

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
}

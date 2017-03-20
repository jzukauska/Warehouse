//Class by Jacob Zukauska




import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;



public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String Order_STRING = "o";
	private List<OrderLineItem> orderItems = new LinkedList<OrderLineItem>();

	private List<OrderLineItem> waitlistItems = new LinkedList<OrderLineItem>();

	private String id;

	private Calendar date;
	private String clientReferenceId;

	public Order() {

		this.id = Order_STRING + (OrderIdServer.instance()).getId();
		date = new GregorianCalendar();
		date.setTimeInMillis(System.currentTimeMillis());

	}
	// TODO Auto-generated constructor stub

	public void createWaitList() {

		Iterator it = getOrderProducts();

		while (it.hasNext()) {
			OrderLineItem orderItem = (OrderLineItem) it.next();
			int oldProductQuantity = orderItem.product.getQuantity();

			if (orderItem.product.getQuantity() >= orderItem.quantity) {
				orderItem.product.setQuantity(orderItem.product.getQuantity() - orderItem.quantity);

			}

			else if (orderItem.product.getQuantity() < orderItem.quantity) {
				// how much we are requesting - current stock
				int tempQuantityFornewWaitlist = orderItem.quantity - orderItem.product.getQuantity();
				// assign to new order waitlist item
				OrderLineItem newWaitListedItem = new OrderLineItem(orderItem.product, tempQuantityFornewWaitlist);
				waitlistItems.add(newWaitListedItem);

				orderItem.quantity = oldProductQuantity;
				orderItem.product.setQuantity(0);

				// System.out.println("waitlisted item: "+ newWaitListedItem+ "
				// waitlistItems: " + waitlistItems + " order items quantity:" +
				// orderItem.quantity);

			}
		}
	}

	public void findWaitListWithProduct(Product product) {
		Iterator iterate = waitlistItems.iterator();

		while (iterate.hasNext()) {
			OrderLineItem object = (OrderLineItem) iterate.next();

			if (object.product.equals(product) && object.quantity > 0) {
				System.out.println("Order " + id + " is waiting for " + object.quantity + ": " + product.getName());

			}

		}

	}

	public String getClientReferenceId() {
		return clientReferenceId;
	}

	public String getId() {
		return id;
	}

	public Iterator getOrderProducts() {
		return orderItems.iterator();
	}

	public double getTotals() {
		Iterator it = orderItems.iterator();
		double total = 0.00;

		while (it.hasNext()) {
			OrderLineItem orderLine = (OrderLineItem) it.next();
			total += orderLine.product.getCost() * orderLine.quantity;
		}
		System.out.println("Totals are: " + total);
		return total;
	}

	public List<OrderLineItem> getWaitlistItems() {
		return waitlistItems;
	}

	public Iterator getWaitListIterator() {

		return waitlistItems.iterator();
	}

	public boolean insertlistedItem(Product product, int amount) {

		return orderItems.add(new OrderLineItem(product, amount));
	}

	public String modifyWaitList(Product product, int i) {
		for (Iterator iterator = waitlistItems.iterator(); iterator.hasNext();) {
			OrderLineItem orderLineItem = (OrderLineItem) iterator.next();

			if (orderLineItem.product.equals(product) && orderLineItem.product.getQuantity() < product.getQuantity()) {

				orderLineItem.product.setQuantity(0);
				return clientReferenceId;
			}

		}
		return null;

	}

	public void setClientReferenceId(String clientReferenceId) {
		this.clientReferenceId = clientReferenceId;
		if (this.clientReferenceId == clientReferenceId) {

		}
	}

	@Override
	public String toString() {
		return "Id=" + id + "\nOrderItems=" + orderItems + "\n waitlistItems=" + waitlistItems + "\n date="
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date.getTimeInMillis())
				+ "\n clientReferenceId=" + clientReferenceId + "\n\n";
	}

}

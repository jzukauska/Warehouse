//Class by Jacob Zukauska



import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;



public class OrderList implements Serializable {
	private static final long serialVersionUID = 1L;
	private static OrderList orderlist;

	public static OrderList instance() {
		if (orderlist == null) {
			return (orderlist = new OrderList());
		} else {
			return orderlist;
		}
	}

	private List<Order> orders = new LinkedList<Order>();

	public Order findParticularOrder(String potentialId) {

		Iterator it = orders.iterator();

		while (it.hasNext()) {

			Order order = (Order) it.next();
			if (order.getId() == potentialId)
				return order;

		}
		return null;

	}

	public Iterator getOrder() {
		return orders.iterator();
	}

	public boolean insertOrderNode(Order order) {
		return orders.add(order);
	}

	/*
	 * public double getOrderTotal(String orderId){ Order order =
	 * findParticularOrder(orderId); if(order !=null){ return order.getTotals();
	 * System.out.println("Debited" + orderId); } else
	 * System.out.println("Couldn't Debit  " + orderId);
	 * 
	 * }
	 */

	public void outPutWaitListForProduct(Product product) {
		Iterator orderIterator = orders.iterator();

		while (orderIterator.hasNext()) {
			Order orderwaitlistItems = (Order) orderIterator.next();

			orderwaitlistItems.findWaitListWithProduct(product);

		}
	}

	private void readObject(java.io.ObjectInputStream input) {
		try {
			if (orderlist != null) {
				return;

			} else {
				input.defaultReadObject();

				if (orderlist == null) {
					orderlist = (OrderList) input.readObject();

				} else {
					input.readObject();
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "OrderList \n\n" + orders;
	}

	private void writeObject(java.io.ObjectOutputStream output) {
		try {
			output.defaultWriteObject();
			output.writeObject(orderlist);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/*
	 * public void administerWaitlistedProducts(Product product, int i) {
	 * Iterator orderIterator = orders.iterator();
	 * 
	 * while (orderIterator.hasNext()) { String clientToCharge; Order order =
	 * (Order) orderIterator.next(); clientToCharge =
	 * order.modifyWaitList(product,i);
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * }
	 */

}
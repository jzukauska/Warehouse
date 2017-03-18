//Class by Jacob Zukauska, Josh Wilford, Ryan Shaughnessy 



import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;



public class Inventory implements Serializable {
	private static final long serialVersionUID = 1L;

	private static Inventory inventory;

	public static Inventory instance() {
		if (inventory == null) {
			return (inventory = new Inventory());
		} else {
			return inventory;
		}
	}

	private List<Product> products = new LinkedList<Product>();

	private Inventory() {
	}

	public Product find(String productId) {
		Iterator allProducts = products.iterator();
		while (allProducts.hasNext()) {
			Product product = (Product) allProducts.next();
			if (product.getId().equals(productId)) {
				return product;
			}
		}

		return null;
	}

	public Iterator getProducts() {
		return products.iterator();
	}

	public boolean insertProduct(Product product) {
		products.add(product);
		return true;
	}

	private void readObject(java.io.ObjectInputStream input) {
		try {
			if (inventory != null) {
				return;
			} else {
				input.defaultReadObject();
				if (inventory == null) {
					inventory = (Inventory) input.readObject();
				} else {
					input.readObject();
				}
			}
		} catch (IOException ioe) {
			System.out.println("in Inventory readObject \n" + ioe);
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return products.toString();
	}

	private void writeObject(java.io.ObjectOutputStream output) {
		try {
			output.defaultWriteObject();
			output.writeObject(inventory);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	/*
	 * public void recieveNewProduct(String productName, int i) { Product
	 * product = find(productName); try { product.setQuantity(i +
	 * product.getQuantity());
	 * 
	 * 
	 * } catch (Exception e) { System.out.
	 * println("Error setting quantity: The quantity wasn't a proper number");
	 * 
	 * 
	 * // TODO: handle exception }
	 */

}

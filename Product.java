//Class by  Ryan Shaughnessy 



import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;



public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String PRODUCT_STRING = "p";
	private String name;
	private String id;
	private int quantity;

	private double cost;

	private List<String> manufacturerIds;

	public Product(String name, int quantity, double cost) {
		this.name = name;
		this.quantity = quantity;
		this.setCost(cost);
		this.manufacturerIds = new LinkedList<String>();
		this.id = PRODUCT_STRING + (ProductIdServer.instance()).getId();

	}

	public boolean addManufacturer(String manid) {
		return manufacturerIds.add(manid);
	}

	public double getCost() {
		return cost;
	}

	public String getId() {
		return id;
	}

	public int getManufacturersCount() {
		return manufacturerIds.size();
	}

	public Iterator getManufacturersIds() {
		return manufacturerIds.iterator();
	}

	public String getName() {
		return name;
	}

	public int getQuantity() {
		return quantity;
	}

	public boolean removeManufacturer(String manid) {
		return manufacturerIds.remove(manid);
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Id:" + id + " name:" + name + " manufacturer:" + manufacturerIds + " Quantity " + this.quantity;
	}
}

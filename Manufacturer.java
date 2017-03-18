//Class byJosh Wilford




import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;



public class Manufacturer implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String manufacturerString = "m";
	private String name;
	private String id;

	private List<String> productIds;

	public Manufacturer(String name) {
		this.name = name;
		this.id = manufacturerString + (ManufacturerIdServer.instance()).getId();
		this.productIds = new LinkedList<String>();
	}

	public boolean addProduct(String productId) {
		return productIds.add(productId);
	}

	public boolean equals(String id) {
		return this.id.equals(id);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getProductCount() {
		return productIds.size();
	}

	public Iterator getProductIds() {
		return productIds.iterator();
	}

	public boolean removeProduct(String productId) {
		return productIds.remove(productId);
	}

	public void setName(String newName) {
		name = newName;
	}

	@Override
	public String toString() {
		return "Id:" + id + " name:" + name + " products:" + productIds;
	}
}

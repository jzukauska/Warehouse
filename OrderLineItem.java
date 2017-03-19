//Class by Jacob Zukauska




import java.io.Serializable;

public class OrderLineItem implements Serializable {

	private static final long serialVersionUID = 1L;
	Product product;
	int quantity;

	public OrderLineItem(Product product, int quantity) {

		this.product = product;
		this.quantity = quantity;
	}

	@Override
	public String toString() {

		return this.quantity + ": " + product.getName();
	}

}

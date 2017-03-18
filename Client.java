//Class byJosh Wilford


import java.io.Serializable;



public class Client implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String CLIENT_STRING = "c";
	private String name;
	private String id;
	private double invoicedAmount = 0.00;

	public Client(String name) {
		this.name = name;
		this.id = CLIENT_STRING + (ClientIdServer.instance()).getId();
	}

	public void creditInvoice(double money) {
		if (invoicedAmount > 0.000) {
			invoicedAmount -= money;
		} else {
			System.out.println("No invoiced amount to credit");
		}

	}

	public void debitInvoice(double money) {

		this.invoicedAmount += money;

	}

	public boolean equal(String id) {
		return this.id.equals(id);
	}

	public String getId() {
		return id;
	}

	public double getInvoicedAmount() {
		return invoicedAmount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Client [name=" + name + ", id=" + id + ", invoicedAmount=" + invoicedAmount + "]";
	}
}

//Class by Jacob Zukauska, Josh Wilford, Ryan Shaughnessy 



import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;



public class ClientList implements Serializable {
	private static final long serialVersionUID = 1L;
	private static ClientList clientList;

	public static ClientList instance() {
		if (clientList == null) {
			return (clientList = new ClientList());
		} else {
			return clientList;
		}
	}

	private List<Client> clients = new LinkedList<Client>();

	public void creditClient(String id, double amount) {

		Client client = findParticularClient(id);
		if (client != null) {
			client.creditInvoice(amount);
			System.out.println("Credited" + id);
		} else
			System.out.println("Couldn't credit " + id);

	}

	public void debitClient(String id, double amount) {

		Client client = findParticularClient(id);

		if (client != null) {
			client.debitInvoice(amount);
			System.out.println("Debited: " + id + " for " + amount);
		} else
			System.out.println("Couldn't Debit  " + id);

	}

	public Client findParticularClient(String potentialId) {

		Iterator it = clients.iterator();

		while (it.hasNext()) {
			Client client = (Client) it.next();
			if (client.equal(potentialId))
				return client;

		}
		return null;

	}

	public Iterator getClients() {
		return clients.iterator();
	}

	public boolean insertClientNode(Client client) {
		return clients.add(client);
	}

	public void listOutstandingBalances() {
		Iterator it = clients.iterator();

		while (it.hasNext()) {
			Client client = (Client) it.next();

			if (client.getInvoicedAmount() > 0) {
				System.out.println(
						"Client " + client.getId() + " has a outstanding balance of: " + client.getInvoicedAmount());

			}
		}

	}

	private void readObject(java.io.ObjectInputStream input) {
		try {
			if (clientList != null) {
				return;

			} else {
				input.defaultReadObject();

				if (clientList == null) {
					clientList = (ClientList) input.readObject();

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
		return clients.toString();
	}

	private void writeObject(java.io.ObjectOutputStream output) {
		try {
			output.defaultWriteObject();
			output.writeObject(clientList);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
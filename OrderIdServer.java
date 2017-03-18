//Class by Jacob Zukauska, Josh Wilford, Ryan Shaughnessy 



import java.io.*;

public class OrderIdServer implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idCounter;
	private static OrderIdServer server;

	private OrderIdServer() {
		idCounter = 1;
	}

	public int getId() {
		return idCounter++;
	}

	@Override
	public String toString() {
		return ("IdServer" + idCounter);
	}

	// Id server singleton instantiation
	public static OrderIdServer instance() {
		if (server == null) {
			return (server = new OrderIdServer());
		} else {
			return server;
		}
	}

	public static void retrieve(ObjectInputStream input) {
		try {
			server = (OrderIdServer) input.readObject();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception cnfe) {
			cnfe.printStackTrace();
		}
	}

	private void writeObject(java.io.ObjectOutputStream output) throws IOException {
		try {
			output.defaultWriteObject();
			output.writeObject(server);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void readObject(java.io.ObjectInputStream input) throws IOException, ClassNotFoundException {
		try {
			input.defaultReadObject();
			if (server == null) {
				server = (OrderIdServer) input.readObject();
			} else {
				input.readObject();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}

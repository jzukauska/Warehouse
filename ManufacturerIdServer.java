//Class by Jacob Zukauska, Josh Wilford, Ryan Shaughnessy 



import java.io.*;

public class ManufacturerIdServer implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idCounter;
	private static ManufacturerIdServer server;

	private ManufacturerIdServer() {
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
	public static ManufacturerIdServer instance() {
		if (server == null) {
			return (server = new ManufacturerIdServer());
		} else {
			return server;
		}
	}

	public static void retrieve(ObjectInputStream input) {
		try {
			server = (ManufacturerIdServer) input.readObject();
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
				server = (ManufacturerIdServer) input.readObject();
			} else {
				input.readObject();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}

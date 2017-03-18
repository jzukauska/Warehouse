//Class by Jacob Zukauska, Josh Wilford, Ryan Shaughnessy 



import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;



public class ManufacturerList implements Serializable {
	private static final long serialVersionUID = 1L;

	private static ManufacturerList manufacturerList;

	public static ManufacturerList instance() {
		if (manufacturerList == null) {
			return (manufacturerList = new ManufacturerList());
		} else {
			return manufacturerList;
		}
	}

	private List<Manufacturer> manufacturers = new LinkedList<Manufacturer>();

	private ManufacturerList() {
	}

	public boolean addManufacturer(Manufacturer manufacturer) {
		return manufacturers.add(manufacturer);
	}

	public Iterator getManufacturers() {
		return manufacturers.iterator();
	}

	private void readObject(java.io.ObjectInputStream input) {
		try {
			if (manufacturerList != null) {
				return;
			} else {
				input.defaultReadObject();
				if (manufacturerList == null) {
					manufacturerList = (ManufacturerList) input.readObject();
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

	public Manufacturer search(String manid) {
		Iterator allman = manufacturers.iterator();
		while (allman.hasNext()) {
			Manufacturer manufacturer = (Manufacturer) allman.next();
			if (manufacturer.getId().equals(manid)) {
				return manufacturer;
			}
		}
		return null;
	}

	private void writeObject(java.io.ObjectOutputStream output) {
		try {
			output.defaultWriteObject();
			output.writeObject(manufacturerList);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
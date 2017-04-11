import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Utility {

	private Utility() {
		// TODO Auto-generated constructor stub
		
		
	}
	public static String getToken(String s){
		String temp;
		temp = JOptionPane.showInputDialog(s);
			return temp;
			
			
			
		}
	
	
	public static void collectFrame(JFrame frame){
		frame = WarehouseContext.instance().getFrame();
		Component[] components = frame.getContentPane().getComponents();
		
		
		
		   frame.getContentPane().removeAll();
		   frame.getContentPane().setLayout(new FlowLayout());
		   
		   frame.setVisible(true);
		   frame.paint(frame.getGraphics()); 
		
		
		
	}
}

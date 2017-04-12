 import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Securitystate extends WarState{
	
	  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));  
	  private static boolean loginSuccess = false;
	  private static Securitystate instance;
	  
	  
	  private Securitystate() {
	      super();
	     // context = LibContext.instance();
	  }

	  public static Securitystate instance() {
	    if (instance == null) {
	      instance = new Securitystate();
	    }
	    return instance;
	  }

	  

	  public String getToken(String prompt) {
	    do {
	      try {
	        System.out.println(prompt);
	        String line = reader.readLine();
	        StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
	        if (tokenizer.hasMoreTokens()) {
	          return tokenizer.nextToken();
	        }
	      } catch (IOException ioe) {
	        System.exit(0);
	      }
	    } while (true);
	  }
	 
	
	public void process() {
	    String userName = "";
	    String password = "" ;
	    
	    if(WarehouseContext.instance().getLogin() == 3 ){

	    userName = Utility.getToken("Please input manager user name (manager)");
	    password = Utility.getToken("Enter the manager password (manager)");
	  
	    	if (userName.equals("manager") && password.equals("manager")) {
	    		loginSuccess = true;
	    	}
	    	(WarehouseContext.instance()).changeState(3);
	    }
	    
	    if(WarehouseContext.instance().getLogin() == 0 ){
		    
		    userName = Utility.getToken("Please input manager user name (salesclerk)");
		    password = Utility.getToken("Enter the clerk password (salesclerk)");
		    System.out.println(userName);
			   System.out.println(password);
		    	if (userName.equals("salesclerk") && password.equals("salesclerk")) {
		    		loginSuccess = true;
		    	}
		    	WarehouseContext.instance().changeState(0);
		    }
	    
	  }
	  public boolean isLocked(){
		  return this.loginSuccess;		
		  
		  
	  }
	  
	  public void reLock(){
		  
		  this.loginSuccess = false;
	  }
	  public void run() {
	    process();
	    
	  }
}

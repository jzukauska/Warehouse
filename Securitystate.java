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
	    System.out.println("As long as the input is not null you can log in");
	    userName = getToken("Please input manager user name (anything works)");
	    password = getToken("Enter the manager password (anything works)");
	   
	    if (userName != "" && password != "") {
			loginSuccess = true;
		}

	    
	    (WarehouseContext.instance()).changeState(3);
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


import java.io.*;
import java.util.ArrayList;

public class auth {

public static int arg_count;
	 
public static void main(String[] args) throws Exception {

	//Check to see if the arguments are empty 
	arg_count = args.length;
	if(arg_count == 0) {
		System.out.println("Error: No Input");
		System.exit(0);
	}
	String function = args[0];
	
	
	//addUser pathway
	if(function.equals("AddUser")) {
		if(arg_count < 3 || arg_count > 3) {
			System.out.println("Error: invalid # of args for " + function);
			System.exit(0);
		}
		
		if(!args[1].isEmpty() && (args[2] != null) && arg_count == 3) {
			String username = args[1];
			String password = args[2];
			
			addUser(username, password);
		}
		else if(args[1].isEmpty()) {
			System.out.println("Error: username missing");
		}

	}//authenticate pathway
	else if(function.equals("Authenticate")) {
		
		if(arg_count < 3 || arg_count > 3) {
			System.out.println("Error: invalid # of args for " + function);
			System.exit(0);
		}
		
		if(!args[1].isEmpty() && (args[2] != null) && arg_count == 3) {
			String username = args[1];
			String password = args[2];
			
			authenticate(username, password);
			
		}
		
		
	}//SetDomain pathway
	else if(function.equals("SetDomain")) {
		
		if(arg_count < 3 || arg_count > 3) {
			System.out.println("Error: invalid # of args for " + function);
			System.exit(0);
		}
		
		if(!args[1].isEmpty() && (!args[2].isEmpty() && args[2] != null ) && arg_count == 3) {
			String username = args[1];
			String domain = args[2];
			
			setDomain(username, domain);
		}
		else {
			System.out.println("Error: missing domain");
		}
	}//DomainInfo pathway
	else if(function.equals("DomainInfo")) {
		if(arg_count < 2 || arg_count > 2) {
			System.out.println("Error: invalid # of args for " + function);
			System.exit(0);
		}
		
		if((!args[1].isEmpty() && args[1] != null ) && arg_count == 2) {
		String domain = args[1];	
		domainInfo(domain);
		}
		else {
			System.out.println("Error: missing domain");
		}
		
	}//SetType pathway
	else if(function.equals("SetType")) {
		
		if(arg_count < 3 || arg_count > 3) {
			System.out.println("Error: invalid # of args for " + function);
			System.exit(0);
		}
		
		if((!args[1].isEmpty()) && (!args[2].isEmpty()) && arg_count == 3) {
			String object = args[1];
			String type = args[2];
			
			setType(object, type);
		}
		else {
			System.out.println("Error");
		}
	}//TypeInfo pathway
	else if(function.equals("TypeInfo")) {
		if(arg_count < 2 || arg_count > 2) {
			System.out.println("Error: invalid # of args for " + function);
			System.exit(0);
		}
		
		if((!args[1].isEmpty() && args[1] != null ) && arg_count == 2) {
		String type = args[1];	
		typeInfo(type);
		}
		else {
			System.out.println("Error: missing type");
		}
		
	}
	else if(function.equals("AddAccess")) {
		if(arg_count < 4 || arg_count > 4) {
			System.out.println("Error: invalid # of args for " + function);
			System.exit(0);
		}
		
		if((!args[1].isEmpty() && args[1] != null ) && (!args[2].isEmpty() && args[2] != null ) && (!args[3].isEmpty() && args[3] != null ) && arg_count == 4) {
			String operation = args[1];
			String domain = args[2];
			String type = args[3];
			
			addAccess(operation, domain, type);
		}
		else if (args[1] == null || args[1].isEmpty()) {
			System.out.println("Error: missing operation");
		}
		else if(args[2] == null || args[2].isEmpty()) {
			System.out.println("Error: missing domain");
		}
		else if(args[3] == null || args[3].isEmpty()) {
			System.out.println("Error: missing type");
		}
		else {
			System.out.println("invalid # of args");
		}
	}
	else if(function.equals("CanAccess")) {
		if(arg_count < 4 || arg_count > 4) {
			System.out.println("Error: access denied");
			System.exit(0);
		}
		if((!args[1].isEmpty() && args[1] != null ) && (!args[2].isEmpty() && args[2] != null ) && (!args[3].isEmpty() && args[3] != null ) && arg_count == 4) {
			String operation = args[1];
			String user = args[2];
			String object = args[3];
			
			canAccess(operation, user, object);
		}
		else {
			System.out.println("Error: access denied");
		}
	}
	else {
		System.out.println("Error: invalid command "+ function);
	}
	
	

	} 
	
	public static void addUser(String username, String password) throws Exception {
		
		Boolean authUser = checkUser(username);
		
		if(authUser) {
			System.out.println("Error: user exists");
		}
		else {
			
			//We write the username and passcode to the credentials.txt file 
		writeCredentials(username, password);
		System.out.println("Success");
		
		}
		
	}
	
	public static void addAccess(String operation, String domain, String type) throws Exception {
		
		Boolean verDomain = verifyDomain(domain);
		 
		Boolean verType = verifyType(type);
		//If domain and type do not exists then add them 
		if(!verDomain) {
			//
			writeDomain("", domain);	
		}
		if (!verType){
			//set the Domain
			writeType("", type);
			}
		
		boolean exists = checkOperation(domain, operation, type);
		if(exists) {
			System.out.println("Operation Already Exists.");
		}
		else {
			 
			writeOperations(operation,domain,type);
			System.out.println("Success");
			//write to operations.txt
		}
		
		//Check to see if pattern exists if it does then return error if not write to operations.txt
		
		
		
	}
	
public static void canAccess(String operation, String user, String object) throws Exception {
		
		ArrayList<String> domains = grabDomains(user);
		ArrayList<String> types = grabTypes(object);

		
		if(domains.isEmpty() || types.isEmpty()) {
			System.out.println("1, Error: Access Denied");
			return;
		}
		//Use user to grab the names of the domains that the "user" is apart of 
	   // Then return an arraylist of Strings that contains all of the domains
	  //Use the same concept with object and type
		for(String d: domains) {
			for(String t: types) {
				
				
				Boolean canAccess = checkOperation(d, operation, t);
				
				if(canAccess) {
					
					System.out.println("Success");
					return;
				}
				
			}
		}
	
		
		System.out.println("2, Error: Access Denied");
	}
	
	public static void authenticate(String username, String password) throws Exception {
		
		Boolean authUser = checkUser(username);
		Boolean authPassword = checkPassword(password);
		if(!authUser) {
			System.out.println("Error: no such user");
			return;		
		}
		if(!authPassword) {
			System.out.println("Error: bad password");
			return;
		}
		
		System.out.println("Success");
	}
	
	public static void setDomain(String username, String domain) throws Exception {
		
		Boolean authUser = checkUser(username);
		//Checking if the user already belongs to the domain 
		Boolean authDomain = checkDomain(username, domain);
		if(authDomain) {
			System.out.println("Success");
			return;
		}
		
		if(authUser) {
			//set the Domain
			writeDomain(username, domain);
			System.out.println("Success");
		}
		else {
			System.out.println("Error: no such user");
		}
		
	}
	 
	public static Boolean checkUser(String username) throws Exception
    {
		 int len = username.length();
		 BufferedReader br = new BufferedReader(new FileReader("credentials.txt"));
		 
		 String st;
		 while ((st = br.readLine()) != null) {
			 
		     // System.out.println(st);
		      if(len > st.length()) {
		    	  continue;
		      }
		      if(username.equals(st.substring(0,len))) {
		    	  return true;
		      };
		 }
		 
    return false;
    }
	
	
	public static Boolean checkPassword(String password) throws Exception
    {
		 int len = password.length();
		 BufferedReader br = new BufferedReader(new FileReader("credentials.txt"));
		 
		 String st;
		 while ((st = br.readLine()) != null) {
			 
			 if(len > st.length()) {
				 continue;
				 
			 }
		     int start = st.length() - len;
		   
		      
		      if(password.equals(st.substring(start))) {
		    	  return true;
		      };
		 }
		 
    return false;
    }
	
	public static Boolean verifyDomain(String domain) throws Exception
    {
		 int len = domain.length();
		 //File file1 = new File("crendentials.txt");
		 BufferedReader br = new BufferedReader(new FileReader("domain.txt"));
		 
		 String st;
		 while ((st = br.readLine()) != null) {
			 int start = st.length() - len;
			 if(len > st.length()) {
				 continue;
			 }
		     else if(domain.equals(st.substring(start))) {
		    	  return true;
		      };
		 }
    return false;
    }
	
	public static Boolean verifyType(String type) throws Exception
    {
		 int len = type.length();
		 //File file1 = new File("crendentials.txt");
		 BufferedReader br = new BufferedReader(new FileReader("object_type.txt"));
		 
		 String st;
		 while ((st = br.readLine()) != null) {
			 
		     int start = st.length() - len;
		     if(len > st.length()) {
		    	 continue;
		     }
		     else if(type.equals(st.substring(start))) {
		    	  
		    	  return true;
		      };
		 }
	
    return false;
    }
	
	public static Boolean checkDomain(String username, String domain) throws Exception
    {
		 int len = username.length();
		 int len2 = domain.length();
		 //File file1 = new File("crendentials.txt");
		 BufferedReader br = new BufferedReader(new FileReader("domain.txt"));
		 
		 String st;
		 while ((st = br.readLine()) != null) {
			 
		     int start = st.length() - len2;
		     
		     if((len + len2) > st.length()) {
		    	 continue;
		     }
		      
		     if(st.length() == 0) {
		    	 break;
		     } 
		     else if(username.equals(st.substring(0,len)) && domain.equals(st.substring(start))) {
		    	  return true;
		      };
		 }
		 
    return false;
    }
	public static ArrayList<String> grabDomains(String username) throws Exception
    {
		ArrayList<String> domains = new ArrayList<String>();
		 int len = username.length();
		 int start = username.length() + 1;
		 
		 //File file1 = new File("crendentials.txt");
		 BufferedReader br = new BufferedReader(new FileReader("domain.txt"));
		 
		 String st;
		 while ((st = br.readLine()) != null) {
		      
		     if(len > st.length()) {
		    	 continue;
		     } 
		     else if(username.equals(st.substring(0,len))){
		    	  domains.add(st.substring(start));
		    	  
		      };
		 }
		 
    return domains;
    }
	
	public static ArrayList<String> grabTypes(String object) throws Exception
    {
		ArrayList<String> types = new ArrayList<String>();
		 int len = object.length();
		 int start = object.length() + 1;
		 
		 //File file1 = new File("crendentials.txt");
		 BufferedReader br = new BufferedReader(new FileReader("object_type.txt"));
		 
		 String st;
		 while ((st = br.readLine()) != null) {
		      
		     if(len > st.length()) {
		    	 continue;
		     } 
		     else if(object.equals(st.substring(0,len))){
		    	  types.add(st.substring(start));
		    	  
		      };
		 }
		 
    return types;
    }
	
	public static Boolean checkOperation(String domain, String operation, String type) throws Exception
    {
		 int len = domain.length();
		 int len3 = type.length();
		 
		 //File file1 = new File("crendentials.txt");
		 BufferedReader br = new BufferedReader(new FileReader("operations.txt"));
		 
		 String st;
		 while ((st = br.readLine()) != null) {
			 
		     int start = st.length() - len3;
		     
		     if(st.length() < len + operation.length() + len3) {
		    	 continue;
		     } 
		     else if(domain.equals(st.substring(0,len)) && operation.equals(st.substring(len + 1,start - 1)) && type.equals(st.substring(start))) {
		    	  return true;
		      };
		 }

    return false;
    }
	
	public static Boolean checkObject(String object) throws Exception
    {
		 int len = object.length();
		 //File file1 = new File("crendentials.txt");
		 BufferedReader br = new BufferedReader(new FileReader("object_type.txt"));
		 
		 String st;
		 while ((st = br.readLine()) != null) {
			 
			 if(len > st.length()) {
				 continue;
			 }
		     // System.out.println(st);
		      
		      if(object.equals(st.substring(0,len))) {
		    	  System.out.println("true");
		    	  return true;
		      };
		 }
		 
    return false;
    }
	
	public static Boolean checkType(String object, String type) throws Exception
    {
		 int len = object.length();
		 int len2 = type.length();
		 //File file1 = new File("crendentials.txt");
		 BufferedReader br = new BufferedReader(new FileReader("object_type.txt"));
		 
		 String st;
		 while ((st = br.readLine()) != null) {
			 if((len + len2) > st.length()) {
				 continue;
			 }
		     int start = st.length() - len2;
		      
		      if(object.equals(st.substring(0,len)) && type.equals(st.substring(start))) {
		    	  
		    	  return true;
		      };
		 }
		 
    return false;
    }
	
	
	
	public static void writeCredentials(String username, String password) throws IOException {
		
		try (FileWriter f = new FileWriter("credentials.txt", true); 
			BufferedWriter b = new BufferedWriter(f); 
			PrintWriter p = new PrintWriter(b);) { 
			p.println(username + " " + password);
			
			} 
		catch (IOException i) { 
				i.printStackTrace(); 
				}

		
	}
	
public static void writeDomain(String username, String domain) throws IOException {
		
		try (FileWriter f = new FileWriter("domain.txt", true); 
			BufferedWriter b = new BufferedWriter(f); 
			PrintWriter p = new PrintWriter(b);) { 
			p.println(username + " " + domain);
			
			} 
		catch (IOException i) { 
				i.printStackTrace(); 
				}
 
		
	}

public static void writeOperations(String operation, String domain, String type) throws IOException {
	
	try (FileWriter f = new FileWriter("operations.txt", true); 
		BufferedWriter b = new BufferedWriter(f); 
		PrintWriter p = new PrintWriter(b);) { 
		p.println(domain + " " + operation + " " + type);
		
		} 
	catch (IOException i) { 
			i.printStackTrace(); 
			}	
}

public static void writeType(String object, String type) throws IOException {
	
	try (FileWriter f = new FileWriter("object_type.txt", true); 
		BufferedWriter b = new BufferedWriter(f); 
		PrintWriter p = new PrintWriter(b);) { 
		p.println(object + " " + type);
		
		} 
	catch (IOException i) { 
			i.printStackTrace(); 
			}	
}


public static void domainInfo(String domain) throws IOException {
	 int len = domain.length();
	 //File file1 = new File("crendentials.txt");
	 BufferedReader br = new BufferedReader(new FileReader("domain.txt"));
	 
	 String st;
	 while ((st = br.readLine()) != null) {
		 
	     int start = st.length() - len;
	      
	      if (len > st.length()) {
	    	  continue;
	      }
	      
	      if(domain.equals(st.substring(start))) {
	    	  String user = st.substring(0, start - 1);
	    	  
	    	  if(!user.isEmpty()) {
	    	  System.out.println(user);
	    	  
	    	  }
	      }
	 }
}

public static void typeInfo(String type) throws IOException {
	 int len = type.length();
	 //File file1 = new File("crendentials.txt");
	 BufferedReader br = new BufferedReader(new FileReader("object_type.txt"));
	 
	 String st;
	 while ((st = br.readLine()) != null) {
		 
	     int start = st.length() - len;
	      if(len > st.length()) {
	    	  continue;
	      }
	      
	      if(type.equals(st.substring(start))) {
	    	  String object = st.substring(0, start - 1);
	    	  
	    	  if(!object.isEmpty()) {
	    	  System.out.println(object);
	    	  
	    	  }
	      }
	 }
} 

public static void setType(String object, String type) throws Exception {
	

	Boolean authType = checkType(object, type);
	if(authType) {
		System.out.println("Success");
		return;
	}
	else{
		//set the Domain
		writeType(object, type);
		System.out.println("Success");
	}
	
	
}
 
	

}


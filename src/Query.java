

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

/**
 * A runnable class to implement query. run it with command: "java -jar Query.jar DATA_PATH"
 * @author Chu Rong
 *
 */
public class Query {
	
	public static void main(String[] args) throws IOException, ParseException {
		QueryHandler queryHandler = new QueryHandler(args[0]);
	    Scanner scanner = new Scanner(System.in);
	    String query = scanner.nextLine();
	    String[] arguments = query.split(" ");
	    while(!arguments[0].equals("EXIT")){
	    	if(arguments[0].equals("QUERY")) {
		    	String response = queryHandler.handleQuery(arguments);
		    	System.out.println("CPU" + arguments[2] + " usage on " + arguments[1] + ":");
		    	System.out.println(response);
		    }
	    	query = scanner.nextLine();
	    	arguments = query.split(" ");
	    }	    	    
    	scanner.close();
    	System.exit(0);
	}

}

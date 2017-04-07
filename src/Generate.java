

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

/**
 * A runnable class that implement a simulator to generate logs for a day. 
 * To the class, pass in the directory name to store the logs. 
 * <p>
 * eg. "java -jar Generate.jar logs", in which "logs" is the directory name in the workspace to store all the logs.
 * after starting this program, follow the instruct to type in the number of servers, the number of CPUs per server 
 * and the date to log.
 * 
 * @author Chu Rong
 *
 */
public class Generate {
	
	public static void main(String[] args) {
	    Scanner scanner = new Scanner(System.in);
		System.out.println("This is a sminulator to generate logs for a certain number of servers during a day");
		System.out.println("Please enter the number of servers: ");
		String serverNumber = scanner.nextLine();
		int serverNum = Integer.parseInt(serverNumber);
		System.out.println("Please enter the number of CPUs per server: ");
		String CPUNumber = scanner.nextLine();
		int cpuNum = Integer.parseInt(CPUNumber);
		System.out.println("Please enter the date to log (formate: yyyy-MM-dd): ");
		String date = scanner.nextLine();
		scanner.close();
		System.out.println("The number of servers is: " + serverNumber + "; The number of CPUs per server is: "+ CPUNumber +"; The date is: " + date);
		System.out.println("Generating logs ...");
		LogGenerator logGenerator = new LogGenerator(args[0]);		
		try {
			logGenerator.simulate(serverNum, cpuNum, date);
		} catch (IOException e) {
			System.out.println("Please start the program again, and run it with a correct directory name!");
		} catch (ParseException e) {
			System.out.println("Please start the program again, and run it with a correct date!");
		}
		System.out.println("Done! logs are under the directory /" + args[0] + " in the workspace. Please go to the directory to see the randomly generated IP adresses!");
	}
}

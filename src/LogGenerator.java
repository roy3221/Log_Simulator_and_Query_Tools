

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * A simulator that can be used to generate logs for a specific number of servers.
 * <p>
 * Assume that each server will be logged per minute.
 * If you want to simulate logs for 1000 servers during a day (eg. 2014-10-31), you can pass in the date of that day as a string, "2014-10-31 ",
 * and the number of servers as an integer, 1000. 
 * <p>Then you will find the logs as text files in the /logs directory.
 * <p> To store the logs, partition the logs in the following way:
 *<p> a. each server(IP address) will have a folder in the logs folder;
 *<p> b. Each IP's folder will contain two .txt files for each cpu. 
 *<p> In this way, each .txt file will only have 1440 lines of logs, which helps with querying performance.
 * 			
 * @author Chu Rong
 *
 */
public class LogGenerator {
	
	private String dir;
	
	/**
	 * Initialize the LogGenerator with the directory name, where the logs will be stored.
	 * @param dir the directory name for storing logs.
	 */
	public LogGenerator(String dir) {
		this.dir = dir;
	}
	
	
	/**
	 * A method to generate random IP addresses for a specific number of server, all begin with 192.168.*.*.
	 * 
	 * @param serverNum the number of servers we are going to simulate
	 * @return A set of randomly generated IP addresses.
	 */	
	private Set<String> generateIP(int serverNum) {
		Set<String> serverIPs = new HashSet<>(); // use set instead of list to avoid duplicates
		Random r = new Random();
		String IP = "";
		while(serverIPs.size() < serverNum) {
			IP = 192 + "." + 168 + "." + r.nextInt(256) + "." + r.nextInt(256);
			serverIPs.add(IP);
		}
		return serverIPs;
	}
	
	/**
	 * A method that can be used to generate a random cpu usage.
	 * @return An integer that indicates a random cpu usage in %.
	 */
	private int generate_cpu_usage() {
		Random r = new Random();
		return r.nextInt(100);
	}
	
	/**
	 * 
	 * @param Date a date that indicate the day for simulation. in formate of: yyyy-mm-dd
	 * @return A list of time stamps per minute during the day.
	 * @throws ParseException If the date format is incorrect.
	 */
	private List<Long> generateTimestamp(String date) throws ParseException {
		List<Long> timestamps = new ArrayList<>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date tmpDate = format.parse(date + " 00:00:00");
		long timestamp = tmpDate.getTime()/1000; // convert ms to s;
		long tmpStamp = timestamp;
		while(tmpStamp < timestamp + 60*60*24) {
			timestamps.add(tmpStamp);
			tmpStamp += 60;
		}
		return timestamps;
	}
	
	/**
	 * A method that write logs for a single file, the file name is the "DATA_PATH/IP_ADDRESS/CPU_ID.txt".
	 * @param IP 
	 * @param cpu_id
	 * @param timestamps
	 * @throws IOException An exception happens if file name is incorrect.
	 */
	private void writeLogFile(String IP, int cpu_id, List<Long> timestamps) throws IOException {
		File filePath = new File(dir + "/"+ IP);
		filePath.mkdirs();
		File file = new File(filePath + "/" + String.valueOf(cpu_id) + ".txt");
		FileOutputStream outStream = new FileOutputStream(file);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outStream));
		for(long timestamp : timestamps) {
			bw.write(timestamp + " " + IP + " " + cpu_id + " " + generate_cpu_usage());
			bw.newLine();
		}
		bw.close();
	}
	
	public void simulate(int server_Num, int cpu_Num_Per_Server, String date) throws IOException, ParseException {		
		Set<String> serverIPs = generateIP(server_Num);
		List<Long> timestamps = generateTimestamp(date);
		for(String IP : serverIPs) {
			for(int i = 0; i < cpu_Num_Per_Server; i++) {
				writeLogFile(IP, i, timestamps);
			}
		}	
	}
}

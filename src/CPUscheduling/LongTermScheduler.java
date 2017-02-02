package CPUscheduling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;


// reads/loads processes from a file, creates the processes, adds them to the ready queue
public class LongTermScheduler {
	
	public ArrayList<Process> arrList;	// for loading processes into the system from the file and sorting them
	public Queue<Process>readyQueue;	// for adding loaded processes into a queue for execution
	/* Note that a priority queue could have been used here, but for demonstration purposes an ordinary FIFO queue 
	   is used because that is what the round robin algorithm uses. */
	ProcessCreator pc;
	
	public LongTermScheduler(String filePath){
		arrList = new ArrayList<Process>(); // array list dynamically resizes for allowing any number of processes to be loaded
		readyQueue = new Queue<Process>();
		
		loadProcesses(filePath);
		sortProcesses();
	}
	
	// read processes from the file and create them
	public void loadProcesses(String filePath){
		pc = new ProcessCreator();
		BufferedReader br;
		try{
			br = new BufferedReader(new FileReader(filePath));
			String line = br.readLine();
			while(line != null){
				String[] tokens = line.split(",");
				// create new process
				Process p = pc.createProcess(Integer.parseInt(tokens[0]), Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]));
				Simulator.logCreation(p);
				arrList.add(p);		
				line = br.readLine();
			}
			br.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}finally{}
	}
	
	
	// sort the processes in the file as they may not already be ordered by arrival time.
	public void sortProcesses(){
		arrList.sort(processCompare);
	}
	
	// insert all processes from the array list into the ready queue and return it
	public Queue<Process> getReadyQueue(){
		int size = arrList.size();
		for(int i = 0; i < size; i++){
			Process p = arrList.get(i);
			readyQueue.insert(p);	
		}
		return readyQueue;
	}
	
	public ArrayList<Process> getArrayList(){
		return arrList;
	}
	
	
	// comparator for sorting Processes in the array list
	public Comparator<Process> processCompare = new Comparator<Process>(){
		public int compare(Process p1, Process p2) {
			if(p1.arrivalTime < p2.arrivalTime)
				return -1;
			else if(p1.arrivalTime == p2.arrivalTime)
				return 0;
			else 
				return 1;
		}
	};
}



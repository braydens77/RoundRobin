package CPUscheduling;

import java.util.ArrayList;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class Simulator {

	public static MyClock clock;
	public static CPU cpu;
	public static ArrayList<Process>finishedProcesses;
	
	public static void main(String[]args){
		if(args[0] == null) 
			System.out.println("No arguments given!");
		String filePath = args[0];
		double timeQuantum = Double.parseDouble(args[1]);
		cpu = new CPU(timeQuantum);
		clock = new MyClock();
		
		LongTermScheduler lts = new LongTermScheduler(filePath);
		System.out.println("Processes loaded and created.");
		System.out.println("Allocating the ready queue...");
		Queue<Process>readyQueue = lts.getReadyQueue();
		
		System.out.println("Ready queue allocated. Sending for dispatch");
		ShortTermScheduler sts = new ShortTermScheduler(readyQueue, cpu);
		finishedProcesses = sts.getFinishedList();
		finishedProcesses.sort(lts.processCompare);
		calculateStats();
	}
	
	public static void calculateStats(){		
		System.out.println("Total context switches= " + cpu.numOfContextSwitches);
		/* inaccurate output for totalContextSwitchTime below 
		   System.out.println("Total context switch time: " + (NANOSECONDS.toMillis(cpu.totalContextSwitchTime)/100000) + "ms");
		*/
		double[]waitTimes = new double[finishedProcesses.size()];
		double[]turnaroundTimes = new double[finishedProcesses.size()];

		for(int i =0; i<finishedProcesses.size(); i++){
			Process p = finishedProcesses.get(i);
			long waitTime = NANOSECONDS.toMillis(p.finishTime - p.startTime);
			long turnaroundTime = waitTime + (long)p.burstTime;
			waitTimes[i] = waitTime;
			turnaroundTimes[i] = turnaroundTime;
			
			System.out.printf("pid=%-5d \twait time=%-5dms \tturnaround time=%-5dms %n", p.pid, waitTime, turnaroundTime);
		}
		// calculate average waiting time
		double waitSum = 0;
		for (int i =0; i < waitTimes.length; i++)
			waitSum+=waitTimes[i];
		double waitAvg = waitSum / waitTimes.length;
		
		// calculate average turnaround time
		double turnaroundSum = 0;
		for (int i =0; i < turnaroundTimes.length; i++)
			turnaroundSum+=turnaroundTimes[i];
		double turnaroundAvg = turnaroundSum / turnaroundTimes.length;
		
		System.out.printf("%nCPU utilization=\t%-14s%n", "100%");
		System.out.printf("Avg. wait time=\t\t%-10.3fms%n", waitAvg);
		System.out.printf("Avg. turnaround time=\t%-10.3fms%n",turnaroundAvg );		
		System.out.printf("Avg. throughput=\t%-10.3fms/process%n", turnaroundAvg/turnaroundTimes.length);
	}
	
	//------------------------------------------------------------------------
	// distinct logging methods for the console
	public static void logCreation(Process p){
		System.out.printf("Process created  \t\tpid=%-8d \ttime=%-30s %n", p.pid, clock.timeStamp());
		//System.out.println("Process created\t\t\tpID: " + p.pid + "\ttime: " + clock.timeStamp());
	}
	
	public static void logExecution(Process p){
		System.out.printf("Process executing  \t\tpid=%-8d \ttime=%-30s %n", p.pid, clock.timeStamp());
		//System.out.println("Process executing\t\tpid: " + p.pid + "\ttime: " + clock.timeStamp());
	}
	
	public static void logProcessExecutionComplete(Process p){
		System.out.printf("Process completed  \t\tpid=%-8d \ttime=%-30s %n", p.pid, clock.timeStamp());
		//System.out.println("Process Completed\t\tpid: " + p.pid + "\ttime: " + clock.timeStamp());
	}
	
	public static void logContextSwitchStart(){
		System.out.printf("Context switch started  \t\t\ttime=%-30s %n", clock.timeStamp());
		//System.out.println("Context Switch Started\t\ttime: " + clock.timeStamp());
	}
	
	public static void logContextSwitchComplete(){
		System.out.printf("Context switch complete \t\t\ttime=%-30s %n",clock.timeStamp());
		//System.out.println("Context Switch Completed\t\ttime: " + clock.timeStamp());
	}
	
	public static void logAllProcessesCompleted(){
		System.out.println("All processes have been fully executed.\n");
	}
}

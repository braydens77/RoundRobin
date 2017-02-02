package CPUscheduling;
import java.util.Comparator;


public class Process implements Comparator<Process>{

	public int pid;
	public double arrivalTime;
	public double burstTime;
	public double burstRemaining;
	public double creationTime;
	public boolean hasStarted;
	public long startTime;
	public long finishTime;
	public int numOfSwitches;
	
	public Process(int pid, double arrivalTime, double burstTime)
	{
		this.pid = pid;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.burstRemaining = burstTime;
		this.creationTime = System.nanoTime();
		this.hasStarted = false;
	}
	
	public void start(){
		this.hasStarted = true;
		this.startTime = System.nanoTime();
	}

	@Override
	public int compare(Process p1, Process p2) {
		if(p1.startTime < p2.startTime)
			return 1;
		else if(p1.startTime == p2.startTime)
			return 0;
		else 
			return -1;
	}
}

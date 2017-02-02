package CPUscheduling;

public class ProcessCreator {

	public ProcessCreator(){}
	
	public Process createProcess(int pid, double arrivalTime, double burstTime){
		return new Process(pid, arrivalTime, burstTime);
	}
}

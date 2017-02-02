package CPUscheduling;

import java.util.ArrayList;

public class ShortTermScheduler {

	public double timeQuantum;
	public CPU cpu;
	ArrayList<Process>finishedList; //array list for calculating stats of all finished processes
	
	public ShortTermScheduler(Queue<Process> readyQueue, CPU cpu){
		this.cpu = cpu;
		finishedList = new ArrayList<Process>();
		runNextProcess(readyQueue);
	}
	
	public void runNextProcess(Queue<Process> rq){
		Process p = rq.remove();
		while(p!=null){
			cpu.execute(p);
			if(p.burstRemaining > 0)
				rq.insert(p);
			else{
				p.finishTime = System.nanoTime();
				finishedList.add(p);
			}
			p = rq.remove();
		}
		Simulator.logAllProcessesCompleted();
	}
	
	public ArrayList<Process> getFinishedList(){
		return finishedList;
	}
}

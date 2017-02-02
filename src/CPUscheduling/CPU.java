package CPUscheduling;

public class CPU {
	
	public double timeQuantum; // optional
	public long totalContextSwitchTime;
	public int numOfContextSwitches;
	
	// variables for calculating context switch times
	public long executeEndTime;
	public long executeStartTime;
	

	public CPU(double timeQuantum){
		this.timeQuantum = timeQuantum;
		this.numOfContextSwitches = 0;
	}
	
	public void execute(Process p){
		if(p.burstRemaining > 0){
			if(!p.hasStarted)
				p.start();
			numOfContextSwitches++;
			executeStartTime = System.nanoTime();
			Simulator.logContextSwitchComplete();
			calculateContextSwitch();
			Simulator.logExecution(p);
			
			
			// simulate execution by delaying execution for the specified time quantum
			try {
				if(p.burstTime < timeQuantum)
					Thread.sleep((long) (p.burstTime/timeQuantum));
				else
					Thread.sleep((long) timeQuantum);
			} catch (InterruptedException e) {		// cpu execution may be interrupted in a real scenario
				e.printStackTrace();
			}
			
			
			// update the remaining burst time for the process
			p.burstRemaining = p.burstRemaining - timeQuantum;
			if(p.burstRemaining <= 0){
				p.burstRemaining = 0; // avoid any negative burstRemaining
				Simulator.logProcessExecutionComplete(p);
			}
			else
				p.numOfSwitches++; 
			executeEndTime = System.nanoTime();
			Simulator.logContextSwitchStart();
		}
	}
	
	// updates total context switch time
	public void calculateContextSwitch(){
		long elapsedTime = executeStartTime - executeEndTime;
		totalContextSwitchTime += elapsedTime;
	}
}

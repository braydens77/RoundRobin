package CPUscheduling;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyClock {
	
	public MyClock(){}
	
	public String timeStamp(){
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS").format(Calendar.getInstance().getTime());
	}
	
	

}

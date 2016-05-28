package il.co.runnerdevice.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class UtilConvertor {

	public static String ConvertDateToISO(Date d){
		
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		df.setTimeZone(tz);
		String nowAsISO = df.format(d);
		return nowAsISO;
		
		//String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		//return currentDateandTime;
	}
}
 //

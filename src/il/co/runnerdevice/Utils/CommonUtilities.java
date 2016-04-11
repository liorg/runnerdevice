package il.co.runnerdevice.Utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * Helper class providing methods and constants common to other classes in the
 * app.
 */
public final class CommonUtilities 
{
	
	public static final String URL = "http://testkipo.kipodeal.co.il";

    /**
     * Base URL of the Demo Server (such as http://my_host:8080/gcm-demo)
     */
	public static final String SERVER_URL = "http://testkipo.kipodeal.co.il/push/Register.ashx";

    /**
     * Google API project id registered to use GCM.
     */
	public static final String SENDER_ID = "530446261684";

    /**
     * Tag used on log messages.
     */
	public  static final String TAG = "GCMDemo";

    /**
     * Intent used to display a message in the screen.
     */
	public static final String DISPLAY_MESSAGE_ACTION ="il.co.runnerdevice.DISPLAY_MESSAGE";
	public static final String NotifyMessage ="NotifyMessage";

    /**
     * Intent's extra that contains the message to be displayed.
     */
	public static final String EXTRA_MESSAGE = "message";
	
	public  static final String TAGC = "COMMON";
	
	public  static final String APP_NAME = "runnerdevice.co.il";
    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
	public  static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
	
	 public static boolean  IsValidToken(String eDate){
	    	Log.d(APP_NAME, TAGC + "> IsValidToken ");
			Date local = new Date();
			Date  expiredDate;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Log.d(APP_NAME, TAGC + "> IsValidToken =>local return "+local );
			Log.d(APP_NAME, TAGC + "> IsValidToken =>expired Date return "+eDate );
			//String eDate=pref.getString(KEY_ExpiredDate, null);
			
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
			Calendar cal = Calendar.getInstance(TimeZone.getDefault());
			local = cal.getTime();
			Log.d(APP_NAME, TAGC + "> IsValidToken =>local(UTC) return "+local );
			
			try {
				format.setTimeZone(TimeZone.getDefault());
				expiredDate = format.parse(eDate);
				Log.d(APP_NAME, TAGC + "> IsValidToken =>expiredDate return "+expiredDate );
				   if(local.before(expiredDate)){
					   Log.d(APP_NAME, TAGC + "> IsValidToken =>local.before(expiredDat RETURN True");
					   	return true;
				   }
				   Log.d(APP_NAME, TAGC + "> IsValidToken =>local.before(expiredDat RETURN false");
				 	return false;
			} 
			catch (Exception e) {
				 Log.e(APP_NAME, TAGC + "> IsValidToken =>error"+e.getMessage());
				 
			    e.printStackTrace();
		return false;
			}
			
		}
}

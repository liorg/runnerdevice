package il.co.runnerdevice.Tutorial;

public interface Config {
	 
    
    // CONSTANTS
    static final String YOUR_SERVER_URL = "http://5.100.251.87:4545/push/Register.ashx";
     
    // Google project id
    static final String GOOGLE_SENDER_ID = "530446261684"; 
 
    /**
     * Tag used on log messages.
     */
    static final String TAG = "GCM Android Example";
 
    static final String DISPLAY_MESSAGE_ACTION =
            "com.androidexample.gcm.DISPLAY_MESSAGE";
 
    static final String EXTRA_MESSAGE = "message";
         
     
}
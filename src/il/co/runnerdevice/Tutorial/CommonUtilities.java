package il.co.runnerdevice.Tutorial;


import android.content.Context;
import android.content.Intent;


/**
 * Helper class providing methods and constants common to other classes in the
 * app.
 */
public final class CommonUtilities {

    /**
     * Base URL of the Demo Server (such as http://my_host:8080/gcm-demo)
     */
	public static final String SERVER_URL = "http://5.100.251.87:4545/push/Register.ashx";

    /**
     * Google API project id registered to use GCM.
     */
    public static final String SENDER_ID = "530446261684";

    /**
     * Tag used on log messages.
     */
    public static final String TAG = "GCMDemo";

    /**
     * Intent used to display a message in the screen.
     */
    public static final String DISPLAY_MESSAGE_ACTION ="il.co.runnerdevice.DISPLAY_MESSAGE";
    public static final String NotifyMessage ="NotifyMessage";

    /**
     * Intent's extra that contains the message to be displayed.
     */
    public static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}

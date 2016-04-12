package il.co.runnerdevice.Dblocal;


 
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
//http://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
public class DatabaseHelper extends SQLiteOpenHelper {
	 
    // Logcat tag
    private static final String LOG = "DatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "shippingManager";
 
    // Table Names
    private static final String TABLE_USER = "user";
  
    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
 
    // user Table - column nmaes
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_USERID = "userid";
    // TAGS Table - column names
    private static final String KEY_TAG_USERNAME= "username";

    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_TODO = "CREATE TABLE "
            + TABLE_USER  
            + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
    		+ KEY_TAG_USERNAME + " TEXT," 
            + KEY_FIRSTNAME + " TEXT," 
            + KEY_LASTNAME + " TEXT," 
            + KEY_USERID + " TEXT," 
            + KEY_CREATED_AT + " DATETIME" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
 
        // creating required tables
        db.execSQL(CREATE_TABLE_TODO);
     
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
      
 
        // create new tables
        onCreate(db);
    }
}
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
    public static final String LOG = "DatabaseHelper";
 
    // Database Version
    public static final int DATABASE_VERSION = 2;
 
    // Database Name
    public static final String DATABASE_NAME = "shippingManager";
 
    // Table Names
    public static final String TABLE_USER = "user";
  
    // Common column names
    public static final String KEY_ID = "id";
    public static final String KEY_CREATED_AT = "LastUpdateRecord";
 
    // user Table - column nmaes
    public static final String KEY_FIRSTNAME = "firstname";
    public static final String KEY_LASTNAME = "lastname";
    public static final String KEY_USERID = "userid";
    // TAGS Table - column names
    public static final String KEY_TAG_USERNAME= "username";
    public static final String KEY_SyncStatus= "SyncStatus";
    public static final String KEY_SyncStateRecord= "SyncStateRecord";
    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_USER= "CREATE TABLE "
            + TABLE_USER  
            + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
    		+ KEY_TAG_USERNAME + " TEXT," 
            + KEY_FIRSTNAME + " TEXT," 
            + KEY_LASTNAME + " TEXT," 
            + KEY_SyncStatus + " INTEGER," 
            + KEY_SyncStateRecord + " INTEGER," 
            + KEY_USERID + " TEXT," 
            + KEY_CREATED_AT + " DATETIME" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
     //   getReadableDatabase(); 
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_USER);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        // create new tables
        onCreate(db);
    }
    public boolean UpdateUser (String firstname, String lastname, String userid)
    {
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues contentValues = new ContentValues();
       contentValues.put(KEY_FIRSTNAME, firstname);
       contentValues.put(KEY_LASTNAME, lastname);
       db.update(TABLE_USER, contentValues, KEY_USERID+" = ?", new String[]{userid});
       return true;
    }
    public boolean AddUser (String firstname, String lastname, String userid,String userName)
    {
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues contentValues = new ContentValues();
       contentValues.put(KEY_USERID, userid);
       contentValues.put(KEY_FIRSTNAME, firstname);
       contentValues.put(KEY_LASTNAME, lastname);
       contentValues.put(KEY_TAG_USERNAME, userName);
       db.insert(TABLE_USER, null, contentValues);
       return true;
    }
    public Cursor getUser(String userid){
        SQLiteDatabase db = this.getReadableDatabase();
       // Cursor res =  db.rawQuery( "select * from "+TABLE_USER+" where "+KEY_USERID+"='"+userid+"'", null );
        Cursor res= db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + KEY_USERID + "=?",
                new String[] { String.valueOf(userid) });
        return res;
     }
}
package il.co.runnerdevice.Dblocal.Dao;

import il.co.runnerdevice.Dblocal.DatabaseHelper;
import il.co.runnerdevice.Pojo.WhoAmI;

import java.io.Serializable;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * This class represent a UserDao
 *
 * Created by Udini on 6/22/13.
 */
public class UserDao implements Serializable {

    // Fields
    public WhoAmI m_whoami;
  

    public UserDao( WhoAmI whoami) {
        this.m_whoami = whoami;
  
    }

    // Create a TvShow object from a cursor
    public static WhoAmI fromCursor(Cursor curUser) 
    {
    	WhoAmI whoami= new WhoAmI();
    	whoami.setFirstName(curUser.getString(curUser.getColumnIndex(DatabaseHelper.KEY_FIRSTNAME)));
    	whoami.setLastName( curUser.getString(curUser.getColumnIndex(DatabaseHelper.KEY_LASTNAME)));
    	whoami.setUserId( curUser.getString(curUser.getColumnIndex(DatabaseHelper.KEY_USERID)));
    	whoami.setUserName(curUser.getString(curUser.getColumnIndex(DatabaseHelper.KEY_TAG_USERNAME)));
       return whoami;
    }
    
    /**
     * Convenient method to get the objects data members in ContentValues object.
     * This will be useful for Content Provider operations,
     * which use ContentValues object to represent the data.
     *
     * @return
     */
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
   //     values.put(DatabaseHelper.KEY_FIRSTNAME, name);
   //     values.put(TvShowsDbHelper.TVSHOWS_COL_YEAR, year);
        return values;
    }

   

    @Override
    public boolean equals(Object o) {
        
        return true;
    }

    @Override
    public int hashCode() 
    {
    	return 0;
    }

    @Override
    public String toString() {
    	return "";
    }
}
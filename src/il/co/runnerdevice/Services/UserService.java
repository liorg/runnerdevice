package il.co.runnerdevice.Services;

import il.co.runnerdevice.Dblocal.DatabaseHelper;
import il.co.runnerdevice.Pojo.WhoAmI;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.appcompat.R.string;

public class UserService {
	Context m_Context;

	public UserService(Context context) {
		m_Context = context;
	}
	public WhoAmI IfGetMeIsEmpty(String userid,String fname,String lname,String user ) {
		WhoAmI get=GetMe(userid);
		if(get==null)
			get=AddUser(userid,fname,lname,user);
		
		return get;
	}
	
	public WhoAmI AddUser(String userid,String fname,String lname,String user){
		WhoAmI whoAmI =null;
		try {
			DatabaseHelper dal = new DatabaseHelper(m_Context);
			whoAmI=new WhoAmI();
			boolean isOk=dal.AddUser(fname, lname, userid, user);
			if(!isOk) return null;
			whoAmI.setFirstName(fname);
			whoAmI.setLastName(lname);
			whoAmI.setUserName(user);
			whoAmI.setUserId(userid);
			return whoAmI;
		} finally {

		
		}
	}
	
	public WhoAmI UpdateUser(String userid,String fname,String lname,String user){
		WhoAmI whoAmI =null;
		try {
			DatabaseHelper dal = new DatabaseHelper(m_Context);
			whoAmI=new WhoAmI();
			boolean isOk=dal.UpdateUser(fname, lname, userid);
			if(!isOk)
				return null;
			
			whoAmI.setFirstName(fname);
			whoAmI.setLastName(lname);
			whoAmI.setUserName(user);
			whoAmI.setUserId(userid);
			return whoAmI;
		} finally {

		
		}
	}
	public WhoAmI GetMe(String userid) {

		Cursor cursor = null;
		WhoAmI whoAmI =null;
		try {
			DatabaseHelper dal = new DatabaseHelper(m_Context);
			cursor = dal.getUser(userid);
			
			if (cursor!=null && cursor.getCount() > 0) {
				whoAmI = new WhoAmI();
				cursor.moveToFirst();
				whoAmI.setUserId(cursor.getString(cursor
						.getColumnIndex(DatabaseHelper.KEY_USERID)));
				whoAmI.setFirstName(cursor.getString(cursor
						.getColumnIndex(DatabaseHelper.KEY_FIRSTNAME)));
				whoAmI.setLastName(cursor.getString(cursor
						.getColumnIndex(DatabaseHelper.KEY_LASTNAME)));
				whoAmI.setFullName(whoAmI.getFirstName() + " "
						+ whoAmI.getLastName());
			}

			return whoAmI;
		} finally {
			if(cursor!=null)
				cursor.close();
		}
	}

}

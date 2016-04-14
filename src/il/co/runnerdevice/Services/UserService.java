package il.co.runnerdevice.Services;

import il.co.runnerdevice.Dblocal.DatabaseHelper;
import il.co.runnerdevice.Pojo.WhoAmI;
import android.content.Context;
import android.database.Cursor;

public class UserService {
	Context m_Context;

	public UserService(Context context) {
		m_Context = m_Context;
	}

	public WhoAmI GetMe(String userid) {

		Cursor cursor = null;
		WhoAmI whoAmI =null;
		try {
			DatabaseHelper dal = new DatabaseHelper(m_Context);
			cursor = dal.getUser(userid);
			whoAmI = new WhoAmI();
			if (cursor.getCount() > 0) {

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

			cursor.close();
		}
	}

}

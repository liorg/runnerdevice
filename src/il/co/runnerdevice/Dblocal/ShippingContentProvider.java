package il.co.runnerdevice.Dblocal;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class ShippingContentProvider extends ContentProvider {

	public static final UriMatcher URI_MATCHER = buildUriMatcher();
	public static final String PATH_USER = "user";
	public static final int PATH_USERTOKEN = 100;
	public static final String PATH_FOR_USER_ID = "user/*";
	public static final int PATH_FOR_USERID_TOKEN = 200;
	public static final String PATH_USER_CHANGED = "userChanged";
	public static final int PATH_FOR_USERCHANGED_TOKEN = 300;
	
	// Uri Matcher for the content provider
	private static UriMatcher buildUriMatcher() {
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		final String authority = ShippingContract.AUTHORITY;
		matcher.addURI(authority, PATH_USER, PATH_USERTOKEN);
		matcher.addURI(authority, PATH_FOR_USER_ID, PATH_FOR_USERID_TOKEN);
		matcher.addURI(authority, PATH_USER_CHANGED, PATH_FOR_USERCHANGED_TOKEN);
		
		return matcher;
	}

	// Content Provider stuff

	private DatabaseHelper dbHelper;

	@Override
	public boolean onCreate() {
		Context ctx = getContext();
		dbHelper = new DatabaseHelper(ctx);
		return true;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		final int match = URI_MATCHER.match(uri);
		switch (match) {
		// retrieve tv shows list
		case PATH_USERTOKEN: {
			SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
			builder.setTables(DatabaseHelper.TABLE_USER);
			return builder.query(db, projection, selection, selectionArgs,
					null, null, sortOrder);
		}
		case PATH_FOR_USERCHANGED_TOKEN: {
			return dbHelper.getChangedDataUser();
		}
		case PATH_FOR_USERID_TOKEN: {
			String userid = uri.getLastPathSegment();
			return dbHelper.getUser(userid);
		}
		default:
			return null;
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int token = URI_MATCHER.match(uri);
		int rowsDeleted = -1;
		switch (token) {
		case (PATH_USERTOKEN):
			rowsDeleted = db.delete(DatabaseHelper.TABLE_USER, selection,
					selectionArgs);
			break;
		case (PATH_FOR_USERID_TOKEN):
			String tvShowIdWhereClause = DatabaseHelper.KEY_USERID + "="
					+ uri.getLastPathSegment();
			if (!TextUtils.isEmpty(selection))
				tvShowIdWhereClause += " AND " + selection;
			rowsDeleted = db.delete(DatabaseHelper.TABLE_USER,
					tvShowIdWhereClause, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		// Notifying the changes, if there are any
		if (rowsDeleted != -1)
			getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int token = URI_MATCHER.match(uri);
	
		switch (token) {
		case (PATH_FOR_USERID_TOKEN):
			//String userid = values.getAsString(DatabaseHelper.KEY_USERID);
			String userid = uri.getLastPathSegment();
			String firstname = values.getAsString(DatabaseHelper.KEY_FIRSTNAME);
			String lastname = values.getAsString(DatabaseHelper.KEY_LASTNAME);
			dbHelper.UpdateUser(firstname, lastname, userid);
			break;
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		return 0;
	}
}

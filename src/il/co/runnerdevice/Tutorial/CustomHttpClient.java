package il.co.runnerdevice.Tutorial;

import il.co.runnerdevice.Services.SessionService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;

public class CustomHttpClient {

	public static List<NameValuePair> GetLoginData(String username, String pws) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("grant_type", "password"));
		nameValuePairs.add(new BasicNameValuePair("username", username));
		nameValuePairs.add(new BasicNameValuePair("password", pws));
		nameValuePairs.add(new BasicNameValuePair("client_id", "ngAutoApp"));
		return nameValuePairs;
	}

	public static void SetHeaderToken(HttpGet httpGet, SessionManager session) {
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");
		String token = session.GetToken();
		httpGet.setHeader("Authorization", "Bearer " + token);
	}

	public static List<NameValuePair> GetRefreshToken(SessionManager session) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs
				.add(new BasicNameValuePair("grant_type", "refresh_token"));
		nameValuePairs.add(new BasicNameValuePair("refresh_token", session
				.GetRefreshKeyToken()));

		nameValuePairs.add(new BasicNameValuePair("client_id", "ngAutoApp"));
		return nameValuePairs;
	}

	public static List<NameValuePair> GetRefreshToken(SessionService session) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs
				.add(new BasicNameValuePair("grant_type", "refresh_token"));
		nameValuePairs.add(new BasicNameValuePair("refresh_token", session
				.GetRefreshKeyToken()));

		nameValuePairs.add(new BasicNameValuePair("client_id", "ngAutoApp"));
		return nameValuePairs;
	}

	public static String GetASCIIContentFromEntity(HttpEntity entity)
			throws IllegalStateException, IOException, JSONException {
		InputStream in = entity.getContent();
		StringBuffer out = new StringBuffer();
		int n = 1;
		while (n > 0) {
			byte[] b = new byte[4096];
			n = in.read(b);
			if (n > 0)
				out.append(new String(b, 0, n));
		}
		return out.toString();
	}
}

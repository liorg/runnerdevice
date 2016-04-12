package il.co.runnerdevice.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessToken {

	@SerializedName("access_token")
	@Expose
	private String accessToken;
	@SerializedName("token_type")
	@Expose
	private String tokenType;
	@SerializedName("expires_in")
	@Expose
	private int expiresIn;
	@SerializedName("refresh_token")
	@Expose
	private String refreshToken;
	@SerializedName("as:client_id")
	@Expose
	private String asClientId;
	@SerializedName("userName")
	@Expose
	private String userName;
	@SerializedName("m:userId")
	@Expose
	private String mUserId;
	@SerializedName("m:currentTime")
	@Expose
	private String mCurrentTime;
	@SerializedName("m:expiredOn")
	@Expose
	private String mExpiredOn;
	@SerializedName("roles")
	@Expose
	private String roles;
	@SerializedName(".issued")
	@Expose
	private String Issued;
	@SerializedName(".expires")
	@Expose
	private String Expires;

	/**
	 * 
	 * @return The accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * 
	 * @param accessToken
	 *            The access_token
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * 
	 * @return The tokenType
	 */
	public String getTokenType() {
		return tokenType;
	}

	/**
	 * 
	 * @param tokenType
	 *            The token_type
	 */
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	/**
	 * 
	 * @return The expiresIn
	 */
	public int getExpiresIn() {
		return expiresIn;
	}

	/**
	 * 
	 * @param expiresIn
	 *            The expires_in
	 */
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	/**
	 * 
	 * @return The refreshToken
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * 
	 * @param refreshToken
	 *            The refresh_token
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	/**
	 * 
	 * @return The asClientId
	 */
	public String getAsClientId() {
		return asClientId;
	}

	/**
	 * 
	 * @param asClientId
	 *            The as:client_id
	 */
	public void setAsClientId(String asClientId) {
		this.asClientId = asClientId;
	}

	/**
	 * 
	 * @return The userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 
	 * @param userName
	 *            The userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 
	 * @return The mUserId
	 */
	public String getMUserId() {
		return mUserId;
	}

	/**
	 * 
	 * @param mUserId
	 *            The m:userId
	 */
	public void setMUserId(String mUserId) {
		this.mUserId = mUserId;
	}

	/**
	 * 
	 * @return The mCurrentTime
	 */
	public String getMCurrentTime() {
		return mCurrentTime;
	}

	/**
	 * 
	 * @param mCurrentTime
	 *            The m:currentTime
	 */
	public void setMCurrentTime(String mCurrentTime) {
		this.mCurrentTime = mCurrentTime;
	}

	/**
	 * 
	 * @return The mExpiredOn
	 */
	public String getMExpiredOn() {
		return mExpiredOn;
	}

	/**
	 * 
	 * @param mExpiredOn
	 *            The m:expiredOn
	 */
	public void setMExpiredOn(String mExpiredOn) {
		this.mExpiredOn = mExpiredOn;
	}

	/**
	 * 
	 * @return The roles
	 */
	public String getRoles() {
		return roles;
	}

	/**
	 * 
	 * @param roles
	 *            The roles
	 */
	public void setRoles(String roles) {
		this.roles = roles;
	}

	/**
	 * 
	 * @return The Issued
	 */
	public String getIssued() {
		return Issued;
	}

	/**
	 * 
	 * @param Issued
	 *            The .issued
	 */
	public void setIssued(String Issued) {
		this.Issued = Issued;
	}

	/**
	 * 
	 * @return The Expires
	 */
	public String getExpires() {
		return Expires;
	}

	/**
	 * 
	 * @param Expires
	 *            The .expires
	 */
	public void setExpires(String Expires) {
		this.Expires = Expires;
	}

}
package il.co.runnerdevice.Pojo;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sync {

	protected Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@SerializedName("DeviceId")
	@Expose
	private String DeviceId;

	@SerializedName("ClientId")
	@Expose
	private String ClientId;

	@SerializedName("UserId")
	@Expose
	private String UserId;

	public String getDeviceId() {
		return DeviceId;
	}

	public void setDeviceId(String DeviceId) {
		this.DeviceId = DeviceId;
	}

	public String getClientId() {
		return ClientId;
	}

	public void setClientId(String ClientId) {
		this.ClientId = ClientId;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String UserId) {
		this.UserId = UserId;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}
}

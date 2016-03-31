package il.co.runnerdevice.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestSync
{

	@SerializedName("DeviceId")
	@Expose
	private String DeviceId;
	@SerializedName("ClientId")
	@Expose
	
     private String ClientId;

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

}
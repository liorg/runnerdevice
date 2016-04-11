package il.co.runnerdevice.Pojo;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemSync extends  Sync
{

	@SerializedName("SyncStatus")
	@Expose
	private int SyncStatus;
	
	@SerializedName("ObjectTableCode")
	@Expose
	private int ObjectTableCode;
	
	@SerializedName("ObjectId")
	@Expose
	private String ObjectId;
	
	@SerializedName("SyncStateRecord")
	@Expose
	private int SyncStateRecord;
	
	@SerializedName("LastUpdateRecord")
	@Expose
	private String LastUpdateRecord;
	
	public int getSyncStateRecord() {
	return SyncStateRecord;
	}
	public void setSyncStateRecord(int SyncStateRecord) {
	this.SyncStateRecord = SyncStateRecord;
	}
	
	public int getSyncStatus() {
	return SyncStatus;
	}
	public void setSyncStatus(int SyncStatus) {
	this.SyncStatus = SyncStatus;
	}

	public int getObjectTableCode() {
	return ObjectTableCode;
	}
	
	public void setObjectTableCode(int ObjectTableCode)
	{
	this.ObjectTableCode = ObjectTableCode;
	}
	
	public String getLastUpdateRecord() 
	{
		return LastUpdateRecord;
	}

	public void setLastUpdateRecord(String LastUpdateRecord) 
	{
		this.LastUpdateRecord = LastUpdateRecord;
	}
	
	
	public String getObjectId() {
		return ObjectId;
	}

	public void setObjectId(String ObjectId) {
		this.ObjectId = ObjectId;
	}
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}
}



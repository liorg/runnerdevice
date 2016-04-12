package il.co.runnerdevice.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shipping {

	@SerializedName("Id")
	@Expose
	private String Id;
	@SerializedName("Name")
	@Expose
	private String Name;
	@SerializedName("ActualStartDateDt")
	@Expose
	private Object ActualStartDateDt;
	@SerializedName("ActualEndDateDt")
	@Expose
	private String ActualEndDateDt;
	@SerializedName("SlaEndTime")
	@Expose
	private String SlaEndTime;
	@SerializedName("ShipTypeIdName")
	@Expose
	private String ShipTypeIdName;
	@SerializedName("ShipTypeId")
	@Expose
	private String ShipTypeId;
	@SerializedName("Status")
	@Expose
	private String Status;
	@SerializedName("StatusPresent")
	@Expose
	private int StatusPresent;
	@SerializedName("WalkOrder")
	@Expose
	private int WalkOrder;
	@SerializedName("SlaDate")
	@Expose
	private Object SlaDate;
	@SerializedName("Recipient")
	@Expose
	private Object Recipient;
	@SerializedName("TelSource")
	@Expose
	private String TelSource;
	@SerializedName("TelTarget")
	@Expose
	private String TelTarget;
	@SerializedName("NameSource")
	@Expose
	private String NameSource;
	@SerializedName("NameTarget")
	@Expose
	private String NameTarget;
	@SerializedName("TargetAddress")
	@Expose
	private Address TargetAddress;
	@SerializedName("SourceAddress")
	@Expose
	private Address SourceAddress;
	@SerializedName("SigBackType")
	@Expose
	private int SigBackType;
	@SerializedName("Direction")
	@Expose
	private int Direction;
	@SerializedName("PathSig")
	@Expose
	private Object PathSig;

	public String getId() {
		return Id;
	}

	public void setId(String Id) {
		this.Id = Id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public Object getActualStartDateDt() {
		return ActualStartDateDt;
	}

	public void setActualStartDateDt(Object ActualStartDateDt) {
		this.ActualStartDateDt = ActualStartDateDt;
	}

	public String getActualEndDateDt() {
		return ActualEndDateDt;
	}

	public void setActualEndDateDt(String ActualEndDateDt) {
		this.ActualEndDateDt = ActualEndDateDt;
	}

	public String getSlaEndTime() {
		return SlaEndTime;
	}

	public void setSlaEndTime(String SlaEndTime) {
		this.SlaEndTime = SlaEndTime;
	}

	public String getShipTypeIdName() {
		return ShipTypeIdName;
	}

	public void setShipTypeIdName(String ShipTypeIdName) {
		this.ShipTypeIdName = ShipTypeIdName;
	}

	public String getShipTypeId() {
		return ShipTypeId;
	}

	public void setShipTypeId(String ShipTypeId) {
		this.ShipTypeId = ShipTypeId;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String Status) {
		this.Status = Status;
	}

	public int getStatusPresent() {
		return StatusPresent;
	}

	public void setStatusPresent(int StatusPresent) {
		this.StatusPresent = StatusPresent;
	}

	public int getWalkOrder() {
		return WalkOrder;
	}

	public void setWalkOrder(int WalkOrder) {
		this.WalkOrder = WalkOrder;
	}

	public Object getSlaDate() {
		return SlaDate;
	}

	public void setSlaDate(Object SlaDate) {
		this.SlaDate = SlaDate;
	}

	public Object getRecipient() {
		return Recipient;
	}

	public void setRecipient(Object Recipient) {
		this.Recipient = Recipient;
	}

	public String getTelSource() {
		return TelSource;
	}

	public void setTelSource(String TelSource) {
		this.TelSource = TelSource;
	}

	public String getTelTarget() {
		return TelTarget;
	}

	public void setTelTarget(String TelTarget) {
		this.TelTarget = TelTarget;
	}

	public String getNameSource() {
		return NameSource;
	}

	public void setNameSource(String NameSource) {
		this.NameSource = NameSource;
	}

	public String getNameTarget() {
		return NameTarget;
	}

	public void setNameTarget(String NameTarget) {
		this.NameTarget = NameTarget;
	}

	public Address getTargetAddress() {
		return TargetAddress;
	}

	public void setTargetAddress(Address TargetAddress) {
		this.TargetAddress = TargetAddress;
	}

	public Address getSourceAddress() {
		return SourceAddress;
	}

	public void setSourceAddress(Address SourceAddress) {
		this.SourceAddress = SourceAddress;
	}

	public int getSigBackType() {
		return SigBackType;
	}

	public void setSigBackType(int SigBackType) {
		this.SigBackType = SigBackType;
	}

	public int getDirection() {
		return Direction;
	}

	public void setDirection(int Direction) {
		this.Direction = Direction;
	}

	public Object getPathSig() {
		return PathSig;
	}

	public void setPathSig(Object PathSig) {
		this.PathSig = PathSig;
	}
}

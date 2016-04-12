package il.co.runnerdevice.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

	@SerializedName("UId")
	@Expose
	private Object UId;
	@SerializedName("Street")
	@Expose
	private String Street;
	@SerializedName("Streetcode")
	@Expose
	private String Streetcode;
	@SerializedName("City")
	@Expose
	private String City;
	@SerializedName("Citycode")
	@Expose
	private String Citycode;
	@SerializedName("Num")
	@Expose
	private String Num;
	@SerializedName("ExtraDetail")
	@Expose
	private Object ExtraDetail;
	@SerializedName("Lat")
	@Expose
	private double Lat;
	@SerializedName("Lng")
	@Expose
	private double Lng;
	@SerializedName("IsSensor")
	@Expose
	private boolean IsSensor;

	public Object getUId() {
		return UId;
	}

	public void setUId(Object UId) {
		this.UId = UId;
	}

	public String getStreet() {
		return Street;
	}

	public void setStreet(String Street) {
		this.Street = Street;
	}

	public String getStreetcode() {
		return Streetcode;
	}

	public void setStreetcode(String Streetcode) {
		this.Streetcode = Streetcode;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String City) {
		this.City = City;
	}

	public String getCitycode() {
		return Citycode;
	}

	public void setCitycode(String Citycode) {
		this.Citycode = Citycode;
	}

	public String getNum() {
		return Num;
	}

	public void setNum(String Num) {
		this.Num = Num;
	}

	public Object getExtraDetail() {
		return ExtraDetail;
	}

	public void setExtraDetail(Object ExtraDetail) {
		this.ExtraDetail = ExtraDetail;
	}

	public double getLat() {
		return Lat;
	}

	public void setLat(double Lat) {
		this.Lat = Lat;
	}

	public double getLng() {
		return Lng;
	}

	public void setLng(double Lng) {
		this.Lng = Lng;
	}

	public boolean isIsSensor() {
		return IsSensor;
	}

	public void setIsSensor(boolean IsSensor) {
		this.IsSensor = IsSensor;
	}

}

package il.co.runnerdevice.Pojo;

import java.util.HashMap;
import java.util.Map;

public class WhoAmIResponse extends ResponseBase
{
	protected WhoAmI Model;
	
	
	public WhoAmI getModel() {
	return Model;
	}
	
	public void setModel(WhoAmI Model) {
	this.Model = Model;
	}
	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}
	
	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}

}
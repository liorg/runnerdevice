package il.co.runnerdevice.Pojo;

import java.util.HashMap;
import java.util.Map;

public class WhoAmI {
	
private String FirstName;
private String LastName;
private String FullName;
private String UserName;
private Object UserId;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public String getFirstName() {
return FirstName;
}

public void setFirstName(String FirstName) {
this.FirstName = FirstName;
}


public String getLastName() {
return LastName;
}

public void setLastName(String LastName) {
this.LastName = LastName;
}

public String getFullName() {
return FullName;
}

public void setFullName(String FullName) {
this.FullName = FullName;
}

public String getUserName() {
return UserName;
}

public void setUserName(String UserName) {
this.UserName = UserName;
}

public Object getUserId() {
return UserId;
}

public void setUserId(Object UserId) {
this.UserId = UserId;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
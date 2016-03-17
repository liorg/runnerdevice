package il.co.runnerdevice.Pojo;

import java.util.HashMap;
import java.util.Map;

public class WhoAmI {

private String FullName;
private String UserName;
private Object UserId;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

/**
* 
* @return
* The FullName
*/
public String getFullName() {
return FullName;
}

/**
* 
* @param FullName
* The FullName
*/
public void setFullName(String FullName) {
this.FullName = FullName;
}

/**
* 
* @return
* The UserName
*/
public String getUserName() {
return UserName;
}

/**
* 
* @param UserName
* The UserName
*/
public void setUserName(String UserName) {
this.UserName = UserName;
}

/**
* 
* @return
* The UserId
*/
public Object getUserId() {
return UserId;
}

/**
* 
* @param UserId
* The UserId
*/
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
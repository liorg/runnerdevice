


package il.co.runnerdevice.Pojo;

import java.util.HashMap;
import java.util.Map;


public class WhoAmIResponse {

private boolean IsAuthenticated;
private il.co.runnerdevice.Pojo.WhoAmI Model;
private Object ErrCode;
private boolean IsError;
private Object ErrDesc;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

/**
* 
* @return
* The IsAuthenticated
*/
public boolean isIsAuthenticated() {
return IsAuthenticated;
}

/**
* 
* @param IsAuthenticated
* The IsAuthenticated
*/
public void setIsAuthenticated(boolean IsAuthenticated) {
this.IsAuthenticated = IsAuthenticated;
}

/**
* 
* @return
* The Model
*/
public il.co.runnerdevice.Pojo.WhoAmI getModel() {
return Model;
}

/**
* 
* @param Model
* The Model
*/
public void setModel(il.co.runnerdevice.Pojo.WhoAmI Model) {
this.Model = Model;
}

/**
* 
* @return
* The ErrCode
*/
public Object getErrCode() {
return ErrCode;
}

/**
* 
* @param ErrCode
* The ErrCode
*/
public void setErrCode(Object ErrCode) {
this.ErrCode = ErrCode;
}

/**
* 
* @return
* The IsError
*/
public boolean isIsError() {
return IsError;
}

/**
* 
* @param IsError
* The IsError
*/
public void setIsError(boolean IsError) {
this.IsError = IsError;
}

/**
* 
* @return
* The ErrDesc
*/
public Object getErrDesc() {
return ErrDesc;
}

/**
* 
* @param ErrDesc
* The ErrDesc
*/
public void setErrDesc(Object ErrDesc) {
this.ErrDesc = ErrDesc;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
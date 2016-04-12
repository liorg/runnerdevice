package il.co.runnerdevice.Pojo;

import java.util.HashMap;
import java.util.Map;

public class ResponseBase {
	protected boolean IsAuthenticated;

	protected Object ErrCode;
	protected boolean IsError;
	protected Object ErrDesc;

	protected Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public boolean isIsAuthenticated() {
		return IsAuthenticated;
	}

	public void setIsAuthenticated(boolean IsAuthenticated) {
		this.IsAuthenticated = IsAuthenticated;
	}

	public Object getErrCode() {
		return ErrCode;
	}

	public void setErrCode(Object ErrCode) {
		this.ErrCode = ErrCode;
	}

	public boolean isIsError() {
		return IsError;
	}

	public void setIsError(boolean IsError) {
		this.IsError = IsError;
	}

	public Object getErrDesc() {
		return ErrDesc;
	}

	public void setErrDesc(Object ErrDesc) {
		this.ErrDesc = ErrDesc;
	}

}

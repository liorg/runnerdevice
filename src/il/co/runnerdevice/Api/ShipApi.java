package il.co.runnerdevice.Api;


import retrofit2.Call;
import retrofit2.http.GET;

import il.co.runnerdevice.Pojo.WhoAmIResponse;
//http://mvnrepository.com/artifact/com.squareup.retrofit/retrofit/2.0.0-beta2
public interface ShipApi {
	 @GET("api/ship/whoami")
	    Call<WhoAmIResponse> WhoAmi();
}

package il.co.runnerdevice.Api;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import il.co.runnerdevice.Pojo.*;
//http://mvnrepository.com/artifact/com.squareup.retrofit/retrofit/2.0.0-beta2
public interface ShipApi {
	
	 @GET("api/ship/whoami")
	 Call<ResponseItem<WhoAmI>> WhoAmi();
	 
	 @POST("token")
	 @FormUrlEncoded
	 Call<AccessToken> Login( 
	    		@Field("username") String username,
	    		@Field("password") String password,
	    		@Field("client_id") String client_id,
	    		@Field("grant_type") String grant_type);
	
	
	 @POST("api/ship/UpdateWhoAmI")
	Call<ResponseItem<WhoAmI>> UpdateWhoAmI(@Body WhoAmI body);
	 
	 @POST("api/ship/UpdateWhoAmISync")
	Call<ItemSyncGeneric<WhoAmI>> UpdateWhoAmISync(@Body ItemSyncGeneric<WhoAmI> body);
}

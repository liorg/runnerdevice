package il.co.runnerdevice.Api;

import java.io.IOException;

import android.util.Base64;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.*;

import retrofit2.converter.gson.GsonConverterFactory;
//https://futurestud.io/blog/android-basic-authentication-with-retrofit

public class ServiceGenerator {

    public static final String API_BASE_URL = "http://testkipo.kipodeal.co.il";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder  =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }


    public static <S> S createService(Class<S> serviceClass, final String authToken) {  
        if (authToken != null) {
        	httpClient.addInterceptor(new Interceptor() {

				@Override
				public Response intercept(Chain chain) throws IOException {
					// TODO Auto-generated method stub
					  Request original = chain.request();

	                    Request.Builder requestBuilder = original.newBuilder()
	                        .header("Authorization", authToken)
	                        .header("Accept", "application/json")
	                        .header("content-type", "application/json")
	                        .method(original.method(), original.body());

	                    Request request = requestBuilder.build();
	                    return chain.proceed(request);
				}
                
             });
        }
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
   
}

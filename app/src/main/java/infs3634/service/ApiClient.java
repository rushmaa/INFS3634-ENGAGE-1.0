package infs3634.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
//    public static final String BASE_URL = "http://localhost/infs3634/public/index.php/";
//    public static final String BASE_URL = "http://infs3634proj.x10host.com/public/index.php/";
    public static final String BASE_URL = "http://infs3634.alphacomma.xyz/";
    public static ApiEndpointInterface apiService;

    public static ApiEndpointInterface getInstance() {
        if (apiService == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            apiService =
                    retrofit.create(ApiEndpointInterface.class);
        }
        return apiService;

    }
}

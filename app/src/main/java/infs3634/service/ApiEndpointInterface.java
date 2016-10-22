package infs3634.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import infs3634.pojo.*;

public interface ApiEndpointInterface {
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    @GET("users/{userId}")
    Call<User> getUserById(@Path("userId") String userId);

    @GET("users")
    Call<List<User>> getUsers();

    @POST("users/login")
    Call<User> login(@Body User user);

    @POST("users")
    Call<User> saveOrUpdateUser(@Body User user);

    @DELETE("users/{userId}")
    Call<Void> deleteUser(@Path("userId") String userId);

    @GET("admins")
    Call<List<User>> getAdmins();

    @GET("students")
    Call<List<User>> getStudents();

    @GET("users/{userId}/journals")
    Call<List<Journal>> getUserJounrals(@Path("userId") String userId);

    @GET("journals/{journalId}")
    Call<List<Journal>> getJounralById(@Path("journalId") String journalId);

    @POST("journals")
    Call<Journal> saveOrUpdateJournal(@Body Journal journal);

    @DELETE("journals/{journalId}")
    Call<Void> deleteJournal(@Path("journalId") String journalId);

}

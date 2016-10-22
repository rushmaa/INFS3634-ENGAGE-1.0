package infs3634.journalapp;

import org.junit.Test;

import infs3634.pojo.User;
import infs3634.service.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void testUserDao() throws Exception {

        Call<User> call = ApiClient.getInstance().getUserById("1");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User u = response.body();
                System.out.println(u.getUsername());
                assertEquals("tutor1", u.getUsername());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Log error here since request failed
            }
        });
    }
}
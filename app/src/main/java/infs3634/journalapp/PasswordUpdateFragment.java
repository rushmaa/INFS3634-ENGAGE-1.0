package infs3634.journalapp;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.IOException;

import infs3634.journalapp.databinding.FragmentAccountUpdateBinding;
import infs3634.journalapp.databinding.FragmentPasswordUpdateBinding;
import infs3634.pojo.User;
import infs3634.service.ApiClient;
import retrofit2.Call;
import retrofit2.Response;


public class PasswordUpdateFragment extends Fragment {
    private User user;
    private View view;
    private UpdateUserTask updateUserTask;


    public PasswordUpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.user = (User) getArguments().getSerializable("user");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentPasswordUpdateBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_password_update, container, false);
        View view = binding.getRoot();
        binding.setUser(this.user);
        binding.setFragment(this);
        this.view = view;

        ((BaseActivity) getActivity()).getSupportActionBar().setTitle("Update Password");
        return view;
    }

    public void save() {
        EditText et1 = (EditText) view.findViewById(R.id.password_update_form_password);
        et1.setError(null);
        if (et1.getText().toString().isEmpty()) {
            et1.setError("Please fill this field");
            et1.requestFocus();
            return;
        }

        if (!user.getPassword().equals(et1.getText().toString())) {
            et1.setError("Wrong password, please try again");
            et1.requestFocus();
            return;
        }

        EditText et2 = (EditText) view.findViewById(R.id.password_update_form_password2);
        et2.setError(null);
        if (et2.getText().toString().isEmpty()) {
            et2.setError("Please fill this field");
            et2.requestFocus();
            return;
        }

        EditText et3 = (EditText) view.findViewById(R.id.password_update_form_password3);
        et3.setError(null);
        if (!et3.getText().toString().equals(et2.getText().toString())) {
            et3.setError("New password is not identical");
            et3.requestFocus();
            return;
        }

        user.setPassword(et2.getText().toString());

        Call<User> call = ApiClient.getInstance().saveOrUpdateUser(user);
        updateUserTask = new UpdateUserTask();
        updateUserTask.execute(call);
    }

    public class UpdateUserTask extends AsyncTask<Call, Void, User> {

        @Override
        protected User doInBackground(Call... params) {

            try {
                Call<User> call = params[0];
                Response<User> response = call.execute();
                User user = response.body();
                return user;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(final User user) {
            updateUserTask = null;

            if (user != null) {
                Snackbar.make(view.getRootView().findViewById(android.R.id.content), "Updated successful", Snackbar.LENGTH_LONG)
                        .show();
            } else {
                Snackbar.make(view.getRootView().findViewById(android.R.id.content), "Updated Failed", Snackbar.LENGTH_LONG)
                        .show();
            }
        }

        @Override
        protected void onCancelled() {
            updateUserTask = null;
        }
    }
}

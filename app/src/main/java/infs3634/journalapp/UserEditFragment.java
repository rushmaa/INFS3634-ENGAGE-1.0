package infs3634.journalapp;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.IOException;

import infs3634.journalapp.databinding.FragmentUserDetailBinding;
import infs3634.journalapp.databinding.FragmentUserEditBinding;
import infs3634.pojo.Journal;
import infs3634.pojo.User;
import infs3634.service.ApiClient;
import retrofit2.Call;
import retrofit2.Response;

public class UserEditFragment extends Fragment {
    private User user;
    private View view;
    private UpdateUserTask updateUserTask;

    public UserEditFragment() {
        // Requird empty public constructor
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
        FragmentUserEditBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_user_edit, container, false);
        View view = binding.getRoot();
        binding.setUser(this.user);
        binding.setFragment(this);
        this.view = view;

        if (user.getIsAdmin().equals("1")) {
            ((BaseActivity) getActivity()).getSupportActionBar().setTitle("Add Tutor");
        } else {
            ((BaseActivity) getActivity()).getSupportActionBar().setTitle("Add Student");
        }

        return view;
    }

    public void back() {
        if (this.user.getIsAdmin().equals("1")) {
            ((AdminActivity) getActivity()).showTutorsFragment();
        } else {
            ((AdminActivity) getActivity()).showStudentsFragment();
        }
    }

    public void save() {
        EditText et1 = (EditText) view.findViewById(R.id.user_edit_form_username);
        et1.setError(null);
        if (et1.getText().toString().isEmpty()) {
            et1.setError("Please fill this field");
            et1.requestFocus();
            return;
        }
        user.setUsername(et1.getText().toString());
        EditText et2 = (EditText) view.findViewById(R.id.user_edit_form_password);
        et2.setError(null);
        if (et2.getText().toString().isEmpty()) {
            et2.setError("Please fill this field");
            et2.requestFocus();
            return;
        }
        user.setPassword(et2.getText().toString());
        EditText et3 = (EditText) view.findViewById(R.id.user_edit_form_name);
        et3.setError(null);
        if (et3.getText().toString().isEmpty()) {
            et3.setError("Please fill this field");
            et3.requestFocus();
            return;
        }
        user.setName(et3.getText().toString());

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
                back();
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

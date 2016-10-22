package infs3634.journalapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import infs3634.pojo.User;
import infs3634.service.ApiClient;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnUsersFragmentInteractionListener}
 * interface.
 */
public class UsersFragment extends Fragment {

    public static final String ARG_USER_TYPE = "user-type";
    public static final int ARG_USER_TYPE_STUDENT = 0;
    public static final int ARG_USER_TYPE_TUTOR = 1;
    private int user_type = 0;
    private OnUsersFragmentInteractionListener mListener;
    private FetchUsersTask fetchUsersTask;
    private UserRecyclerViewAdapter userRecyclerViewAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UsersFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user_type = getArguments().getInt(ARG_USER_TYPE);
        }
        setHasOptionsMenu(true);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (user_type == ARG_USER_TYPE_STUDENT) {
            inflater.inflate(R.menu.admin_student_menu, menu);
        } else {
            inflater.inflate(R.menu.admin_tutor_menu, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context).build());
            this.userRecyclerViewAdapter = new UserRecyclerViewAdapter(new ArrayList<User>(), mListener);
            recyclerView.setAdapter(userRecyclerViewAdapter);
        }


        // Do ajax call
        Call<List<User>> call;
        if (user_type == ARG_USER_TYPE_STUDENT) {
            call = ApiClient.getInstance().getStudents();
        } else {
            call = ApiClient.getInstance().getAdmins();
        }
        fetchUsersTask = new FetchUsersTask();
        fetchUsersTask.execute(call);
        if (user_type == ARG_USER_TYPE_STUDENT) {
            ((AdminActivity) getActivity()).getSupportActionBar().setTitle("Students");
        } else {
            ((AdminActivity) getActivity()).getSupportActionBar().setTitle("Tutors");
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUsersFragmentInteractionListener) {
            mListener = (OnUsersFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnJounralsFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnUsersFragmentInteractionListener {
        void onListFragmentInteraction(User item);
    }


    public class FetchUsersTask extends AsyncTask<Call, Void, List<User>> {

        @Override
        protected List<User> doInBackground(Call... params) {

            try {
                Call<List<User>> call = params[0];
                Response<List<User>> response = call.execute();
                List<User> users = response.body();
                return users;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(final List<User> users) {
            fetchUsersTask = null;

            if (users != null) {
                userRecyclerViewAdapter.updateItems(users);
            } else {
                System.out.println("No result");
            }
        }

        @Override
        protected void onCancelled() {
            fetchUsersTask = null;
        }
    }

}

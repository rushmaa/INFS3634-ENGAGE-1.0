package infs3634.journalapp;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import infs3634.pojo.User;
import infs3634.journalapp.databinding.FragmentUserDetailBinding;

public class UserDetailFragment extends Fragment {
    private User user;

    public UserDetailFragment() {
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
        FragmentUserDetailBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_user_detail, container, false);
        View view = binding.getRoot();
        binding.setUser(this.user);
        binding.setFragment(this);

        if (this.user.getIsAdmin().equals("1")) {
            Button button = (Button) view.findViewById(R.id.show_user_journals);
            button.setVisibility(View.GONE);
        }

        if (user.getIsAdmin().equals("1")) {
            ((BaseActivity) getActivity()).getSupportActionBar().setTitle("Tutor Detail");
        } else {
            ((BaseActivity) getActivity()).getSupportActionBar().setTitle("Student Detail");
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

    public void showStudentJournalsdFragment() {
        ((AdminActivity) getActivity()).showStudentJournalsdFragment(user);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.user = null;
    }
}
package infs3634.journalapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import infs3634.model.Consultation;
import infs3634.pojo.User;

public class JournalsFragment extends Fragment {

    private JournalRecyclerViewAdapter journalRecyclerViewAdapter;
    private User user;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public JournalsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.user = (User) getArguments().getSerializable("user");
        }
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (getActivity() instanceof AdminActivity) {
        } else {
            inflater.inflate(R.menu.student_journal_menu, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journal_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context).build());

            journalRecyclerViewAdapter = new JournalRecyclerViewAdapter(this.user);
            recyclerView.setAdapter(journalRecyclerViewAdapter);
        }

        if (getActivity() instanceof AdminActivity) {
            ((AdminActivity) getActivity()).getSupportActionBar().setTitle(user.getUsername() + "'s Journals");
        } else {
            ((StudentActivity) getActivity()).getSupportActionBar().setTitle("Journals");
        }
        return view;
    }


}
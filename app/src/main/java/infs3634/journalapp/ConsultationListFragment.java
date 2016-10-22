package infs3634.journalapp;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import infs3634.model.Consultation;
import infs3634.storage.AppDBHelper;
import infs3634.storage.ConsultationDBAdapter;
import infs3634.storage.tables.ConsultationContract;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConsultationListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ConsultationRecyclerViewAdapter adapter;
    //private AppDBHelper appDBHelper;
    private List<Consultation> consultationList = new ArrayList<Consultation>();
    private ConsultationDBAdapter dbAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbAdapter = new ConsultationDBAdapter(getActivity().getBaseContext());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (getActivity() instanceof ConsultationActivity) {
        } else {
            inflater.inflate(R.menu.admin_consultation_menu, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        consultationList = dbAdapter.getAllConsultations();

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_consultation_list, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.consultationList);
        adapter = new ConsultationRecyclerViewAdapter(consultationList, getActivity(), getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public interface OnConsultationFragmentInteractionListener {
        void onListFragmentInteraction(Consultation item);
    }
}

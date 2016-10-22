package infs3634.journalapp;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import infs3634.journalapp.databinding.FragmentJournalDetailBinding;
import infs3634.pojo.Journal;
import infs3634.service.ApiClient;
import retrofit2.Call;
import retrofit2.Response;


public class JournalDetailFragment extends Fragment {

    private Journal journal;
    private UpdateJournalTask updateJournalTask;
    private DeleteJournalTask deleteJournalTask;
    private View view;

    public JournalDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.journal = (Journal) getArguments().getSerializable("journal");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentJournalDetailBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_journal_detail, container, false);
        View view = binding.getRoot();
        binding.setJournal(this.journal);
        binding.setFragment(this);

        if (getActivity() instanceof AdminActivity) {
            View actionBarView = view.findViewById(R.id.journal_detail_actionbar);
            actionBarView.setVisibility(View.GONE);
            EditText titleEt = (EditText) view.findViewById(R.id.journal_detail_form_title);
            EditText contentEt = (EditText) view.findViewById(R.id.journal_detail_form_content);
            titleEt.setFocusable(false);
            titleEt.setFocusableInTouchMode(false);
            titleEt.setCursorVisible(false);
            contentEt.setFocusable(false);
            contentEt.setFocusableInTouchMode(false);
            contentEt.setCursorVisible(false);
        }

        Button deleteBtn = (Button) view.findViewById(R.id.journal_detail_form_delete_button);
        if (this.journal.getJournalId() == null) {
            deleteBtn.setVisibility(View.GONE);
        }

        if (journal.getJournalId() == null) {
            ((BaseActivity) getActivity()).getSupportActionBar().setTitle("Add Jounral");
        } else {
            ((BaseActivity) getActivity()).getSupportActionBar().setTitle("Journal Detail Page");
        }

        this.view = view;
        return view;
    }

    public void saveOrUpdate(Journal journal) {
        EditText titleEt = (EditText) view.findViewById(R.id.journal_detail_form_title);
        titleEt.setError(null);
        if (titleEt.getText().toString().isEmpty()) {
            titleEt.setError("Please fill this field");
            titleEt.requestFocus();
            return;
        }
        journal.setJournalTitle(titleEt.getText().toString());
        EditText contentEt = (EditText) view.findViewById(R.id.journal_detail_form_content);
        contentEt.setError(null);
        if (contentEt.getText().toString().isEmpty()) {
            contentEt.setError("Please fill this field");
            contentEt.requestFocus();
            return;
        }
        journal.setJournalContent(contentEt.getText().toString());

        Call<Journal> call = ApiClient.getInstance().saveOrUpdateJournal(journal);
        updateJournalTask = new UpdateJournalTask();
        updateJournalTask.execute(call);
    }

    public void delete() {
        Call<Void> call = ApiClient.getInstance().deleteJournal(journal.getJournalId());
        deleteJournalTask = new DeleteJournalTask();
        deleteJournalTask.execute(call);
    }


    public void back() {
        ((StudentActivity) getActivity()).showJournalsFragment();
    }


    public class UpdateJournalTask extends AsyncTask<Call, Void, Journal> {

        @Override
        protected Journal doInBackground(Call... params) {

            try {
                Call<Journal> call = params[0];
                Response<Journal> response = call.execute();
                Journal journal = response.body();
                return journal;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(final Journal journal) {
            updateJournalTask = null;

            if (journal != null) {
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
            updateJournalTask = null;
        }
    }

    public class DeleteJournalTask extends AsyncTask<Call, Void, Void> {

        @Override
        protected Void doInBackground(Call... params) {

            try {
                Call<Void> call = params[0];
                Response<Void> resposne = call.execute();
                return resposne.body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(final Void _void) {
            deleteJournalTask = null;
            back();
        }

        @Override
        protected void onCancelled() {
            deleteJournalTask = null;
        }
    }
}

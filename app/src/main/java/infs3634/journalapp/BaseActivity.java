package infs3634.journalapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import infs3634.pojo.Journal;


public class BaseActivity extends AppCompatActivity {
    public void showJournalDetailFragment(Journal journal) {
        Bundle arguments = new Bundle();
        arguments.putSerializable("journal", journal);
        JournalDetailFragment fragment = new JournalDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.admin_container, fragment)
                .addToBackStack("Jounral Detail")
                .commit();
    }
}

package infs3634.journalapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import infs3634.model.Consultation;
import infs3634.pojo.User;

import static java.security.AccessController.getContext;

public class ConsultationActivity extends AppCompatActivity /* extends BaseActivity implements ConsultationListFragment.OnConsultationFragmentInteractionListener, FragmentManager.OnBackStackChangedListener */{


    public static final String CONSULTATION_ID = "infs3634.journalapp.ConsultationID ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);

        ConsultationListFragment fragment = new ConsultationListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_consultation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("Click item");
//        switch (item.getItemId()) {
//            case R.id.action_student_add_journal:
                //Consultation consultation = new Consultation();
                showNewConsultationDetailFragment();
//                break;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void showNewConsultationDetailFragment() {

        Intent intent = new Intent(getBaseContext(), ConsultationDetailActivity.class);
        intent.putExtra(ConsultationActivity.CONSULTATION_ID, -1);
        startActivity(intent);
    }


}

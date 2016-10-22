package infs3634.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import infs3634.pojo.Journal;
import infs3634.pojo.User;

public class StudentActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.user = (User) getIntent().getSerializableExtra("user");

        // First Time render fragment don't put into back trace
        getSupportActionBar().setTitle("Jounrals");
        Bundle arguments = new Bundle();
        arguments.putSerializable("user", this.user);
        JournalsFragment fragment = new JournalsFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.admin_container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_student_add_journal:
                Journal journal = new Journal();
                journal.setUserId(user.getUserId());
                showJournalDetailFragment(journal);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_journals) {
            showJournalsFragment();
        } else if (id == R.id.nav_my_profile) {
            showAccountUpdateFragment();
        } else if (id == R.id.nav_change_password) {
            showChangePasswordFragment();
        } else if (id == R.id.nav_dropbox) {
            showDropboxFragment();
        } else if (id == R.id.nav_logout) {
            startActivity(new Intent(getApplication(), LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showJournalsFragment() {
        // render user Fragment by default
        Bundle arguments = new Bundle();
        arguments.putSerializable("user", this.user);
        JournalsFragment fragment = new JournalsFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.admin_container, fragment)
                .addToBackStack("Jounrals")
                .commit();
    }

    public void showAccountUpdateFragment() {
        Bundle arguments = new Bundle();
        arguments.putSerializable("user", user);
        AccountUpdateFragment fragment = new AccountUpdateFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.admin_container, fragment)
                .addToBackStack("My Profile")
                .commit();
    }

    public void showDropboxFragment() {
        Bundle arguments = new Bundle();
        arguments.putSerializable("user", user);
        DropboxFragment fragment = new DropboxFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.admin_container, fragment)
                .addToBackStack("Dropbox")
                .commit();
    }

    public void showChangePasswordFragment() {
        Bundle arguments = new Bundle();
        arguments.putSerializable("user", user);
        PasswordUpdateFragment fragment = new PasswordUpdateFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.admin_container, fragment)
                .addToBackStack("Change Password")
                .commit();
    }


}

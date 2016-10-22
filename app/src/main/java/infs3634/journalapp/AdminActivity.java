package infs3634.journalapp;

import android.app.FragmentManager;
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

import infs3634.pojo.User;

public class AdminActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, UsersFragment.OnUsersFragmentInteractionListener, FragmentManager.OnBackStackChangedListener {
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
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

        // first time render , don't put into back tracks
        getSupportActionBar().setTitle("Students");
        Bundle arguments = new Bundle();
        arguments.putInt(UsersFragment.ARG_USER_TYPE, UsersFragment.ARG_USER_TYPE_STUDENT);
        UsersFragment fragment = new UsersFragment();
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
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }


    public void shouldDisplayHomeUp() {
        //Enable Up button only  if there are entries in the back stack
        boolean canback = getSupportFragmentManager().getBackStackEntryCount() > 0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.admin, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_admin_add_student:
                showAddStudentFragment();
                break;
            case R.id.action_admin_add_tutor:
                showAddTutorFragment();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_students) {
            showStudentsFragment();
        } else if (id == R.id.nav_tutors) {
            showTutorsFragment();
        } else if (id == R.id.nav_my_profile) {
            showAccountUpdateFragment();
        } else if (id == R.id.nav_change_password) {
            showChangePasswordFragment();
        } else if (id == R.id.nav_dropbox) {
            showDropboxFragment();
        } else if (id == R.id.nav_consulation) {
            // init consulation activity
            Intent intent = new Intent(getApplication(), ConsultationActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            startActivity(new Intent(getApplication(), LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showAddStudentFragment() {
        User user = new User();
        user.setIsAdmin("0");
        Bundle arguments = new Bundle();
        arguments.putSerializable("user", user);
        UserEditFragment fragment = new UserEditFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.admin_container, fragment)
                .addToBackStack("Add Student")
                .commit();
    }

    public void showAddTutorFragment() {
        User user = new User();
        user.setIsAdmin("1");
        Bundle arguments = new Bundle();
        arguments.putSerializable("user", user);
        UserEditFragment fragment = new UserEditFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.admin_container, fragment)
                .addToBackStack("Add Tutor")
                .commit();
    }

    public void showStudentsFragment() {
        // render user Fragment by default
        Bundle arguments = new Bundle();
        arguments.putInt(UsersFragment.ARG_USER_TYPE, UsersFragment.ARG_USER_TYPE_STUDENT);
        UsersFragment fragment = new UsersFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.admin_container, fragment)
                .addToBackStack("Students")
                .commit();
    }

    public void showTutorsFragment() {
        Bundle arguments = new Bundle();
        arguments.putInt(UsersFragment.ARG_USER_TYPE, UsersFragment.ARG_USER_TYPE_TUTOR);
        UsersFragment fragment = new UsersFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.admin_container, fragment)
                .addToBackStack("Tutors")
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

    public void showStudentJournalsdFragment(User user) {
        Bundle arguments = new Bundle();
        arguments.putSerializable("user", user);
        JournalsFragment fragment = new JournalsFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.admin_container, fragment)
                .addToBackStack("Journals")
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

    @Override
    public void onListFragmentInteraction(User user) {
        Bundle arguments = new Bundle();
        arguments.putSerializable("user", user);
        UserDetailFragment fragment = new UserDetailFragment();
        fragment.setArguments(arguments);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.admin_container, fragment)
                .addToBackStack("User Detail")
                .commit();
    }
}

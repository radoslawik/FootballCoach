package footballcoach.com.footballcoach;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private MatchAdapter adapter;
    private ArrayList<Match> matchList;
    private Intent intent;
    private final String TEAM_NAME = "MyTeam";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddMatchActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        matchList = initializeMatchList();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MatchAdapter(this, matchList);
        recyclerView.setAdapter(adapter);

        intent = new Intent(this, MatchDisplayActivity.class);
        intent.putParcelableArrayListExtra("data", matchList);

        adapter.setOnItemClickListener(new MatchAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                System.out.println("Clicked item number:");
                System.out.println(position);
                intent.putExtra("pos", position);
                startActivity(intent);
            }
        });

        System.out.println("mainAct");
        System.out.println(matchList.get(0).getScore());
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public ArrayList<Match> initializeMatchList(){
        ArrayList<Match> res = new ArrayList<>();

        Date c = Calendar.getInstance().getTime();

        res.add(new Match(
                1,
                TEAM_NAME,
                "YourTeam1",
                "Friendly",
                c,
                new TeamStats(1),
                new TeamStats(1),
                R.drawable.ic_menu_camera
                ));
        res.add(new Match(
                1,
                TEAM_NAME,
                "YourTeam2",
                "Cup",
                c,
                new TeamStats(2),
                new TeamStats(1),
                R.drawable.ic_menu_camera
        ));
        res.add(new Match(
                1,
                TEAM_NAME,
                "YourTeam3",
                "League",
                c,
                new TeamStats(3),
                new TeamStats(4),
                R.drawable.ic_menu_camera
        ));
        res.add(new Match(
                1,
                TEAM_NAME,
                "YourTeam4",
                "League",
                c,
                new TeamStats(5),
                new TeamStats(1),
                R.drawable.ic_menu_camera
        ));
        res.add(new Match(
                1,
                TEAM_NAME,
                "YourTeam5",
                "League",
                c,
                new TeamStats(1),
                new TeamStats(0),
                R.drawable.ic_menu_camera
        ));
        res.add(new Match(
                1,
                TEAM_NAME,
                "YourTeam6",
                "League",
                c,
                new TeamStats(2),
                new TeamStats(2),
                R.drawable.ic_menu_camera
        ));
        return res;
    }
}

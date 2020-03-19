package footballcoach.com.footballcoach;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RenameTeamDialog.RenameTeamListener {

    private RecyclerView recyclerView;
    private MatchAdapter adapter;
    private ArrayList<Match> matchList;
    private Intent intent;
    private String TEAM_NAME = "MyTeam";
    private int ADD_MATCH_RESULT = 1;
    private boolean EDIT_MODE = false;
    private boolean DELETE_MODE = false;

    private TextView tvTeamName, tvEmpty;

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
                Intent intentAddMatch =  new Intent(MainActivity.this, AddMatchActivity.class);
                startActivityForResult(intentAddMatch, ADD_MATCH_RESULT);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        tvTeamName = headerView.findViewById(R.id.tvTeamName);

        tvEmpty = (TextView)findViewById(R.id.tvEmpty);
        //matchList = initializeMatchList();
        matchList = new ArrayList<>();
        checkIfEmpty();

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
                if(DELETE_MODE){
                    matchList.remove(position);
                    checkIfEmpty();
                    adapter.notifyDataSetChanged();
                    DELETE_MODE = false;
                    Toast.makeText(getApplicationContext(),"Match deleted",Toast.LENGTH_SHORT).show();
                } else if (EDIT_MODE) {
                    Toast.makeText(getApplicationContext(),"Edit match feature not available",Toast.LENGTH_SHORT).show();
                    EDIT_MODE = false;
                } else {
                    startActivity(intent);
                }
            }
        });
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

        if (id == R.id.nav_add) {
            Intent intentAddMatch =  new Intent(MainActivity.this, AddMatchActivity.class);
            startActivityForResult(intentAddMatch, ADD_MATCH_RESULT);
        } else if (id == R.id.nav_delete) {
            if(matchList.isEmpty()){
                Toast.makeText(this,"No item to delete",Toast.LENGTH_SHORT).show();
            } else {
                EDIT_MODE = false;
                DELETE_MODE = true;
                Toast.makeText(this,"Click on the item to delete",Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_edit) {
            if(matchList.isEmpty()) {
                Toast.makeText(this,"No item to modify",Toast.LENGTH_SHORT).show();
            } else {
                DELETE_MODE = false;
                EDIT_MODE = true;
                Toast.makeText(this,"Click on the item to modify",Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_rename) {
            RenameTeamDialog exampleDialog = new RenameTeamDialog();
            exampleDialog.show(getSupportFragmentManager(), "rename team dialog");
        } else {
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_MATCH_RESULT) {
            if(resultCode == Activity.RESULT_OK){
                Match result = data.getParcelableExtra("newMatch");
                result.homeName = TEAM_NAME;
                matchList.add(0, result );
                checkIfEmpty();
                adapter.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

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

    @Override
    public void sendText(String teamName) {
        if(teamName.length()>16 || teamName.isEmpty()){
            Toast.makeText(this,"Invalid team name",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"Team name changed successfully",Toast.LENGTH_SHORT).show();
            TEAM_NAME = teamName;
            tvTeamName.setText(TEAM_NAME);
        }
        return;
    }

    public void checkIfEmpty(){
        if(matchList.isEmpty()){
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);
        }
        return;
    }
}

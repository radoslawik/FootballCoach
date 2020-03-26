package footballcoach.com.footballcoach;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;
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
    private String TEAM_NAME;
    private int ADD_MATCH_RESULT = 1;
    private boolean EDIT_MODE = false;
    private boolean DELETE_MODE = false;
    private LocalDatabaseHelper localDbHelper;
    private SQLiteDatabase localDb;
    private ExternalDatabase externalDb;
    private SharedPreferences sharedPref;

    private TextView tvTeamName, tvEmpty;
    private RelativeLayout rlMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // get instance of toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false); // hide app title

        // floating add button initialization
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddMatch =  new Intent(MainActivity.this, AddMatchActivity.class);
                startActivityForResult(intentAddMatch, ADD_MATCH_RESULT);
            }
        });

        // drawer initialization
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // initialize navigation view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        tvTeamName = headerView.findViewById(R.id.tvTeamName);

        // read team name from shared preferences
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        TEAM_NAME = sharedPref.getString("teamName", "myTeam"); // if not there use default
        tvTeamName.setText(TEAM_NAME);

        //get local database instance
        localDbHelper = new LocalDatabaseHelper(this);
        localDb = localDbHelper.getWritableDatabase();
        //afterTableChange(); // if db design was changed
        externalDb = new ExternalDatabase();

        // if opened for the first time read from database, otherwise read from saved state
        if(savedInstanceState == null){
            matchList = readFromLocalDatabase();
        } else {
            matchList = savedInstanceState.getParcelableArrayList("myList");
        }

        tvEmpty = (TextView)findViewById(R.id.tvEmpty);
        checkIfEmpty(); // if matchlist is empty show text

        //initialize recycler view and adjust design to current orientation
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        rlMain = (RelativeLayout)findViewById(R.id.rlMain);
        int currentOrientation = this.getResources().getConfiguration().orientation;
        if(currentOrientation == Configuration.ORIENTATION_LANDSCAPE){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            rlMain.setBackground(getResources().getDrawable(R.drawable.fcbackground_landscape));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            rlMain.setBackground(getResources().getDrawable(R.drawable.fcbackground_portrait));
        }
        adapter = new MatchAdapter(this, matchList);
        recyclerView.setAdapter(adapter);

        // prepare to start display activity
        intent = new Intent(this, MatchDisplayActivity.class);
        intent.putParcelableArrayListExtra("data", matchList);

        adapter.setOnItemClickListener(new MatchAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                System.out.println("Clicked item number:");
                System.out.println(position);
                intent.putExtra("pos", position);
                if(DELETE_MODE){
                    long timestampToDelete = matchList.get(position).getGameId(); // get a timestamp of deleted match
                    String[] tsToDelete = { String.valueOf(timestampToDelete) };
                    matchList.remove(position); // remove clicked match
                    checkIfEmpty(); // if no matches show the textview
                    adapter.notifyDataSetChanged(); // update recycler view adapter
                    localDb.delete(localDbHelper.getTableName(), "game_id=?", tsToDelete); // delete from local
                    //externalDb.deleteMatch(timestampToDelete); // delete from external
                    DELETE_MODE = false; // exit delete mode
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

        if (id == R.id.nav_stats){
            if(!matchList.isEmpty()){
                Intent intentShowStats =  new Intent(MainActivity.this, ShowStatsActivity.class);
                intentShowStats.putParcelableArrayListExtra("data", matchList);
                startActivity(intentShowStats);
            } else {
                Toast.makeText(getApplicationContext(),"There is no stats to show",Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_add) {
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
                result.gameId = System.nanoTime();
                matchList.add(0, result );
                checkIfEmpty();
                addToDatabase(result);
                adapter.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    @Override
    public void sendNewTeamName(String teamName) {
        if(teamName.length()>16 || teamName.isEmpty()){
            Toast.makeText(this,"Invalid team name",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"Team name changed successfully",Toast.LENGTH_SHORT).show();
            TEAM_NAME = teamName;
            tvTeamName.setText(TEAM_NAME);
            sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("teamName", TEAM_NAME);
            editor.apply();
        }
    }

    public void checkIfEmpty(){
        if(matchList.isEmpty()){
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);
        }
        return;
    }

    public void addToDatabase(Match m){
        localDb = localDbHelper.getWritableDatabase();
        ContentValues newRow = new ContentValues();
        List<String> colNames = localDbHelper.getColNames();
        newRow.put(colNames.get(0),  m.getGameId());
        newRow.put(colNames.get(1),  m.getHomeName());
        newRow.put(colNames.get(2),  m.getOpponentName());
        newRow.put(colNames.get(3),  m.getGameType());
        newRow.put(colNames.get(4),  m.getPlayedWhen());
        newRow.put(colNames.get(5),  m.getPlayedWhere());
        newRow.put(colNames.get(6),  m.getImagePath());
        newRow.put(colNames.get(7),  m.homeStats.getScored());
        newRow.put(colNames.get(8),  m.homeStats.getTotal_attemps());
        newRow.put(colNames.get(9),  m.homeStats.getOn_target());
        newRow.put(colNames.get(10), m.homeStats.getPossesion());
        newRow.put(colNames.get(11), m.homeStats.getPasses());
        newRow.put(colNames.get(12), m.homeStats.getPass_acc());
        newRow.put(colNames.get(13), m.homeStats.getFouls());
        newRow.put(colNames.get(14), m.homeStats.getYellow_cards());
        newRow.put(colNames.get(15), m.homeStats.getRed_cards());
        newRow.put(colNames.get(16), m.homeStats.getOffsides());
        newRow.put(colNames.get(17), m.homeStats.getPenalties());
        newRow.put(colNames.get(18), m.homeStats.getCorners());
        newRow.put(colNames.get(19), m.awayStats.getScored());
        newRow.put(colNames.get(20), m.awayStats.getTotal_attemps());
        newRow.put(colNames.get(21), m.awayStats.getOn_target());
        newRow.put(colNames.get(22), m.awayStats.getPossesion());
        newRow.put(colNames.get(23), m.awayStats.getPasses());
        newRow.put(colNames.get(24), m.awayStats.getPass_acc());
        newRow.put(colNames.get(25), m.awayStats.getFouls());
        newRow.put(colNames.get(26), m.awayStats.getYellow_cards());
        newRow.put(colNames.get(27), m.awayStats.getRed_cards());
        newRow.put(colNames.get(28), m.awayStats.getOffsides());
        newRow.put(colNames.get(29), m.awayStats.getPenalties());
        newRow.put(colNames.get(30), m.awayStats.getCorners());
        localDb.insert(localDbHelper.getTableName(), null, newRow);

        //now external db
        //externalDb.addNewMatch(m);
    }

    public ArrayList<Match> readFromLocalDatabase(){
        ArrayList<Match> retList = new ArrayList<>();
        localDb = localDbHelper.getReadableDatabase();
        List<String> colNames = localDbHelper.getColNames();
        String sortOrder = colNames.get(0)+ " DESC";
        Cursor cursor = localDb.query(
                localDbHelper.getTableName(), null, null, null, null, null, sortOrder);
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(colNames.get(0)));
            String itemHomeName = cursor.getString(cursor.getColumnIndexOrThrow(colNames.get(1)));
            String itemAwayName = cursor.getString(cursor.getColumnIndexOrThrow(colNames.get(2)));
            String itemGameType = cursor.getString(cursor.getColumnIndexOrThrow(colNames.get(3)));
            String itemDate = cursor.getString(cursor.getColumnIndexOrThrow(colNames.get(4)));
            String itemLocation = cursor.getString(cursor.getColumnIndexOrThrow(colNames.get(5)));
            String itemImagePath = cursor.getString(cursor.getColumnIndexOrThrow(colNames.get(6)));
            TeamStats home = new TeamStats(
                    cursor.getInt(cursor.getColumnIndexOrThrow(colNames.get(7))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(colNames.get(8))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(colNames.get(9))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(colNames.get(10))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(colNames.get(11))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(colNames.get(12))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(colNames.get(13))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(colNames.get(14))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(colNames.get(15))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(colNames.get(16))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(colNames.get(17))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(colNames.get(18)))
            );
            TeamStats away = new TeamStats(
                    cursor.getInt(cursor.getColumnIndexOrThrow(colNames.get(19))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(colNames.get(20))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(colNames.get(21))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(colNames.get(22))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(colNames.get(23))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(colNames.get(24))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(colNames.get(25))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(colNames.get(26))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(colNames.get(27))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(colNames.get(28))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(colNames.get(29))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(colNames.get(30)))
            );
            retList.add(new Match(
                    itemId,
                    itemHomeName,
                    itemAwayName,
                    itemGameType,
                    itemDate,
                    itemLocation,
                    home,
                    away,
                    itemImagePath
            ));
            System.out.println("game id");
            System.out.println(itemId);
            System.out.println("game date");
            System.out.println(itemDate);
            System.out.println("\n");
        }
        cursor.close();
        return retList;
    }

/*    public void afterTableChange(){
        localDb = localDbHelper.getWritableDatabase();
        localDb.execSQL("DROP TABLE IF EXISTS "+ localDbHelper.getTableName());
    }*/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("myList", matchList);
    }

}

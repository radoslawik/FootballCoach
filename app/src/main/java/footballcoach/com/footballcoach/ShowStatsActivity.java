package footballcoach.com.footballcoach;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowStatsActivity extends AppCompatActivity {

    private TabLayout tbPages;
    private ViewPager vpPages;
    private ShowStatsAdapter adapter;
    private ArrayList<Match> matchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_show_stats);

        Bundle bundle = getIntent().getExtras();
        matchList = bundle.getParcelableArrayList("data");

        tbPages = (TabLayout)findViewById(R.id.tbPages);
        tbPages.addTab(tbPages.newTab().setText(getResources().getString(R.string.stats_average)));
        tbPages.addTab(tbPages.newTab().setText(getResources().getString(R.string.stats_best)));
        tbPages.addTab(tbPages.newTab().setText(getResources().getString(R.string.stats_worst)));

        tbPages.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpPages.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        vpPages = (ViewPager)findViewById(R.id.vpPages);
        adapter = new ShowStatsAdapter(getSupportFragmentManager(), tbPages.getTabCount(),
                matchList.get(0).homeStats,
                matchList.get(0).homeStats,
                matchList.get(0).homeStats,
                matchList.get(0).homeStats,
                matchList.get(0).homeStats,
                matchList.get(0).homeStats);
        vpPages.setAdapter(adapter);
    }

    public TeamStats CalculateAvgHomeStats(){
        List<Integer> sums = new ArrayList<Integer>(Collections.nCopies(12, 0));
        int all = matchList.size();
        for(Match match : matchList){
            sums.set(0,sums.get(0)+match.homeStats.getScored());
            sums.set(1,sums.get(1)+match.homeStats.getTotal_attemps());
            sums.set(2,sums.get(2)+match.homeStats.getOn_target());
            sums.set(3,sums.get(3)+match.homeStats.getPossesion());
            sums.set(4,sums.get(4)+match.homeStats.getPasses());
            sums.set(5,sums.get(5)+match.homeStats.getPass_acc());
            sums.set(6,sums.get(6)+match.homeStats.getFouls());
            sums.set(7,sums.get(7)+match.homeStats.getYellow_cards());
            sums.set(8,sums.get(8)+match.homeStats.getRed_cards());
            sums.set(9,sums.get(9)+match.homeStats.getOffsides());
            sums.set(10,sums.get(10)+match.homeStats.getPenalties());
            sums.set(11,sums.get(11)+match.homeStats.getCorners());
        }
        return new TeamStats();
    }
}

package footballcoach.com.footballcoach;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
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

        // after we click on tab we change current page
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
        adapter = new ShowStatsAdapter(getSupportFragmentManager(), tbPages.getTabCount(), getStatsData());
        vpPages.setAdapter(adapter);

        // if we swipe instead of use tabs, tabs will be updated
        vpPages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                TabLayout.Tab tab = tbPages.getTabAt(position);
                tab.select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public float[] CalculateAvgHomeStats(){

        float f[] = {0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f};
        int all = matchList.size();
        for(Match match : matchList){
            f[0] = f[0] + (float)match.homeStats.getScored();
            f[1] = f[1] + (float)match.homeStats.getTotal_attemps();
            f[2] = f[2] + (float)match.homeStats.getOn_target();
            f[3] = f[3] + (float)match.homeStats.getPossesion();
            f[4] = f[4] + (float)match.homeStats.getPasses();
            f[5] = f[5] + (float)match.homeStats.getPass_acc();
            f[6] = f[6] + (float)match.homeStats.getFouls();
            f[7] = f[7] + (float)match.homeStats.getYellow_cards();
            f[8] = f[8] + (float)match.homeStats.getRed_cards();
            f[9] = f[9] + (float)match.homeStats.getOffsides();
            f[10] = f[10] + (float)match.homeStats.getPenalties();
            f[11] = f[11] + (float)match.homeStats.getCorners();
        }

        for(int i=0;i<f.length;i++){
            f[i] = f[i]/all;
        }
        return f;
    }

    public float[] CalculateAvgAwayStats(){

        float f[] = {0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f};
        int all = matchList.size();
        for(Match match : matchList){
            f[0] = f[0] + (float)match.awayStats.getScored();
            f[1] = f[1] + (float)match.awayStats.getTotal_attemps();
            f[2] = f[2] + (float)match.awayStats.getOn_target();
            f[3] = f[3] + (float)match.awayStats.getPossesion();
            f[4] = f[4] + (float)match.awayStats.getPasses();
            f[5] = f[5] + (float)match.awayStats.getPass_acc();
            f[6] = f[6] + (float)match.awayStats.getFouls();
            f[7] = f[7] + (float)match.awayStats.getYellow_cards();
            f[8] = f[8] + (float)match.awayStats.getRed_cards();
            f[9] = f[9] + (float)match.awayStats.getOffsides();
            f[10] = f[10] + (float)match.awayStats.getPenalties();
            f[11] = f[11] + (float)match.awayStats.getCorners();
        }

        for(int i=0;i<f.length;i++){
            f[i] = f[i]/all;
        }
        return f;
    }

    public float[] CalculateBestHomeStats(){
        float f[] = {0f,0f,0f,0f,0f,0f,999f,999f,999f,999f,0f,0f};
        for(Match match : matchList){
            f[0] = f[0] > (float)match.homeStats.getScored() ? f[0] : (float)match.homeStats.getScored();
            f[1] = f[1] > (float)match.homeStats.getTotal_attemps() ? f[1] : (float)match.homeStats.getTotal_attemps();
            f[2] = f[2] > (float)match.homeStats.getOn_target() ? f[2] : (float)match.homeStats.getOn_target();
            f[3] = f[3] > (float)match.homeStats.getPossesion() ? f[3] : (float)match.homeStats.getPossesion();
            f[4] = f[4] > (float)match.homeStats.getPasses() ? f[4] : (float)match.homeStats.getPasses();
            f[5] = f[5] > (float)match.homeStats.getPass_acc() ? f[5] : (float)match.homeStats.getPass_acc();
            f[6] = f[6] < (float)match.homeStats.getFouls() ? f[6] : (float)match.homeStats.getFouls();
            f[7] = f[7] < (float)match.homeStats.getYellow_cards() ? f[7] : (float)match.homeStats.getYellow_cards();
            f[8] = f[8] < (float)match.homeStats.getRed_cards() ? f[8] : (float)match.homeStats.getRed_cards();
            f[9] = f[9] < (float)match.homeStats.getOffsides() ? f[9] : (float)match.homeStats.getOffsides();
            f[10] = f[10] > (float)match.homeStats.getPenalties() ? f[10] : (float)match.homeStats.getPenalties();
            f[11] = f[11] > (float)match.homeStats.getCorners() ? f[11] : (float)match.homeStats.getCorners();
        }

        return f;
    }

    public float[] CalculateBestAwayStats(){
        float f[] = {0f,0f,0f,0f,0f,0f,999f,999f,999f,999f,0f,0f};
        for(Match match : matchList){
            f[0] = f[0] > (float)match.awayStats.getScored() ? f[0] : (float)match.awayStats.getScored();
            f[1] = f[1] > (float)match.awayStats.getTotal_attemps() ? f[1] : (float)match.awayStats.getTotal_attemps();
            f[2] = f[2] > (float)match.awayStats.getOn_target() ? f[2] : (float)match.awayStats.getOn_target();
            f[3] = f[3] > (float)match.awayStats.getPossesion() ? f[3] : (float)match.awayStats.getPossesion();
            f[4] = f[4] > (float)match.awayStats.getPasses() ? f[4] : (float)match.awayStats.getPasses();
            f[5] = f[5] > (float)match.awayStats.getPass_acc() ? f[5] : (float)match.awayStats.getPass_acc();
            f[6] = f[6] < (float)match.awayStats.getFouls() ? f[6] : (float)match.awayStats.getFouls();
            f[7] = f[7] < (float)match.awayStats.getYellow_cards() ? f[7] : (float)match.awayStats.getYellow_cards();
            f[8] = f[8] < (float)match.awayStats.getRed_cards() ? f[8] : (float)match.awayStats.getRed_cards();
            f[9] = f[9] < (float)match.awayStats.getOffsides() ? f[9] : (float)match.awayStats.getOffsides();
            f[10] = f[10] > (float)match.awayStats.getPenalties() ? f[10] : (float)match.awayStats.getPenalties();
            f[11] = f[11] > (float)match.awayStats.getCorners() ? f[11] : (float)match.awayStats.getCorners();
        }

        return f;
    }

    public float[] CalculateWorstHomeStats(){
        float f[] = {999f,999f,999f,999f,999f,999f,0f,0f,0f,0f,999f,999f};
        for(Match match : matchList){
            f[0] = f[0] < (float)match.homeStats.getScored() ? f[0] : (float)match.homeStats.getScored();
            f[1] = f[1] < (float)match.homeStats.getTotal_attemps() ? f[1] : (float)match.homeStats.getTotal_attemps();
            f[2] = f[2] < (float)match.homeStats.getOn_target() ? f[2] : (float)match.homeStats.getOn_target();
            f[3] = f[3] < (float)match.homeStats.getPossesion() ? f[3] : (float)match.homeStats.getPossesion();
            f[4] = f[4] < (float)match.homeStats.getPasses() ? f[4] : (float)match.homeStats.getPasses();
            f[5] = f[5] < (float)match.homeStats.getPass_acc() ? f[5] : (float)match.homeStats.getPass_acc();
            f[6] = f[6] > (float)match.homeStats.getFouls() ? f[6] : (float)match.homeStats.getFouls();
            f[7] = f[7] > (float)match.homeStats.getYellow_cards() ? f[7] : (float)match.homeStats.getYellow_cards();
            f[8] = f[8] > (float)match.homeStats.getRed_cards() ? f[8] : (float)match.homeStats.getRed_cards();
            f[9] = f[9] > (float)match.homeStats.getOffsides() ? f[9] : (float)match.homeStats.getOffsides();
            f[10] = f[10] < (float)match.homeStats.getPenalties() ? f[10] : (float)match.homeStats.getPenalties();
            f[11] = f[11] < (float)match.homeStats.getCorners() ? f[11] : (float)match.homeStats.getCorners();
        }

        return f;
    }

    public float[] CalculateWorstAwayStats(){
        float f[] = {999f,999f,999f,999f,999f,999f,0f,0f,0f,0f,999f,999f};
        for(Match match : matchList){
            f[0] = f[0] < (float)match.awayStats.getScored() ? f[0] : (float)match.awayStats.getScored();
            f[1] = f[1] < (float)match.awayStats.getTotal_attemps() ? f[1] : (float)match.awayStats.getTotal_attemps();
            f[2] = f[2] < (float)match.awayStats.getOn_target() ? f[2] : (float)match.awayStats.getOn_target();
            f[3] = f[3] < (float)match.awayStats.getPossesion() ? f[3] : (float)match.awayStats.getPossesion();
            f[4] = f[4] < (float)match.awayStats.getPasses() ? f[4] : (float)match.awayStats.getPasses();
            f[5] = f[5] < (float)match.awayStats.getPass_acc() ? f[5] : (float)match.awayStats.getPass_acc();
            f[6] = f[6] > (float)match.awayStats.getFouls() ? f[6] : (float)match.awayStats.getFouls();
            f[7] = f[7] > (float)match.awayStats.getYellow_cards() ? f[7] : (float)match.awayStats.getYellow_cards();
            f[8] = f[8] > (float)match.awayStats.getRed_cards() ? f[8] : (float)match.awayStats.getRed_cards();
            f[9] = f[9] > (float)match.awayStats.getOffsides() ? f[9] : (float)match.awayStats.getOffsides();
            f[10] = f[10] < (float)match.awayStats.getPenalties() ? f[10] : (float)match.awayStats.getPenalties();
            f[11] = f[11] < (float)match.awayStats.getCorners() ? f[11] : (float)match.awayStats.getCorners();
        }

        return f;
    }



    public Dictionary<String, float[]> getStatsData(){
        Dictionary<String, float[]> mData = new Hashtable<>();
        mData.put("homeAvgData", CalculateAvgHomeStats());
        mData.put("awayAvgData", CalculateAvgAwayStats());
        mData.put("homeBestData", CalculateBestHomeStats());
        mData.put("awayBestData", CalculateBestAwayStats());
        mData.put("homeWorstData", CalculateWorstHomeStats());
        mData.put("awayWorstData", CalculateWorstAwayStats());
        return mData;
    }
}

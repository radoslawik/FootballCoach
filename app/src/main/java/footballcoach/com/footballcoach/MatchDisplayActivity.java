package footballcoach.com.footballcoach;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

public class MatchDisplayActivity extends FragmentActivity {

    private ViewPager mPager;
    private PagerAdapter pagerAdapter;
    private ArrayList<Match> matchList;
    private int currentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_display);

        Bundle bundle = getIntent().getExtras();
        this.matchList = bundle.getParcelableArrayList("data");
        this.currentPos = bundle.getInt("pos");

        mPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new MatchDisplayAdapter (getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.setCurrentItem(currentPos);

        System.out.println("currentPos");
        System.out.println(currentPos);
        System.out.println("fragAct");
        System.out.println(matchList.get(currentPos).getTitle());
        System.out.println(matchList.get(currentPos).homeStats.scored);

    }

    private class MatchDisplayAdapter extends FragmentStatePagerAdapter {
        public MatchDisplayAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MatchDisplayFragment.newInstance(matchList.get(position));
        }

        @Override
        public int getCount() {
            return matchList.size();
        }
    }

}

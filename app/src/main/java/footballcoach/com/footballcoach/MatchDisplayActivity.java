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

        /* gets the parameters from main activity */
        Bundle bundle = getIntent().getExtras();
        matchList = bundle.getParcelableArrayList("data");
        currentPos = bundle.getInt("pos");

        /* sets the page adapter together */
        mPager = findViewById(R.id.pager);
        pagerAdapter = new MatchDisplayAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        /* show clicked item */
        mPager.setCurrentItem(currentPos);

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

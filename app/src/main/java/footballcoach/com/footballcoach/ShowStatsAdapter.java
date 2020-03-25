package footballcoach.com.footballcoach;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ShowStatsAdapter extends FragmentStatePagerAdapter {

    private int tabsNum;
    private TeamStats homeAvg, awayAvg, homeBest, awayBest, homeWorst, awayWorst;
    public ShowStatsAdapter(FragmentManager fm, int tm, TeamStats homeAvg,
                            TeamStats awayAvg, TeamStats homeBest, TeamStats awayBest,
                            TeamStats homeWorst, TeamStats awayWorst)
    {
        super(fm);
        this.tabsNum = tm;
        this.homeAvg = homeAvg;
        this.homeBest = homeBest;
        this.homeWorst = homeWorst;
        this.awayAvg = awayAvg;
        this.awayBest = awayBest;
        this.awayWorst = awayWorst;
    }


    @Override
    public Fragment getItem(int position) {
        ShowStatsFragment ssf = new ShowStatsFragment();
        Bundle bundle = new Bundle();
        switch(position)
        {
            case 0:
                bundle.putParcelable("homeData", homeAvg);
                bundle.putParcelable("awayData", homeWorst);
                break;
            case 1:
                bundle.putParcelable("homeData", homeBest);
                bundle.putParcelable("awayData", awayBest);
                break;
            case 2:
                bundle.putParcelable("homeData", homeWorst);
                bundle.putParcelable("awayData", awayWorst);
                break;
            default:
                return null;
        }

        ssf.setArguments(bundle);
        return ssf;
    }

    @Override
    public int getCount() {
        return tabsNum;
    }
}

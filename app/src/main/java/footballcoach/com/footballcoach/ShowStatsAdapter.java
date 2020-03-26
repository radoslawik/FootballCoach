package footballcoach.com.footballcoach;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.Dictionary;


public class ShowStatsAdapter extends FragmentStatePagerAdapter {

    private int tabsNum;
    private Dictionary<String, float[]> extStats;
    public ShowStatsAdapter(FragmentManager fm, int tm, Dictionary<String, float[]> data)
    {
        super(fm);
        this.tabsNum = tm;
        this.extStats = data;
    }


    @Override
    public Fragment getItem(int position) {
        ShowStatsFragment ssf = new ShowStatsFragment();
        Bundle bundle = new Bundle();
        switch(position)
        {
            case 0:
                bundle.putFloatArray("homeData", extStats.get("homeAvgData"));
                bundle.putFloatArray("awayData", extStats.get("awayAvgData"));
                break;
            case 1:
                bundle.putFloatArray("homeData", extStats.get("homeBestData"));
                bundle.putFloatArray("awayData", extStats.get("awayBestData"));
                break;
            case 2:
                bundle.putFloatArray("homeData", extStats.get("homeWorstData"));
                bundle.putFloatArray("awayData", extStats.get("awayWorstData"));
                break;
            default:
                bundle.putFloatArray("homeData", extStats.get("homeAvgData"));
                bundle.putFloatArray("awayData", extStats.get("awayAvgData"));
                break;
        }

        ssf.setArguments(bundle);
        return ssf;
    }

    @Override
    public int getCount() {
        return tabsNum;
    }
}

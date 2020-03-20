package footballcoach.com.footballcoach;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
public class MatchDisplayFragment extends Fragment {

    private Match mEntry;
    private TextView tvScore, tvAwayTeamName, tvHomeTeamName, tvLocation, tvDate, homeVal, awayVal, statName;
    private ImageView imHomeVal, imAwayVal;
    private CardView cvScoreboard;
    private LinearLayout llStats;

    List<String> statNames;
    List<Integer> homeStatValues, awayStatValues;
    List<View> statLayouts;

    public MatchDisplayFragment() {
        // Required empty public constructor
    }

    public static MatchDisplayFragment newInstance(Match matchEntry) {
        MatchDisplayFragment fragment = new MatchDisplayFragment();
        Bundle args = new Bundle();
        args.putParcelable("entry", matchEntry);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            /* fragment gets the match class from activity */
            mEntry = getArguments().getParcelable("entry");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_match_display, container, false);

        /* minor layout changes */
        int currentOrientation = this.getResources().getConfiguration().orientation;
        cvScoreboard = rootView.findViewById(R.id.cvScoreboard);
        llStats = rootView.findViewById(R.id.llStats);
        ActionMenuView.LayoutParams lParams = new ActionMenuView.LayoutParams(
                ActionMenuView.LayoutParams.MATCH_PARENT,
                0
        );
        if(currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            lParams.weight = 1.8f;
            llStats.setPaddingRelative(150, 15, 150, 15);
        } else {
            lParams.weight = 1.2f;
            llStats.setPaddingRelative(15, 15, 15, 15);
        }
        cvScoreboard.setLayoutParams(lParams);

        /* scoreboard part */
        tvHomeTeamName = rootView.findViewById(R.id.homeTeamName);
        tvAwayTeamName = rootView.findViewById(R.id.awayTeamName);
        tvLocation = rootView.findViewById(R.id.matchLocation);
        tvDate = rootView.findViewById(R.id.matchDate);
        tvScore = rootView.findViewById(R.id.score);

        tvScore.setText(mEntry.getScore());

        if(mEntry.homeStats.getScored()>mEntry.awayStats.getScored()){
            tvScore.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else if(mEntry.homeStats.getScored()==mEntry.awayStats.getScored()){
            // keep black
        } else {
            tvScore.setTextColor(getResources().getColor(R.color.textColorError));
        }

        tvHomeTeamName.setText(mEntry.getHomeName());
        tvAwayTeamName.setText(mEntry.getOpponentName());
        tvDate.setText(mEntry.getPlayedWhen());
        tvLocation.setText(mEntry.getPlayedWhere());

        /* stats part */
        statNames = Arrays.asList(
                getResources().getString(R.string.shots),
                getResources().getString(R.string.shots_on_target),
                getResources().getString(R.string.ball_possession),
                getResources().getString(R.string.passes),
                getResources().getString(R.string.pass_accuracy),
                getResources().getString(R.string.fouls),
                getResources().getString(R.string.yellow_cards),
                getResources().getString(R.string.red_cards),
                getResources().getString(R.string.offsides),
                getResources().getString(R.string.penalties),
                getResources().getString(R.string.corners)
        );

        homeStatValues = Arrays.asList(
                mEntry.homeStats.getTotal_attemps(),
                mEntry.homeStats.getOn_target(),
                mEntry.homeStats.getPossesion(),
                mEntry.homeStats.getPasses(),
                mEntry.homeStats.getPass_acc(),
                mEntry.homeStats.getFouls(),
                mEntry.homeStats.getYellow_cards(),
                mEntry.homeStats.getRed_cards(),
                mEntry.homeStats.getOffsides(),
                mEntry.homeStats.getPenalties(),
                mEntry.homeStats.getCorners()
        );

        awayStatValues = Arrays.asList(
                mEntry.awayStats.getTotal_attemps(),
                mEntry.awayStats.getOn_target(),
                mEntry.awayStats.getPossesion(),
                mEntry.awayStats.getPasses(),
                mEntry.awayStats.getPass_acc(),
                mEntry.awayStats.getFouls(),
                mEntry.awayStats.getYellow_cards(),
                mEntry.awayStats.getRed_cards(),
                mEntry.awayStats.getOffsides(),
                mEntry.awayStats.getPenalties(),
                mEntry.awayStats.getCorners()
        );

        statLayouts = Arrays.asList(
                rootView.findViewById(R.id.layoutShots),
                rootView.findViewById(R.id.layoutShotsOnTarget),
                rootView.findViewById(R.id.layoutBallPos),
                rootView.findViewById(R.id.layoutPasses),
                rootView.findViewById(R.id.layoutPassAcc),
                rootView.findViewById(R.id.layoutFouls),
                rootView.findViewById(R.id.layoutYellows),
                rootView.findViewById(R.id.layoutReds),
                rootView.findViewById(R.id.layoutOffsides),
                rootView.findViewById(R.id.layoutPenalties),
                rootView.findViewById(R.id.layoutCorners)
        );

        View viewCurrentStat;
        int homeNum, awayNum;
        String statTxt;
        for(int i=0; i<statLayouts.size(); i++){
            viewCurrentStat = statLayouts.get(i);
            homeVal = viewCurrentStat.findViewById(R.id.homeValue);
            awayVal = viewCurrentStat.findViewById(R.id.awayValue);
            statName = viewCurrentStat.findViewById(R.id.statName);
            imHomeVal = viewCurrentStat.findViewById(R.id.imHomeValue);
            imAwayVal = viewCurrentStat.findViewById(R.id.imAwayValue);

            homeNum = homeStatValues.get(i);
            awayNum = awayStatValues.get(i);
            statTxt = statNames.get(i);
            /* stats with percentage values
             * 2 - BALL POSSESSION
             * 4 - PASS ACCURACY
             * */
            if(i==2 || i==4){
                homeVal.setText(String.valueOf(homeNum+"%"));
                awayVal.setText(String.valueOf(awayNum+"%"));
            } else {
                homeVal.setText(String.valueOf(homeNum));
                awayVal.setText(String.valueOf(awayNum));
            }

            statName.setText(statTxt);
            /* stats with those indexes has negative meaning
            * 5 - FOULS
            * 6 - YELLOWS
            * 7 - REDS
            * 8 - OFFSIDES
            * rest is positive */
            if(i>=5 && i<=8) {
                if (homeNum < awayNum) {
                    imHomeVal.setVisibility(View.VISIBLE);
                } else if(homeNum > awayNum){
                    imAwayVal.setVisibility(View.VISIBLE);
                }
            } else {
                if(homeNum > awayNum){
                    imHomeVal.setVisibility(View.VISIBLE);
                } else if(homeNum < awayNum){
                    imAwayVal.setVisibility(View.VISIBLE);
                }
            }
        }
        return rootView;
    }


}

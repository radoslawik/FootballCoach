package footballcoach.com.footballcoach;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class ShowStatsFragment extends Fragment {

    private List<String> statNames;
    private float[] homeStatValues, awayStatValues;
    private List<View> statLayouts;

    private TextView homeVal, awayVal, statName;
    private ImageView imHomeVal, imAwayVal;


    public ShowStatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("checking if args are null");
        if (getArguments() != null) {
            System.out.println("not null");
            homeStatValues = getArguments().getFloatArray("homeData");
            awayStatValues = getArguments().getFloatArray("awayData");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_show_stats, container, false);

        // goal bar first
        View vGoals = fragView.findViewById(R.id.layoutGoals);
        TextView currentTextView = vGoals.findViewById(R.id.statName);
        currentTextView.setText(getResources().getString(R.string.goals));
        currentTextView = vGoals.findViewById(R.id.homeValue);
        currentTextView.setText(String.format(java.util.Locale.US,"%.2f", homeStatValues[0]));
        currentTextView = vGoals.findViewById(R.id.awayValue);
        currentTextView.setText(String.format(java.util.Locale.US,"%.2f", awayStatValues[0]));
        ImageView currentImageView;
        if(homeStatValues[0] > awayStatValues[0]){
            currentImageView = fragView.findViewById(R.id.imHomeValue);
            currentImageView.setVisibility(View.VISIBLE);
        } else if(homeStatValues[0] < awayStatValues[0]){
            currentImageView = fragView.findViewById(R.id.imAwayValue);
            currentImageView.setVisibility(View.VISIBLE);
        }

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

        statLayouts = Arrays.asList(
                fragView.findViewById(R.id.layoutShots),
                fragView.findViewById(R.id.layoutShotsOnTarget),
                fragView.findViewById(R.id.layoutBallPos),
                fragView.findViewById(R.id.layoutPasses),
                fragView.findViewById(R.id.layoutPassAcc),
                fragView.findViewById(R.id.layoutFouls),
                fragView.findViewById(R.id.layoutYellows),
                fragView.findViewById(R.id.layoutReds),
                fragView.findViewById(R.id.layoutOffsides),
                fragView.findViewById(R.id.layoutPenalties),
                fragView.findViewById(R.id.layoutCorners)
        );

        View viewCurrentStat;
        float homeNum, awayNum;
        String statTxt;
        for(int i=0; i<statLayouts.size(); i++){
            viewCurrentStat = statLayouts.get(i);
            homeVal = viewCurrentStat.findViewById(R.id.homeValue);
            awayVal = viewCurrentStat.findViewById(R.id.awayValue);
            statName = viewCurrentStat.findViewById(R.id.statName);
            imHomeVal = viewCurrentStat.findViewById(R.id.imHomeValue);
            imAwayVal = viewCurrentStat.findViewById(R.id.imAwayValue);

            homeNum = homeStatValues[i+1];
            awayNum = awayStatValues[i+1];
            statTxt = statNames.get(i);
            /* stats with percentage values
             * 2 - BALL POSSESSION
             * 4 - PASS ACCURACY
             * */
            if(i==2 || i==4){
                homeVal.setText(String.format(java.util.Locale.US,"%.2f", homeNum)+"%");
                awayVal.setText(String.format(java.util.Locale.US,"%.2f", awayNum)+"%");
            } else {
                homeVal.setText(String.format(java.util.Locale.US,"%.2f", homeNum));
                awayVal.setText(String.format(java.util.Locale.US,"%.2f", awayNum));
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

        return fragView;
    }


}

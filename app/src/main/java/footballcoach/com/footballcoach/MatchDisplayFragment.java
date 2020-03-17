package footballcoach.com.footballcoach;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MatchDisplayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MatchDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchDisplayFragment extends Fragment {

    private Match mEntry;
    private TextView tvScore, tvAwayTeamName, tvHomeTeamName, 
            tvHomeShots, tvHomeTargetShots, tvHomeBallPos, tvHomePasses, tvHomePassAcc, tvHomeFouls, 
            tvHomeYellows, tvHomeReds, tvHomeOffsides, tvHomePenalties, tvHomeCorners,
            tvAwayShots, tvAwayTargetShots, tvAwayBallPos, tvAwayPasses, tvAwayPassAcc, tvAwayFouls,
            tvAwayYellows, tvAwayReds, tvAwayOffsides, tvAwayPenalties, tvAwayCorners,
            tvLocation, tvDate;

    public MatchDisplayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MatchDisplayFragment.
     */
    // TODO: Rename and change types and number of parameters
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
            mEntry = getArguments().getParcelable("entry");
            System.out.println(mEntry.getTitle());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_match_display, container, false);
        tvHomeTeamName = rootView.findViewById(R.id.homeTeamName);
        tvHomeBallPos = rootView.findViewById(R.id.homePossesion);
        tvHomeCorners = rootView.findViewById(R.id.homeCorners);
        tvHomeFouls = rootView.findViewById(R.id.homeFouls);
        tvHomeOffsides = rootView.findViewById(R.id.homeOffsides);
        tvHomePassAcc= rootView.findViewById(R.id.homePassAccuracy);
        tvHomePasses = rootView.findViewById(R.id.homePasses);
        tvHomePenalties= rootView.findViewById(R.id.homePenalties);
        tvHomeReds = rootView.findViewById(R.id.homeReds);
        tvHomeShots = rootView.findViewById(R.id.homeShots);
        tvHomeTargetShots = rootView.findViewById(R.id.homeTargetShots);
        tvHomeYellows = rootView.findViewById(R.id.homeYellows);

        tvAwayTeamName = rootView.findViewById(R.id.awayTeamName);
        tvAwayBallPos = rootView.findViewById(R.id.awayPossesion);
        tvAwayCorners = rootView.findViewById(R.id.awayCorners);
        tvAwayFouls = rootView.findViewById(R.id.awayFouls);
        tvAwayOffsides = rootView.findViewById(R.id.awayOffsides);
        tvAwayPassAcc= rootView.findViewById(R.id.awayPassAccuracy);
        tvAwayPasses = rootView.findViewById(R.id.awayPasses);
        tvAwayPenalties= rootView.findViewById(R.id.awayPenalties);
        tvAwayReds = rootView.findViewById(R.id.awayReds);
        tvAwayShots = rootView.findViewById(R.id.awayShots);
        tvAwayTargetShots = rootView.findViewById(R.id.awayTargetShots);
        tvAwayYellows = rootView.findViewById(R.id.awayYellows);
        
        tvLocation= rootView.findViewById(R.id.matchLocation);
        tvDate = rootView.findViewById(R.id.matchDate);
        tvScore = rootView.findViewById(R.id.score);

        tvScore.setText(mEntry.getScore());

        tvDate.setText(mEntry.getPlayedWhen());
        tvLocation.setText("PARIS TODO");

        tvHomeBallPos.setText(String.valueOf(mEntry.homeStats.getScored()));
        tvHomeCorners.setText(String.valueOf(mEntry.homeStats.getCorners()));
        tvHomeFouls.setText(String.valueOf(mEntry.homeStats.getFouls()));
        tvHomeOffsides.setText(String.valueOf(mEntry.homeStats.getOffsides()));
        tvHomePassAcc.setText(String.valueOf(mEntry.homeStats.getPass_acc()));
        tvHomePasses.setText(String.valueOf(mEntry.homeStats.getPasses()));
        tvHomePenalties.setText(String.valueOf(mEntry.homeStats.getPenalties()));
        tvHomeReds.setText(String.valueOf(mEntry.homeStats.getRed_cards()));
        tvHomeShots.setText(String.valueOf(mEntry.homeStats.getTotal_attemps()));
        tvHomeTargetShots.setText(String.valueOf(mEntry.homeStats.getOn_target()));
        tvHomeTeamName.setText(String.valueOf(mEntry.homeName));
        tvHomeYellows.setText(String.valueOf(mEntry.homeStats.getYellow_cards()));

        tvAwayBallPos.setText(String.valueOf(mEntry.awayStats.getScored()));
        tvAwayCorners.setText(String.valueOf(mEntry.awayStats.getCorners()));
        tvAwayFouls.setText(String.valueOf(mEntry.awayStats.getFouls()));
        tvAwayOffsides.setText(String.valueOf(mEntry.awayStats.getOffsides()));
        tvAwayPassAcc.setText(String.valueOf(mEntry.awayStats.getPass_acc()));
        tvAwayPasses.setText(String.valueOf(mEntry.awayStats.getPasses()));
        tvAwayPenalties.setText(String.valueOf(mEntry.awayStats.getPenalties()));
        tvAwayReds.setText(String.valueOf(mEntry.awayStats.getRed_cards()));
        tvAwayShots.setText(String.valueOf(mEntry.awayStats.getTotal_attemps()));
        tvAwayTargetShots.setText(String.valueOf(mEntry.awayStats.getOn_target()));
        tvAwayTeamName.setText(String.valueOf(mEntry.opponentName));
        tvAwayYellows.setText(String.valueOf(mEntry.awayStats.getYellow_cards()));

        tvAwayTeamName.setText(mEntry.getOpponentName());
        return rootView;
    }


}

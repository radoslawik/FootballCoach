package footballcoach.com.footballcoach;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
    private ImageView imHomeShots, imHomeOnTarget, imHomeBallPos, imHomePasses, imHomePassAcc, imHomeFouls,
            imHomeYellows, imHomeReds, imHomeOffsides, imHomePenalties, imHomeCorners,
            imAwayShots, imAwayOnTarget, imAwayBallPos, imAwayPasses, imAwayPassAcc, imAwayFouls,
            imAwayYellows, imAwayReds, imAwayOffsides, imAwayPenalties, imAwayCorners;

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
        
        tvLocation = rootView.findViewById(R.id.matchLocation);
        tvDate = rootView.findViewById(R.id.matchDate);
        tvScore = rootView.findViewById(R.id.score);

        imHomeBallPos = rootView.findViewById(R.id.imHomeBallPos);
        imHomeCorners = rootView.findViewById(R.id.imHomeCorners);
        imHomeFouls = rootView.findViewById(R.id.imHomeFouls);
        imHomeOffsides = rootView.findViewById(R.id.imHomeOffside);
        imHomeOnTarget = rootView.findViewById(R.id.imHomeOnTarget);
        imHomePassAcc = rootView.findViewById(R.id.imHomePassAcc);
        imHomePasses = rootView.findViewById(R.id.imHomePasses);
        imHomePenalties = rootView.findViewById(R.id.imHomePen);
        imHomeReds = rootView.findViewById(R.id.imHomeReds);
        imHomeShots= rootView.findViewById(R.id.imHomeShots);
        imHomeYellows = rootView.findViewById(R.id.imHomeYellows);
        
        imAwayBallPos = rootView.findViewById(R.id.imAwayBallPos);
        imAwayCorners = rootView.findViewById(R.id.imAwayCorners);
        imAwayFouls = rootView.findViewById(R.id.imAwayFouls);
        imAwayOffsides = rootView.findViewById(R.id.imAwayOffside);
        imAwayOnTarget = rootView.findViewById(R.id.imAwayOnTarget);
        imAwayPassAcc = rootView.findViewById(R.id.imAwayPassAcc);
        imAwayPasses = rootView.findViewById(R.id.imAwayPasses);
        imAwayPenalties = rootView.findViewById(R.id.imAwayPen);
        imAwayReds = rootView.findViewById(R.id.imAwayReds);
        imAwayShots= rootView.findViewById(R.id.imAwayShots);
        imAwayYellows = rootView.findViewById(R.id.imAwayYellows);

        tvScore.setText(mEntry.getScore());
        if(mEntry.homeStats.getScored()>mEntry.awayStats.getScored()){
            tvScore.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else if(mEntry.homeStats.getScored()==mEntry.awayStats.getScored()){
            // keep black
        } else {
            tvScore.setTextColor(getResources().getColor(R.color.textColorError));
        }

        tvDate.setText(mEntry.getPlayedWhen());
        tvLocation.setText(mEntry.getPlayedWhere());

        tvHomeBallPos.setText(String.valueOf(mEntry.homeStats.getPossesion()));
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

        tvAwayBallPos.setText(String.valueOf(mEntry.awayStats.getPossesion()));
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
        
        if (mEntry.homeStats.getTotal_attemps()>mEntry.awayStats.getTotal_attemps()){
            imHomeShots.setVisibility(View.VISIBLE);
        } else if (mEntry.homeStats.getTotal_attemps()<mEntry.awayStats.getTotal_attemps()){
            imAwayShots.setVisibility(View.VISIBLE);
        }

        if (mEntry.homeStats.getCorners()>mEntry.awayStats.getCorners()){
            imHomeCorners.setVisibility(View.VISIBLE);
        } else if (mEntry.homeStats.getCorners()<mEntry.awayStats.getCorners()){
            imAwayCorners.setVisibility(View.VISIBLE);
        }

        if (mEntry.homeStats.getCorners()>mEntry.awayStats.getCorners()){
            imHomeCorners.setVisibility(View.VISIBLE);
        } else if (mEntry.homeStats.getCorners()<mEntry.awayStats.getCorners()){
            imAwayCorners.setVisibility(View.VISIBLE);
        }

        if (mEntry.homeStats.getFouls()>mEntry.awayStats.getFouls()){
            imHomeFouls.setVisibility(View.VISIBLE);
        } else if (mEntry.homeStats.getFouls()<mEntry.awayStats.getFouls()){
            imAwayFouls.setVisibility(View.VISIBLE);
        }

        if (mEntry.homeStats.getOffsides()>mEntry.awayStats.getOffsides()){
            imHomeOffsides.setVisibility(View.VISIBLE);
        } else if (mEntry.homeStats.getOffsides()<mEntry.awayStats.getOffsides()){
            imAwayOffsides.setVisibility(View.VISIBLE);
        }

        if (mEntry.homeStats.getOn_target()>mEntry.awayStats.getOn_target()){
            imHomeOnTarget.setVisibility(View.VISIBLE);
        } else if (mEntry.homeStats.getOn_target()<mEntry.awayStats.getOn_target()){
            imAwayOnTarget.setVisibility(View.VISIBLE);
        }

        if (mEntry.homeStats.getPass_acc()>mEntry.awayStats.getPass_acc()){
            imHomePassAcc.setVisibility(View.VISIBLE);
        } else if (mEntry.homeStats.getPass_acc()<mEntry.awayStats.getPass_acc()){
            imAwayPassAcc.setVisibility(View.VISIBLE);
        }

        if (mEntry.homeStats.getPasses()>mEntry.awayStats.getPasses()){
            imHomePasses.setVisibility(View.VISIBLE);
        } else if (mEntry.homeStats.getPasses()<mEntry.awayStats.getPasses()){
            imAwayPasses.setVisibility(View.VISIBLE);
        }

        if (mEntry.homeStats.getPenalties()>mEntry.awayStats.getPenalties()){
            imHomePenalties.setVisibility(View.VISIBLE);
        } else if (mEntry.homeStats.getPenalties()<mEntry.awayStats.getPenalties()){
            imAwayPenalties.setVisibility(View.VISIBLE);
        }

        if (mEntry.homeStats.getPossesion()>mEntry.awayStats.getPossesion()){
            imHomeBallPos.setVisibility(View.VISIBLE);
        } else if (mEntry.homeStats.getPossesion()<mEntry.awayStats.getPossesion()){
            imAwayBallPos.setVisibility(View.VISIBLE);
        }

        if (mEntry.homeStats.getRed_cards()>mEntry.awayStats.getRed_cards()){
            imHomeReds.setVisibility(View.VISIBLE);
        } else if (mEntry.homeStats.getRed_cards()<mEntry.awayStats.getRed_cards()){
            imAwayReds.setVisibility(View.VISIBLE);
        }

        if (mEntry.homeStats.getYellow_cards()>mEntry.awayStats.getYellow_cards()){
            imHomeYellows.setVisibility(View.VISIBLE);
        } else if (mEntry.homeStats.getYellow_cards()<mEntry.awayStats.getYellow_cards()){
            imAwayYellows.setVisibility(View.VISIBLE);
        }

        return rootView;
    }


}

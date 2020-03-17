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
    private TextView tvScore, tvAwayTeamName;

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
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
            mEntry = getArguments().getParcelable("entry");
            System.out.println(mEntry.getTitle());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_match_display, container, false);
        tvScore = rootView.findViewById(R.id.score);
        tvAwayTeamName = rootView.findViewById(R.id.awayTeamName);

        tvScore.setText(mEntry.getScore());
        tvAwayTeamName.setText(mEntry.getOpponentName());
        return rootView;
    }


}

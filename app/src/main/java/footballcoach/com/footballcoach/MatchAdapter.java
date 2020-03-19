package footballcoach.com.footballcoach;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {

    private Context myContext;
    private List<Match> matchList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public MatchAdapter(Context myContext, List<Match> matchList) {
        this.myContext = myContext;
        this.matchList = matchList;
    }

    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(myContext);
        View view = inflater.inflate(R.layout.list_layout, parent, false);
        MatchViewHolder holder = new MatchViewHolder(view, mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(MatchViewHolder holder, int position) {
        Match match = matchList.get(position);

        holder.textViewTitle.setText(match.getTitle());
        holder.textViewDate.setText(match.getPlayedWhen());
        holder.textViewGameType.setText(match.getGameType());
        holder.textViewScore.setText(match.getScore());
        if(match.homeStats.getScored()>match.awayStats.getScored()){
            holder.textViewScore.setTextColor(myContext.getResources().getColor(R.color.colorPrimary));
        } else if (match.homeStats.getScored()==match.awayStats.getScored()){
            // keep black
        } else {
            holder.textViewScore.setTextColor(myContext.getResources().getColor(R.color.textColorError));
        }
        holder.imageView.setImageDrawable(myContext.getResources().getDrawable(match.getImageId(), null));
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public static class MatchViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textViewTitle, textViewScore, textViewDate, textViewGameType;
        public MatchViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewScore = itemView.findViewById(R.id.textViewScore);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewGameType = itemView.findViewById(R.id.textViewGameType);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }
}

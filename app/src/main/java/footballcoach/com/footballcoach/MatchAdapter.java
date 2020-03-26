package footballcoach.com.footballcoach;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
            holder.textViewScore.setTextColor(Color.BLACK);
        } else {
            holder.textViewScore.setTextColor(myContext.getResources().getColor(R.color.textColorError));
        }
        if(match.getImagePath().equals("")){
            holder.imageView.setImageDrawable(myContext.getResources().getDrawable(R.drawable.ic_menu_camera, null));
        } else {
            holder.imageView.setImageDrawable(myContext.getResources().getDrawable(R.drawable.ic_menu_camera, null));
            setPic(holder.imageView, match.getImagePath());
        }

    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    private void setPic(ImageView imageView, String currentPhotoPath) {
        // Get the dimensions of the View

        //int targetW = imageView.getWidth();
        //int targetH = imageView.getHeight();
        int targetW = 180;
        int targetH = 120;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
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

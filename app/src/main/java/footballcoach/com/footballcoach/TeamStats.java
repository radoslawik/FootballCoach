package footballcoach.com.footballcoach;

import android.os.Parcel;
import android.os.Parcelable;

public class TeamStats implements Parcelable {
    int scored;
    int total_attemps;
    int on_target;
    int possesion;
    int passes;
    int fouls;
    int yellow_cards;
    int red_cards;
    int offsides;
    int corners;
    int penalties;

    public TeamStats(int scored, int total_attemps, int on_target, int possesion, int passes, int fouls, int yellow_cards, int red_cards, int offsides, int corners, int penalties) {
        this.scored = scored;
        this.total_attemps = total_attemps;
        this.on_target = on_target;
        this.possesion = possesion;
        this.passes = passes;
        this.fouls = fouls;
        this.yellow_cards = yellow_cards;
        this.red_cards = red_cards;
        this.offsides = offsides;
        this.corners = corners;
        this.penalties = penalties;
    }

    public TeamStats() {
        this.scored = 0;
        this.total_attemps = 0;
        this.on_target = 0;
        this.possesion = 0;
        this.passes = 0;
        this.fouls = 0;
        this.yellow_cards = 0;
        this.red_cards = 0;
        this.offsides = 0;
        this.corners = 0;
        this.penalties = 0;
    }

    protected TeamStats(Parcel in) {
        scored = in.readInt();
        total_attemps = in.readInt();
        on_target = in.readInt();
        possesion = in.readInt();
        passes = in.readInt();
        fouls = in.readInt();
        yellow_cards = in.readInt();
        red_cards = in.readInt();
        offsides = in.readInt();
        corners = in.readInt();
        penalties = in.readInt();
    }

    public static final Creator<TeamStats> CREATOR = new Creator<TeamStats>() {
        @Override
        public TeamStats createFromParcel(Parcel in) {
            return new TeamStats(in);
        }

        @Override
        public TeamStats[] newArray(int size) {
            return new TeamStats[size];
        }
    };

    public int getScored() {
        return scored;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(scored);
        dest.writeInt(total_attemps);
        dest.writeInt(on_target);
        dest.writeInt(possesion);
        dest.writeInt(passes);
        dest.writeInt(fouls);
        dest.writeInt(yellow_cards);
        dest.writeInt(red_cards);
        dest.writeInt(offsides);
        dest.writeInt(corners);
        dest.writeInt(penalties);
    }
}

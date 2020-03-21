package footballcoach.com.footballcoach;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Match implements Parcelable {
    long gameId;
    String homeName;
    String opponentName;
    String gameType;
    String playedWhen;
    String playedWhere;
    TeamStats homeStats;
    TeamStats awayStats;
    private String imagePath;

    public Match(long gameId, String homeName, String opponentName, String gameType,
                 String playedWhen, String strLoc, TeamStats homeStats, TeamStats awayStats, String imageId) {
        this.gameId = gameId;
        this.homeName = homeName;
        this.opponentName = opponentName;
        this.gameType = gameType;
        this.playedWhen = playedWhen;
        this.playedWhere = strLoc;
        this.homeStats = homeStats;
        this.awayStats = awayStats;
        this.imagePath = imageId;
    }

    protected Match(Parcel in) {
        gameId = in.readLong();
        homeName = in.readString();
        opponentName = in.readString();
        gameType = in.readString();
        playedWhen = in.readString();
        playedWhere = in.readString();
        homeStats = in.readParcelable(TeamStats.class.getClassLoader());
        awayStats = in.readParcelable(TeamStats.class.getClassLoader());
        imagePath = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(gameId);
        dest.writeString(homeName);
        dest.writeString(opponentName);
        dest.writeString(gameType);
        dest.writeString(playedWhen);
        dest.writeString(playedWhere);
        dest.writeParcelable(homeStats, flags);
        dest.writeParcelable(awayStats, flags);
        dest.writeString(imagePath);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Match> CREATOR = new Creator<Match>() {
        @Override
        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };

    public long getGameId() {
        return gameId;
    }

    public String getHomeName() {
        return homeName;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public String getGameType() {
        return gameType;
    }

    public String getPlayedWhen() {
        return playedWhen;
    }

    public String getPlayedWhere() {
        return playedWhere;
    }

    public String getImagePath() {
        return imagePath;
    }
    public String getTitle() {
        return homeName + " vs " + opponentName;
    }

    public String getScore(){
        return Integer.toString(homeStats.getScored()) + ":" + Integer.toString(awayStats.getScored());
    }
}

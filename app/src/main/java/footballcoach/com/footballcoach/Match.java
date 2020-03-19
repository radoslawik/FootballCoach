package footballcoach.com.footballcoach;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Match implements Parcelable {
    int gameId;
    String homeName;
    String opponentName;
    String gameType;
    String playedWhen;
    String playedWhere;
    TeamStats homeStats;
    TeamStats awayStats;
    private int imageId;

    public Match(int gameId, String homeName, String opponentName, String gameType,
                 Date playedWhen, String strLoc, TeamStats homeStats, TeamStats awayStats, int imageId) {
        this.gameId = gameId;
        this.homeName = homeName;
        this.opponentName = opponentName;
        this.gameType = gameType;
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        String strDate = formatter.format(playedWhen);
        this.playedWhen = strDate;
        this.playedWhere = strLoc;
        this.homeStats = homeStats;
        this.awayStats = awayStats;
        this.imageId = imageId;
    }

    protected Match(Parcel in) {
        gameId = in.readInt();
        homeName = in.readString();
        opponentName = in.readString();
        gameType = in.readString();
        playedWhen = in.readString();
        playedWhere = in.readString();
        homeStats = in.readParcelable(TeamStats.class.getClassLoader());
        awayStats = in.readParcelable(TeamStats.class.getClassLoader());
        imageId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(gameId);
        dest.writeString(homeName);
        dest.writeString(opponentName);
        dest.writeString(gameType);
        dest.writeString(playedWhen);
        dest.writeString(playedWhere);
        dest.writeParcelable(homeStats, flags);
        dest.writeParcelable(awayStats, flags);
        dest.writeInt(imageId);
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

    public int getGameId() {
        return gameId;
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
    public TeamStats getHomeStats() {
        return homeStats;
    }

    public TeamStats getAwayStats() {
        return awayStats;
    }

    public int getImageId() {
        return imageId;
    }

    public String getTitle() {
        return homeName + " vs " + opponentName;
    }

    public String getScore(){
        return Integer.toString(homeStats.scored) + ":" + Integer.toString(awayStats.scored);
    }
}

package footballcoach.com.footballcoach;

import java.util.Date;

public class Match {
    int gameId;
    String homeName;
    String opponentName;
    String gameType;
    Date playedWhen;
    TeamStats homeStats;
    TeamStats awayStats;
    private int imageId;

    public Match(int gameId, String homeName, String opponentName, String gameType, Date playedWhen, TeamStats homeStats, TeamStats awayStats, int imageId) {
        this.gameId = gameId;
        this.homeName = homeName;
        this.opponentName = opponentName;
        this.gameType = gameType;
        this.playedWhen = playedWhen;
        this.homeStats = homeStats;
        this.awayStats = awayStats;
        this.imageId = imageId;
    }

    public int getGameId() {
        return gameId;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public String getGameType() {
        return gameType;
    }

    public Date getPlayedWhen() {
        return playedWhen;
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

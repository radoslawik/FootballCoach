package footballcoach.com.footballcoach;

public class TeamStats {
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
    }

    public int getScored() {
        return scored;
    }
}

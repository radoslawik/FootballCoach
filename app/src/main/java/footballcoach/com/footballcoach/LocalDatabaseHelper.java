package footballcoach.com.footballcoach;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Arrays;
import java.util.List;

public class LocalDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "RecentMatches.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "MatchData";

    String homeName;
    String opponentName;
    String gameType;
    String playedWhen;
    String playedWhere;

    private static final List<String> colNames = Arrays.asList(
            "game_id", "home_name", "away_name", "game_type", "match_date", "match_location", "image_path",
            "home_scored", "home_shots", "home_on_target", "home_possession", "home_passes", "home_pass_acc",
            "home_fouls", "home_yellows", "home_reds", "home_offsides", "home_penalties", "home_corners",
            "away_scored", "away_shots", "away_on_target", "away_possession", "away_passes", "away_pass_acc",
            "away_fouls", "away_yellows", "away_reds", "away_offsides", "away_penalties", "away_corners");
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    colNames.get(0)+ " BIGINT PRIMARY KEY," +
                    colNames.get(1)+ " TEXT," +
                    colNames.get(2)+ " TEXT," +
                    colNames.get(3)+ " TEXT," +
                    colNames.get(4)+ " TEXT," +
                    colNames.get(5)+ " TEXT," +
                    colNames.get(6)+ " TEXT," +
                    colNames.get(7)+ " INT," +
                    colNames.get(8)+ " INT," +
                    colNames.get(9)+ " INT," +
                    colNames.get(10)+ " INT," +
                    colNames.get(11)+ " INT," +
                    colNames.get(12)+ " INT," +
                    colNames.get(13)+ " INT," +
                    colNames.get(14)+ " INT," +
                    colNames.get(15)+ " INT," +
                    colNames.get(16)+ " INT," +
                    colNames.get(17)+ " INT," +
                    colNames.get(18)+ " INT," +
                    colNames.get(19)+ " INT," +
                    colNames.get(20)+ " INT," +
                    colNames.get(21)+ " INT," +
                    colNames.get(22)+ " INT," +
                    colNames.get(23)+ " INT," +
                    colNames.get(24)+ " INT," +
                    colNames.get(25)+ " INT," +
                    colNames.get(26)+ " INT," +
                    colNames.get(27)+ " INT," +
                    colNames.get(28)+ " INT," +
                    colNames.get(29)+ " INT," +
                    colNames.get(30)+ " INT)";

    // mySQL String
    // CREATE TABLE MatchData (game_id BIGINT PRIMARY KEY, home_name TEXT, away_name TEXT, game_type TEXT, match_date TEXT, match_location TEXT, image_path TEXT, home_scored INT, home_shots INT, home_on_target INT, home_possession INT, home_passes INT, home_pass_acc INT, home_fouls INT, home_yellows INT, home_reds INT, home_offsides INT, home_penalties INT, home_corners INT, away_scored INT, away_shots INT, away_on_target INT, away_possession INT, away_passes INT, away_pass_acc INT, away_fouls INT, away_yellows INT, away_reds INT, away_offsides INT, away_penalties INT, away_corners INT);

    public LocalDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        System.out.println(SQL_CREATE_ENTRIES);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public List<String> getColNames() {
        return colNames;
    }

    public String getTableName(){
        return TABLE_NAME;
    }
}

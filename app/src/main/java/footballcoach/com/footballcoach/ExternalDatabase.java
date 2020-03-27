package footballcoach.com.footballcoach;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

import java.sql.*;
import java.util.ArrayList;

public class ExternalDatabase{
    private Connection connection;
    private final String url = "jdbc:mysql://10.0.2.2:3306/footballcoach";
    private final String user = "root";
    private final String password = "";
    private final String table = "matchdata";
    AsyncTask<Void, Void, Void> runningTask;
    public ArrayList<Match> matchList;


    public ExternalDatabase(){
        matchList = new ArrayList<>();
    }

    public synchronized ArrayList<Match> getMatches(){
        // create a statement; whatever happens, the try-with-resource construct
        // will close the statement, which in turn will close the result set
        ArrayList<Match> list = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(url,user,password);
            Statement statement = connection.createStatement();
            // execute the query
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM " + table + " ORDER BY game_id DESC");
            // convert the result set to a list
            while (result.next()) {
                // accessing attributes by rank: 1, 2 & 3
                list.add(new Match(
                        result.getLong(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getString(6),
                        new TeamStats(
                                result.getInt(8)  ,
                                result.getInt(9)  ,
                                result.getInt(10)  ,
                                result.getInt(11)  ,
                                result.getInt(12)  ,
                                result.getInt(13)  ,
                                result.getInt(14)  ,
                                result.getInt(15)  ,
                                result.getInt(16)  ,
                                result.getInt(17)  ,
                                result.getInt(18)  ,
                                result.getInt(19)
                                ),
                        new TeamStats(
                                result.getInt(20)  ,
                                result.getInt(21)  ,
                                result.getInt(22)  ,
                                result.getInt(23)  ,
                                result.getInt(24)  ,
                                result.getInt(25)  ,
                                result.getInt(26)  ,
                                result.getInt(27)  ,
                                result.getInt(28)  ,
                                result.getInt(29)  ,
                                result.getInt(30)  ,
                                result.getInt(31)
                                ),
                        result.getString(7)
                        ));
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public synchronized boolean addNewMatch(Match m){

        int r = 0;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url,user,password);
            Statement statement = connection.createStatement();

            // do not forget to enclose string litterals (e.g. job) between single quotes:
            String query = "INSERT INTO MatchData VALUES(" +
                m.getGameId() + ",'" +
                m.getHomeName() + "','" +
                m.getOpponentName()+ "','" +
                m.getGameType()+ "','" +
                m.getPlayedWhen()+ "','" +
                m.getPlayedWhere()+ "','" +
                m.getImagePath()+ "','" +
                m.homeStats.getScored()+ "'," +
                m.homeStats.getTotal_attemps()+ "," +
                m.homeStats.getOn_target()+ "," +
                m.homeStats.getPossesion()+ "," +
                m.homeStats.getPasses()+ "," +
                m.homeStats.getPass_acc()+ "," +
                m.homeStats.getFouls()+ "," +
                m.homeStats.getYellow_cards()+ "," +
                m.homeStats.getRed_cards()+ "," +
                m.homeStats.getOffsides()+ "," +
                m.homeStats.getPenalties()+ "," +
                m.homeStats.getCorners()+ "," +
                m.awayStats.getScored()+ "," +
                m.awayStats.getTotal_attemps()+ "," +
                m.awayStats.getOn_target()+ "," +
                m.awayStats.getPossesion()+ "," +
                m.awayStats.getPasses()+ "," +
                m.awayStats.getPass_acc()+ "," +
                m.awayStats.getFouls()+ "," +
                m.awayStats.getYellow_cards()+ "," +
                m.awayStats.getRed_cards()+ "," +
                m.awayStats.getOffsides()+ "," +
                m.awayStats.getPenalties()+ "," +
                m.awayStats.getCorners()+ ");";

            r = statement.executeUpdate(query);
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // one row should be inserted
        return r == 1;
    }

    public synchronized boolean deleteMatch(long timestamp){

        int r = 0;
        try {
            connection = DriverManager.getConnection(url,user,password);
            Statement statement = connection.createStatement();
            // do not forget to enclose string litterals (e.g. job) between single quotes:
            String query = "DELETE FROM matchdata WHERE game_id=" + String.valueOf(timestamp) + ";";
            r = statement.executeUpdate(query);
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // one row should be deleted
        return r == 1;
    }

    public void startAddMatchTask(Match m){
        if (runningTask != null)
            runningTask.cancel(true);
        runningTask = new DbTask(m);
        runningTask.execute();
    }

    public void startDeleteMatchTask(long ts){
        if (runningTask != null)
            runningTask.cancel(true);
        runningTask = new DbTask(ts);
        runningTask.execute();
    }

    public void startSelectMatchTask(){
        if (runningTask != null)
            runningTask.cancel(true);
        runningTask = new DbTask();
        runningTask.execute();
    }

    public ArrayList<Match> getMatchList() {
        return matchList;
    }

    private class DbTask extends AsyncTask<Void, Void, Void> {
        private Match matchToAdd;
        private long tsToDelete;
        private ArrayList<Match> selectedMatches;

        public DbTask(Match m){
            this.matchToAdd = m;
            this.tsToDelete = 0;
            this.selectedMatches = new ArrayList<>();
        }

        public DbTask(long ts){
            this.matchToAdd = null;
            this.tsToDelete = ts;
            this.selectedMatches = new ArrayList<>();
        }

        public DbTask(){
            this.matchToAdd = null;
            this.tsToDelete = 0;
            this.selectedMatches = new ArrayList<>();
        }


        protected Void doInBackground(Void... params) {
            try {
                if(matchToAdd!=null){
                    addNewMatch(matchToAdd);
                } else if (tsToDelete!=0) {
                    deleteMatch(tsToDelete);
                } else {
                    selectedMatches = getMatches();
                }
            } catch (Exception e) { // in case of error
                System.out.println(e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(!selectedMatches.isEmpty()){
                matchList = selectedMatches;
                System.out.println("received gameid");
                System.out.println(matchList.get(0).getGameId());
                Intent intent = new Intent("data-received-mysql");
                LocalBroadcastManager.getInstance(null).sendBroadcast(intent);
            }
        }


    }

}

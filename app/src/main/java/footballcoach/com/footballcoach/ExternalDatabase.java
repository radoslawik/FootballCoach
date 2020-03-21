package footballcoach.com.footballcoach;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ExternalDatabase{
    private Connection connection;
    private final String url = "jdbc:mysql://localhost:3306/footballcoach";
    private final String user = "root";
    private final String password = "";
    private final String table = "matchdata";

    public ExternalDatabase(){
        try{
            connection = DriverManager.getConnection(url,user,password);
        } catch (SQLException e){
            System.out.println("Couldn't connect to database");
        }

    }

    public ArrayList<Match> getMatches(){
        // create a statement; whatever happens, the try-with-resource construct
        // will close the statement, which in turn will close the result set
        ArrayList<Match> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            // execute the query
            ResultSet result = statement.executeQuery(
                    "select * from " + table);
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
        } catch (SQLException e) {
            System.out.println("Error while executing query");
        }
        return list;
    }

    public synchronized boolean addNewMatch(Match m){

        int r = 0;

        try {
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
        } catch (SQLException e) {
            System.out.println("Error while executing insert");
        }

        // one row should be inserted
        return r == 1;
    }

    public synchronized boolean deleteMatch(long timestamp){

        int r = 0;
        try {
            Statement statement = connection.createStatement();
            // do not forget to enclose string litterals (e.g. job) between single quotes:
            String query = "DELETE FROM MatchData WHERE game_id=" + String.valueOf(timestamp) + ";";
            r = statement.executeUpdate(query);
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error while executing delete");
        }
        // one row should be deleted
        return r == 1;
    }

}

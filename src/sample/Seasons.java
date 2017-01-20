package sample;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by piotrek on 20.01.17.
 */
public class Seasons {

    public static ArrayList<Integer> getAllSeasons(Connection dbConnection) throws SQLException {

        ArrayList<Integer> allSeasons = new ArrayList<>();

        String query = "SELECT * FROM sezon ORDER BY sezon DESC;";
        Statement statement = dbConnection.createStatement();
        ResultSet  rS = statement.executeQuery(query);

        while (rS.next())
            allSeasons.add(rS.getInt("sezon"));

        return allSeasons;
    }
}

package sample;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by piotrek on 12.01.17.
 */
class Admin {
    private Connection connection;
    private Statement statement;

    Admin(Connection dbConnection){
        connection = dbConnection;
    }

    public Boolean addSeasonRanking(/*kolumny tabeli ranking sezonu*/){

        return false;
    }

    public Boolean addSailor(/*kolumny tabeli zawodnicy*/){

        return false;
    }

    public Boolean addEvent(/*kolumny tabeli zawody*/){

        return false;
    }

    public Boolean addEventResults(/*kolumny tabeli wyniki zawodów*/){

        return false;
    }

    public Boolean addSeason(/*sezon*/){

        return false;
    }
}

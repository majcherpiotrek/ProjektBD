package sample;

import java.sql.Connection;
import java.sql.SQLException;
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

    public void addSailor(Integer sailNumber, String name, String surname, String sex, String nationality, String boardBrand, String sailBrand, String sponsors) throws SQLException{

        statement = connection.createStatement();
        String update = "";
        update += "INSERT INTO zawodnicy(nr_rejestracyjny, imie, nazwisko," +
                "plec, narodowosc, marka_desek, marka_zagli, sponsorzy) values (\'";
        update += sailNumber + "\',";
        update += "\'"+name+"\',";
        update += "\'"+surname+"\',";
        update += "\'"+sex+"\',";
        update += "\'"+nationality+"\',";
        update += "\'"+boardBrand+"\',";
        update += "\'"+sailBrand+"\',";
        update += "\'"+sponsors+"\');";

        statement.executeUpdate(update);
        System.out.println("Update successfull");
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

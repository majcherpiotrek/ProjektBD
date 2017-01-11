package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.enumerations.Gender;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by piotrek on 12.01.17.
 */
public class SeasonRanking {
    public static ObservableList<Sailor> getSailorsObservableList(Connection dbConnection, Gender gender, Integer season) throws Exception{
        ObservableList<Sailor> sailorsObservableList = FXCollections.observableArrayList();

        String genderStringInDB = (gender.equals(Gender.MALE)) ? "M" : "K";

        String query = "SELECT * FROM ranking_sezonu WHERE sezon='"+ season.toString() +"' ORDER BY suma_punktow_rankingowych DESC;";
        Statement statement = dbConnection.createStatement();
        ResultSet resultSetSeason = statement.executeQuery(query);

        //Jeśli nie znaleziono zawodników dla tego sezonu
        if(resultSetSeason.equals(null))
            throw new Exception("Brak danych dla tego sezonu!");


        Integer orderNumber = 1;
        while(resultSetSeason.next()){
            query = "SELECT * FROM zawodnicy WHERE nr_rejestracyjny='";
            statement = dbConnection.createStatement();
            query += resultSetSeason.getString("nr_rejestracyjny_zawodnika") + "'";
            query += " AND plec='" + genderStringInDB + "';";
            ResultSet sailorData = statement.executeQuery(query);
            if(sailorData.next()){
                Sailor sailor = new Sailor(Integer.toString(sailorData.getInt("nr_rejestracyjny")),
                        sailorData.getString("imie"),
                        sailorData.getString("nazwisko"),
                        sailorData.getString("plec"),
                        sailorData.getString("narodowosc"),
                        sailorData.getString("marka_desek"),
                        sailorData.getString("marka_zagli"),
                        sailorData.getString("sponsorzy"));

                //przypisanie punktów rankingowych do obiektu zawodnika
                sailor.setSeasonPoints(Integer.parseInt(resultSetSeason.getString("suma_punktow_rankingowych")));

                //Przypisanie miejsca
                sailor.setPlaceInTable(orderNumber++);
                sailorsObservableList.add(sailor);
            }
            query = null;
        }

        return sailorsObservableList;
    }
}

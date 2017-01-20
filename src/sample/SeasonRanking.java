package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

        String query = "SELECT * FROM ranking_sezonu WHERE sezon_sezon='"+ season.toString() +"' ORDER BY suma_punktow_rankingowych DESC;";
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
        }

        return sailorsObservableList;
    }

    public static TableView<Sailor> createSeasonRankigTableView(ObservableList<Sailor> sailorsObservableList){
        TableColumn<Sailor, String> placeColumn = new TableColumn<>("Miejsce");
        placeColumn.setMinWidth(200);
        placeColumn.setCellValueFactory(new PropertyValueFactory<>("placeInTable"));

        TableColumn<Sailor, String> nameColumn = new TableColumn<>("Imię");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Sailor, String> surnameColumn = new TableColumn<>("Nazwisko");
        surnameColumn.setMinWidth(200);
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        TableColumn<Sailor, String> sailNumberColumn = new TableColumn<>("Nr rejestracyjny");
        sailNumberColumn.setMinWidth(200);
        sailNumberColumn.setCellValueFactory(new PropertyValueFactory<>("sailNumber"));

        TableColumn<Sailor, String> pointsColumn = new TableColumn<>("Punkty");
        pointsColumn.setMinWidth(200);
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("seasonPoints"));


        TableView<Sailor> rankingTableView = new TableView<>();
        rankingTableView.setItems(sailorsObservableList);
        rankingTableView.getColumns().addAll(placeColumn, nameColumn, surnameColumn, sailNumberColumn, pointsColumn );

        return rankingTableView;
    }
}

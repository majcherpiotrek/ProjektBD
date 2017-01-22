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
import java.util.ArrayList;

/**
 * Created by piotrek on 22.01.17.
 */
public class EventResults {
    public static ObservableList<Sailor> getEventResultsObservableList(Connection dbConnection, Integer eventID, Boolean single) throws Exception{
        ObservableList<Sailor> eventResultsObservableList = FXCollections.observableArrayList();


        String query = "SELECT * FROM wyniki_zawodow WHERE id_zawody="+ eventID;
        if(single)
            query+=" ORDER BY miejsce_pojedyncza_eliminacja ASC;";
        else
            query+=" ORDER BY miejsce_podwojna_eliminacja ASC;";

        Statement statement = dbConnection.createStatement();
        ResultSet resultSetEventResults = statement.executeQuery(query);

        //Jeśli nie znaleziono wyników dla tych zawodów
        if(resultSetEventResults.equals(null))
            throw new Exception("Brak danych dla tych zawodów!");

        //Teraz trzeba zapisać ważne informacje z wyników
        ArrayList<ArrayList<Integer>> resultsData = new ArrayList<>();
        for (int i=0; resultSetEventResults.next(); i++){
            resultsData.add(new ArrayList<>());
            resultsData.get(i).add(resultSetEventResults.getInt("nr_rejestracyjny"));
            resultsData.get(i).add(resultSetEventResults.getInt("miejsce_pojedyncza_eliminacja"));
            resultsData.get(i).add(resultSetEventResults.getInt("miejsce_podwojna_eliminacja"));
            resultsData.get(i).add(resultSetEventResults.getInt("zdobyte_punkty_rankingowe"));
        }

        //Teraz trzeba znaleźć na podstawie wyników zawodów, zawodników im przypisanych
        for(int i= 0; i< resultsData.size(); i++){
            query = "SELECT * FROM zawodnicy WHERE nr_rejestracyjny="+resultsData.get(i).get(0) + ";";
            ResultSet sailorDataResultSet = statement.executeQuery(query);

            while (sailorDataResultSet.next()){
                Integer sailNumber = sailorDataResultSet.getInt("nr_rejestracyjny");
                Sailor sailor = new Sailor(sailNumber.toString(),
                        sailorDataResultSet.getString("imie"),
                        sailorDataResultSet.getString("nazwisko"),
                        sailorDataResultSet.getString("plec"),
                        sailorDataResultSet.getString("narodowosc"),
                        sailorDataResultSet.getString("marka_desek"),
                        sailorDataResultSet.getString("marka_zagli"),
                        sailorDataResultSet.getString("sponsorzy"));

                sailor.setPlaceInTable((single)? resultsData.get(i).get(1) : resultsData.get(i).get(2));
                sailor.setSeasonPoints(resultsData.get(i).get(3));
                eventResultsObservableList.add(sailor);
            }

        }

        return eventResultsObservableList;
    }

    public static TableView<Sailor> createEventResultsTableView(ObservableList<Sailor> eventResultsObservableList, Boolean single){
        TableColumn<Sailor, String> placeColumn = new TableColumn<>( "Miejsce");
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

        TableColumn<Sailor, String> pointsColumn = new TableColumn<>("Punkty do rankingu");
        if(!single) {
            pointsColumn.setMinWidth(200);
            pointsColumn.setCellValueFactory(new PropertyValueFactory<>("seasonPoints"));
        }


        TableView<Sailor> eventResultsTableView = new TableView<>();
        eventResultsTableView.setItems(eventResultsObservableList);
        if(single)
            eventResultsTableView.getColumns().addAll(placeColumn, nameColumn, surnameColumn, sailNumberColumn );
        else
            eventResultsTableView.getColumns().addAll(placeColumn, nameColumn, surnameColumn, sailNumberColumn, pointsColumn );
        return eventResultsTableView;
    }
}

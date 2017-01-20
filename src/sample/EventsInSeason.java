package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.WindsurfingEvent;
import sample.enumerations.Gender;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by piotrek on 12.01.17.
 */
public class EventsInSeason {
    public static ObservableList<WindsurfingEvent> getEventsObservableList(Connection dbConnection, Integer season) throws Exception{
        ObservableList<WindsurfingEvent> eventsObservableList = FXCollections.observableArrayList();

        String query = "SELECT * FROM zawody WHERE sezon='"+ season.toString() +"' ORDER BY data DESC;";
        Statement statement = dbConnection.createStatement();
        ResultSet resultSetEvents = statement.executeQuery(query);

        //Jeśli nie znaleziono zawodów dla tego sezonu
        if(resultSetEvents.equals(null))
            throw new Exception("Brak danych dla tego sezonu!");


        while(resultSetEvents.next()){
            WindsurfingEvent windsurfingEvent = new WindsurfingEvent(
                    resultSetEvents.getInt("id_zawody"),
                    resultSetEvents.getString("nazwa_zawodow"),
                    resultSetEvents.getString("miejsce_rozegrania"),
                    resultSetEvents.getString("data"),
                    resultSetEvents.getInt("pula_nagrod"),
                    resultSetEvents.getInt("sezon"));
            eventsObservableList.add(windsurfingEvent);
        }

        return eventsObservableList;
    }

    public static TableView<WindsurfingEvent> createEventsInSeasonTableView(ObservableList<WindsurfingEvent> eventsObservableList){
        TableColumn<WindsurfingEvent, Integer> eventIDColumn = new TableColumn<>("ID zawodów");
        eventIDColumn.setMinWidth(200);
        eventIDColumn.setCellValueFactory(new PropertyValueFactory<>("eventID"));

        TableColumn<WindsurfingEvent, String> nameColumn = new TableColumn<>("Nazwa");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<WindsurfingEvent, String> locationColumn = new TableColumn<>("Miejsce rozegrania");
        locationColumn.setMinWidth(200);
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<WindsurfingEvent, String> dateColumn = new TableColumn<>("Data rozegrania");
        dateColumn.setMinWidth(200);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<WindsurfingEvent, Integer> prizeMoneyColumn = new TableColumn<>("Pula nagród");
        prizeMoneyColumn.setMinWidth(200);
        prizeMoneyColumn.setCellValueFactory(new PropertyValueFactory<>("prizeMoney"));

        TableView<WindsurfingEvent> eventsInSeasonTableView = new TableView<>();
        eventsInSeasonTableView.setItems(eventsObservableList);
        eventsInSeasonTableView.getColumns().addAll(eventIDColumn,nameColumn,locationColumn,dateColumn,prizeMoneyColumn);

        return eventsInSeasonTableView;
    }
}

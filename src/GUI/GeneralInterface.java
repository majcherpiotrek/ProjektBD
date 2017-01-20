package GUI;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.*;
import sample.enumerations.Gender;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Klasa wyświetlająca ogólny interfejs użytkownika
 * Created by piotrek on 20.01.17.
 */
/*TODO
-Spróbować zrobić generyczny table view
 */
public class GeneralInterface {

    private Stage mainWindow;
    private Connection dbConnection;
    private TableView<Sailor> rankingTableView;
    private TableView<WindsurfingEvent> eventTableView;
    private TableView<Sailor> sailorsTableView;
    GridPane layout;
    VBox tableLayout;
    Scene mainScene;

    public GeneralInterface(Stage mainWindow, Connection connection) {
        this.mainWindow = mainWindow;
        this.dbConnection = connection;
    }

    public void Display() {

        mainWindow.setTitle("Ranking Puchar Świata w windsurfingu!");

        //ChoiceBox do wyboru płci
        ChoiceBox<Gender> genderChoiceBox = new ChoiceBox<>();
        genderChoiceBox.getItems().addAll(Gender.MALE, Gender.FEMALE);
        genderChoiceBox.setValue(Gender.MALE);
        //Zainicjowanie listy sezonów
        ArrayList<Integer> allSeasons;
        try {
            allSeasons = Seasons.getAllSeasons(dbConnection);
        } catch (SQLException ex) {
            AlertBox.Display("Błąd połączenia!", ex.getMessage());
            return;
        }
        //ChoiceBox do wyboru sezonu
        ChoiceBox<Integer> seasonChoiceBox = new ChoiceBox<>();
        for (Integer season : allSeasons)
            seasonChoiceBox.getItems().add(season);
        seasonChoiceBox.setValue(seasonChoiceBox.getItems().get(seasonChoiceBox.getItems().size() - 1)); // najnowszy sezon

        //Zainicjowanie tabeli rankingu
        try {
            rankingTableView = updateRankingTableView(genderChoiceBox.getValue(), seasonChoiceBox.getValue());
        } catch (Exception ex) {
            AlertBox.Display("Błąd pobierania rankingu", ex.getMessage());
        }
        //Przycisk logowania na konto administratora
        Button adminLoginButton = new Button("Logowanie administratora");
        adminLoginButton.setOnAction(e -> {
            Boolean isLoggedIn = false;
            isLoggedIn = LoginWindow.Display(dbConnection);
            System.out.println(isLoggedIn);
            if (isLoggedIn) {
                AdminInterface adminGUI = new AdminInterface(dbConnection);
                adminGUI.Display();
            }
        });
        //Przycisk wyświetlający listę zawodów w sezonie
        Button eventsListButton = new Button("Lista zawodów");
        eventsListButton.setOnAction(event -> {
            try {
                tableLayout.getChildren().remove(0);
                layout.getChildren().remove(tableLayout);
                eventTableView = updateEventsTableView(seasonChoiceBox.getValue());
                tableLayout.getChildren().add(eventTableView);
                layout.add(tableLayout, 0, 1, 5, 1);
            } catch (Exception ex) {
                AlertBox.Display("Błąd pobierania danych", ex.getMessage());
            }
        });
        //Przycisk wyświetlający listę wszystkich zawodników i zawodniczek
        Button sailorListButton = new Button("Lista wszystkich zawodników");
        sailorListButton.setOnAction(e -> {
            try {
                tableLayout.getChildren().remove(0);
                layout.getChildren().remove(tableLayout);
                sailorsTableView = updateSailorsTableView();
                tableLayout.getChildren().add(sailorsTableView);
                layout.add(tableLayout, 0, 1, 5, 1);
            } catch (Exception ex) {
                AlertBox.Display("Błąd pobierania sezonów", ex.getMessage());
            }
        });
        //Przycisk wyświetlający klasyfikację generalną danego sezonu (wyświetlana od razu domyślnie)
        Button rankingButton = new Button("Klasyfikacja sezonu");
        rankingButton.setOnAction(e -> {
            try {
                tableLayout.getChildren().remove(0);
                layout.getChildren().remove(tableLayout);
                rankingTableView = updateRankingTableView(genderChoiceBox.getValue(), seasonChoiceBox.getValue());
                tableLayout.getChildren().add(rankingTableView);
                layout.add(tableLayout, 0, 1, 5, 1);
            } catch (Exception ex) {
                AlertBox.Display("Błąd pobierania rankingu", ex.getMessage());
            }
        });

        //Layout

        layout = new GridPane();
        tableLayout = new VBox();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setHgap(8);
        layout.setVgap(8);
        layout.add(genderChoiceBox, 0, 0);
        layout.add(seasonChoiceBox, 1, 0);
        layout.add(rankingButton, 2, 0);
        layout.add(eventsListButton, 3, 0);
        layout.add(sailorListButton, 4, 0);
        tableLayout.getChildren().add(rankingTableView);
        layout.add(tableLayout, 0, 1, 5, 1);
        layout.add(adminLoginButton, 0, 2);


        mainScene = new Scene(layout);
        mainWindow.setScene(mainScene);
        mainWindow.show();
    }

    private TableView<Sailor> updateRankingTableView(Gender gender, Integer season) throws Exception {
        ObservableList<Sailor> sailorObservableList = SeasonRanking.getSailorsObservableList(this.dbConnection, gender, season);
        return (SeasonRanking.createSeasonRankigTableView(sailorObservableList));
    }

    private TableView<WindsurfingEvent> updateEventsTableView(Integer season) throws Exception {
        ObservableList<WindsurfingEvent> eventsInSeasonObservableList = EventsInSeason.getEventsObservableList(dbConnection, season);
        return (EventsInSeason.createEventsInSeasonTableView(eventsInSeasonObservableList));
    }

    private TableView<Sailor> updateSailorsTableView() throws Exception{
        ObservableList<Sailor> allCompetitorsObservableList = AllCompetitorsList.getSailorsObservableList(dbConnection);
       return (AllCompetitorsList.createAllCompetitorsTableView(allCompetitorsObservableList));
    }
}

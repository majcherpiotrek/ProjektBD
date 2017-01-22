package GUI;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.*;
import sample.enumerations.Gender;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        seasonChoiceBox.setValue(seasonChoiceBox.getItems().get(0)); // najnowszy sezon

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
        Button showDetailsButton = new Button("Pokaż profil zawodnika");
        showDetailsButton.setOnAction(e->{
            try{
                Sailor sailor = rankingTableView.getSelectionModel().getSelectedItem();
                showSailorDetailsWindow(sailor);
            }catch (Exception ex){
                AlertBox.Display("Błąd","Musisz zaznaczyć zawodnika na liście!");
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
                showDetailsButton.setText("Pokaż profil zawodow");
                showDetailsButton.setOnAction(e->{
                    WindsurfingEvent windsurfingEvent = eventTableView.getSelectionModel().getSelectedItem();
                    try {
                        showEventDetailsWindow(windsurfingEvent);
                    }catch (Exception ex){
                        AlertBox.Display("Błąd","Nie udało się załadować listy zawodów!");
                    }
                });
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
                showDetailsButton.setOnAction(ev->{
                    try{
                        Sailor sailor = sailorsTableView.getSelectionModel().getSelectedItem();
                        showSailorDetailsWindow(sailor);
                    }catch (Exception ex){
                        AlertBox.Display("Błąd","Musisz zaznaczyć zawodnika na liście!");
                    }
                });
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
                showDetailsButton.setOnAction(ev->{
                    try{
                        Sailor sailor = rankingTableView.getSelectionModel().getSelectedItem();
                        showSailorDetailsWindow(sailor);
                    }catch (Exception ex){
                        AlertBox.Display("Błąd","Musisz zaznaczyć zawodnika na liście!");
                    }
                });
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
        layout.add(showDetailsButton, 1,2);


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

    private void showSailorDetailsWindow(Sailor sailor) throws SQLException {
        Stage sailorDetailsWindow = new Stage();
        sailorDetailsWindow.setTitle("Profil zawodnika");
        sailorDetailsWindow.setResizable(false);

        Label nameLabel = new Label("Imię: " + sailor.getName());
        nameLabel.setPadding(new Insets(5,5,5,5));
        Label surnameLabel = new Label("Nazwisko: " + sailor.getSurname());
        surnameLabel.setPadding(new Insets(5,5,5,5));
        Label nationalityLabel = new Label("Narodowość: " + sailor.getNationality());
        nationalityLabel.setPadding(new Insets(5,5,5,5));
        Label sailNumberLabel = new Label("Nr rejestracyjny: "+ sailor.getSailNumber());
        sailNumberLabel.setPadding(new Insets(5,5,5,5));
        Label sexLabel = new Label("Płeć: "+sailor.getSex());
        sexLabel.setPadding(new Insets(5,5,5,5));
        Label boardBrandLabel = new Label("Marka desek: " + sailor.getBoardBrand());
        boardBrandLabel.setPadding(new Insets(5,5,5,5));
        Label sailBrandLabel = new Label("Marka żagli: "+ sailor.getSailBrand());
        sailBrandLabel.setPadding(new Insets(5,5,5,5));
        Label sponsorsLabel = new Label("Sponsorzy: " + sailor.getSponsors());
        sponsorsLabel.setPadding(new Insets(5,5,5,5));

        //Pobranie wyników zawodnika w każdych zawodach
        Statement statement = dbConnection.createStatement();
        String query = "SELECT * FROM wyniki_zawodow WHERE nr_rejestracyjny="+sailor.getSailNumber()+" ORDER BY miejsce_podwojna_eliminacja DESC;";
        ResultSet rS = statement.executeQuery(query);

        ArrayList<Label> bestResultsLabels = new ArrayList<>();
        ArrayList<ArrayList<Integer>> resultsData = new ArrayList<>();
        for (int i = 0; rS.next(); i++){
            resultsData.add(new ArrayList<>());
            resultsData.get(i).add(rS.getInt("id_zawody"));
            resultsData.get(i).add(rS.getInt("miejsce_pojedyncza_eliminacja"));
            resultsData.get(i).add(rS.getInt("miejsce_podwojna_eliminacja"));
            resultsData.get(i).add( rS.getInt("zdobyte_punkty_rankingowe"));
        }
        for (int i=0; i < resultsData.size() && i<5; i++){
            query = "SELECT * FROM zawody WHERE id_zawody="+resultsData.get(i).get(0)+";";
            Integer single = resultsData.get(i).get(1);
            Integer finalResult = resultsData.get(i).get(2);
            Integer rankingPoints = resultsData.get(i).get(3);

            ResultSet eventRS = statement.executeQuery(query);
            while(eventRS.next()){
                bestResultsLabels.add(new Label(eventRS.getString("nazwa_zawodow") + "\n"
                        + "Sezon: " + eventRS.getInt("sezon_sezon") + "\n"
                        +"Miejsce w pojedynczej eliminacji: "
                        + single + "\n"
                        + "Miejsce w całych zawodach: "
                        + finalResult + "\n"
                        + "Zdobyte punkty rankingowe: "
                        + rankingPoints));
            }
        }

        VBox bestResultsLayout = new VBox();
        bestResultsLayout.setPadding(new Insets(10,10,10,10));
        bestResultsLayout.setStyle("-fx-border-style: dotted");
        Label title = new Label("Najlepsze wyniki zawodnika:");
        title.setPadding(new Insets(5,5,5,5));
        bestResultsLayout.getChildren().add(title);
        for(Label label : bestResultsLabels) {
            label.setStyle("-fx-border-style: dashed");
            label.setPadding(new Insets(5,5,5,5));
            bestResultsLayout.getChildren().add(label);
        }

        VBox layout = new VBox();
        layout.getChildren().addAll(nameLabel,surnameLabel,nationalityLabel,sailNumberLabel,sexLabel,boardBrandLabel,sailBrandLabel,sponsorsLabel, bestResultsLayout);

        Scene scene = new Scene(layout);

        sailorDetailsWindow.setScene(scene);
        sailorDetailsWindow.show();
    }

    private void showEventDetailsWindow(WindsurfingEvent windsurfingEvent) throws Exception {
        Stage eventDetailsWindow = new Stage();
        eventDetailsWindow.setTitle("Zakładka zawodów");
        eventDetailsWindow.setResizable(false);

       ObservableList<Sailor> eventResultsSingle = EventResults.getEventResultsObservableList(dbConnection,windsurfingEvent.getEventID(),true);
       TableView<Sailor> eventResultsSingleTableView = EventResults.createEventResultsTableView(eventResultsSingle,true);

       ObservableList<Sailor> eventResultsDouble = EventResults.getEventResultsObservableList(dbConnection,windsurfingEvent.getEventID(),false);
       TableView<Sailor> eventResultsDoubleTableView = EventResults.createEventResultsTableView(eventResultsDouble,false);

       eventResultsSingleTableView.setMaxHeight(200);
       eventResultsDoubleTableView.setMaxHeight(200);

       Label eventInfo = new Label(windsurfingEvent.getName()
               + "\nMiejsce rozegrania: " + windsurfingEvent.getLocation()
               + " Data rozegrania: " + windsurfingEvent.getDate()
               + " Pula nagród: " + windsurfingEvent.getPrizeMoney());
       eventInfo.setStyle("-fx-font-weight: bolder; -fx-border-style: dashed");
       eventInfo.setPadding(new Insets(10,10,10,10));

       Label singleElimLabel = new Label("Wyniki pojedynczej eliminacji:");
       singleElimLabel.setPadding(new Insets(10,10,10,10));
       singleElimLabel.setStyle("-fx-border-style: dashed");
       Label doubleElimLabel = new Label("Wyniki podwójnej elminacji i całych zawodów: ");
       doubleElimLabel.setPadding(new Insets(10,10,10,10));
       doubleElimLabel.setStyle("-fx-border-style: dashed");

       Button showSailorButton = new Button("Pokaż profil zawodnika (zaznacz w drugiej tabeli)");
       showSailorButton.setOnAction(e->{
           Sailor sailor = eventResultsDoubleTableView.getSelectionModel().getSelectedItem();
           try {
               showSailorDetailsWindow(sailor);
           }catch (Exception ex){
               AlertBox.Display("Błąd","Nie udało się pobrać profilu zawodnika");
           }
       });

       VBox layout = new VBox();
       layout.setSpacing(10);
       layout.setPadding(new Insets(5,5,5,5));
       layout.getChildren().addAll(eventInfo,singleElimLabel,eventResultsSingleTableView,doubleElimLabel,eventResultsDoubleTableView,showSailorButton);


       Scene scene = new Scene(layout);
       eventDetailsWindow.setScene(scene);
       eventDetailsWindow.show();
    }
}

package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.enumerations.Gender;
import java.sql.Connection;
import java.sql.DriverManager;


/*TODO
-trzeba zrobić tak, żeby dodanie wyników zawodów powodowało zaktualizowanie punktów w wynikach zawodów
-ADMIN: logowanie, dodawanie sezonu, dodawanie zawodów + wyników zawodów, dodawanie zawodników
-listenery do choiceboxów
-wyświetlanie profilu zawodnika po wybraniu go z listy
-wyświetlanie profilu zawodów lub wyników zawodów po wybraniu ich z listy

PO ZROBIENIU DODAWANIA DANYCH PRZEZ ADMINA:
-dodać kobiety
-dodać kilka sezonów
 */
public class Main extends Application {

    private static Connection dbConnection;
    private Stage mainWindow;
    private Scene mainScene;
    private GridPane layout;
    private ChoiceBox<Gender> genderChoiceBox;
    private ChoiceBox<Integer> seasonChoiceBox;
    private Button rankingButton;
    private Button eventsListButton;
    private Button sailorListButton;

    @Override
    public void start(Stage primaryStage) throws Exception{
        try{
            //Połączenie z BD
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useSSl=false", "root", "root");
        }catch(Exception ex){
            AlertBox.Display("Błąd połączenia", ex.getMessage());
            return;
        }

        mainWindow=primaryStage;
        mainWindow.setTitle("Ranking Puchar Świata w windsurfingu!");

        //ChoiceBox do wyboru płci
        genderChoiceBox = new ChoiceBox<>();
        genderChoiceBox.getItems().addAll(Gender.MALE, Gender.FEMALE);
        genderChoiceBox.setValue(Gender.MALE);

        //ChoiceBox do wyboru sezonu
        seasonChoiceBox = new ChoiceBox<>();
        seasonChoiceBox.getItems().addAll(2014, 2015, 2016);
        seasonChoiceBox.setValue(seasonChoiceBox.getItems().get(seasonChoiceBox.getItems().size() - 1)); // najnowszy sezon

        //Przycisk wyświetlający klasyfikację generalną danego sezonu (wyświetlana od razu domyślnie)
        rankingButton = new Button("Klasyfikacja sezonu");
        rankingButton.setOnAction(e -> {
            if(layout.getChildren().size() == 1)
                initStandardLayout(layout);
            mainWindow.setScene(mainScene);
        });

        //Przycisk wyświetlający listę zawodów w sezonie
        eventsListButton = new Button("Lista zawodów");
        eventsListButton.setOnAction(event -> {
            try {
                setEventsListLayout();

            }catch (Exception ex){
                AlertBox.Display("Błąd pobierania danych", ex.getMessage());
            }
        });

        //Przycisk wyświetlający listę wszystkich zawodników i zawodniczek
        sailorListButton = new Button("Lista wszystkich zawodników");
        sailorListButton.setOnAction(e -> {
            try {
                setAllCompetitorsListLayout();
            }catch(Exception ex){
                AlertBox.Display("Błąd pobierania danych", ex.getMessage());
            }
        });

        TableView<Sailor> rankingTableView = new TableView<>();
        try{
            ObservableList<Sailor> sailorObservableList = SeasonRanking.getSailorsObservableList(dbConnection, genderChoiceBox.getValue(), seasonChoiceBox.getValue());
            rankingTableView = SeasonRanking.createSeasonRankigTableView(sailorObservableList);
        }catch (Exception ex){
            AlertBox.Display("Błąd pobierania danych", ex.getMessage());
        }

        //Layout
        layout = new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        layout.setHgap(8);
        layout.setVgap(8);
        layout.add(genderChoiceBox, 0,0);
        layout.add(seasonChoiceBox, 1,0);
        layout.add(rankingButton,2,0);
        layout.add(eventsListButton,3,0);
        layout.add(sailorListButton, 4,0);
        layout.add(rankingTableView,0,1,5,1);


        mainScene = new Scene(layout);
        mainWindow.setScene(mainScene);
        mainWindow.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
    private void initStandardLayout(GridPane layoutToInit){
        layoutToInit.setPadding(new Insets(10,10,10,10));
        layoutToInit.setHgap(8);
        layoutToInit.setVgap(8);
        layoutToInit.add(genderChoiceBox, 0,0);
        layoutToInit.add(seasonChoiceBox, 1,0);
        layoutToInit.add(rankingButton,2,0);
        layoutToInit.add(eventsListButton,3,0);
        layoutToInit.add(sailorListButton, 4,0);
    }

    private void setAllCompetitorsListLayout() throws Exception{

        GridPane allCompetitorsLayout = new GridPane();
        //zainicjowanie wspólnych przycisków
        initStandardLayout(allCompetitorsLayout);

        //usunięcie wyboru płci i sezonu (niepotrzebne na liście wszystkich zawodników)
        allCompetitorsLayout.getChildren().remove(genderChoiceBox);
        allCompetitorsLayout.getChildren().remove(seasonChoiceBox);

        //Pozyskanie danych z bazy
        ObservableList<Sailor> allCompetitorsObservableList = AllCompetitorsList.getSailorsObservableList(dbConnection);
        TableView<Sailor> allCompetitorsTableView = AllCompetitorsList.createAllCompetitorsTableView(allCompetitorsObservableList);

        //Dodanie tabeli do layoutu
        allCompetitorsLayout.add(allCompetitorsTableView, 0, 1, 5, 1);

        Scene allCompetitorsScene = new Scene(allCompetitorsLayout);
        mainWindow.setScene(allCompetitorsScene);
    }

    private void setEventsListLayout() throws Exception{
        GridPane eventsListLayout = new GridPane();
        initStandardLayout(eventsListLayout);

        ObservableList<WindsurfingEvent> eventsInSeasonObservableList = EventsInSeason.getEventsObservableList(dbConnection, seasonChoiceBox.getValue());
        TableView<WindsurfingEvent> eventsInSeasonTableView = EventsInSeason.createEventsInSeasonTableView(eventsInSeasonObservableList);
        eventsListLayout.add(eventsInSeasonTableView,0, 1,5,1);

        Scene eventsListScene = new Scene(eventsListLayout);
        mainWindow.setScene(eventsListScene);
    }

}
